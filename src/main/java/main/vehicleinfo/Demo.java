package main.vehicleinfo;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 本功能主要用到如下几个表：
 * 表一:车辆坐标表
 * 表二:配送单表
 * 表三:停车点表(记录每个车的停车点，不与配送单等关联，就是纯车辆角度的，本表保存在mysql里、Hbase里都行，可以定时清理历史数据，属于一种临时性表，比如只保留最近30天的)
 * 表四:配送单停车结果表，也是前端程序展示数据的来源
 * 表五:配送单临时表，如果某个配送单没有匹配到其进出目标油站的记录，就保存在本表中。
 *
 * 本程序大体逻辑如下:
 * 1、读取HIVE中车辆坐标数据，筛选条件初定是程序运行时刻所在的整点为结束时间，整点前推3小时为开始时间，然后判断出停车点，将其保存到数据库表三中，注意这里要保存到数据库表了。
 * 2、注意最终保存的停车点结果，每条记录包括了车牌号，停车预期的一些地址信息，进入该区域时间，离开该区域时间，对于进入了该区域但是没离开的，我们也会剔除，即结果中只有有进有出的区域。
 * 3、读取配送单表，筛选条件是出库时间是程序运行时刻所在的整点前推3小时为开始时间，开始时间+1小时为结束时间
 * 4、将3中的配送单和2的结果匹配，如果能匹配到，将匹配到的存入一个List中，比如叫ParKResultList
 * 5、如果没匹配到，会放入一个List中，假设叫deliverForNextyList，注意这时候不将deliverForNextyList存入数据库先。
 * 6、读取上面的表五和表三，进行匹配，注意，这里我们不会再去读坐标计算停车，而是直接再次读取表三，另外注意，这时候表三中已经包括了本次计算的停车点。。
 * 7、如果匹配到，将结果存入存入到ParKResultList中，匹配不到的，存入deliverForNextyList
 * 8、将ParKResultList和deliverForNextyList中的记录分别存入数据库表，本次程序结束
 */

public class Demo {
    private static final ZoneId zid = ZoneId.of("Asia/Shanghai");
    private static final ZoneOffset zos = ZoneOffset.of("+8");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00");
    private static final DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws ClassNotFoundException {

        int i = 0;
        int vehicleParkResultSize = 0;
        boolean flag; //如果配送单车辆进入并且离开了目标油站，flag就为true
        List<ParkResult> parkResultList = new LinkedList<>();//用来保存最终结果，即匹配到了离开目标油站的配送单
        ParkResult parkResult;
        VehicleParkInfo vehicleParkInfo;
        VehicleParkInfo[] vehicleParkInfoArray = new VehicleParkInfo[0];
        List<VehicleParkInfo> vehicleParkInfoList = new ArrayList<>();
        LocalDateTime ldt = LocalDateTime.now(zid);
        String endTime = ldt.format(dtf);
        String startTime = ldt.minusHours(3).format(dtf);
        List<Delivery> deliveryList;//<----------------这个用来模拟读取了一个小时内出库的配送单的Resultset嘿嘿
        List<Delivery> deliverForNextyList = new LinkedList<>(); //<----------------这个用来存放本次匹配没找到目标油站的配送单
        //**************************************************开始读取车辆坐标数据并分析停车点**************************************************
        //sql要替换成实际的哈
        String sql = "select 车牌号,地理哈希,进入时间,离开时间,POI " +
                "from " +
                " (select 车牌号,地理哈希,下个地点,POI," +
                "   case when lag(地点,1,'e') over (partition by 车牌号 order by 坐标时间) <> 地点 then 坐标时间 else '0' end as 进入时间," +
                "   case when 地点 <> 下个地点 then 坐标时间 else '0' end as 离开时间 " +
                "  from" +
                "   ( " +
                "     select t1.车牌号 as 车牌号," +
                "            t1.坐标时间 as 坐标时间," +
                "            t1.地理哈希 as 地理哈希," +
                "            lead(t1.地理哈希,1,'e') over (partition by 车牌号 order by 坐标时间) as 下个地点," +
                "            ISNULL(t2.油站名,'0') end as POI " +
                "     from 车辆坐标表 t1 " +
                "     left join 油站坐标表 t2 " +
                "     on t1.地理哈希编码=t2.地理哈希编码 " +
                "     where 车辆坐标发送时间>=" + startTime + " and 车辆坐标发送时间< " + endTime +
                "     order by 车牌号,坐标时间 " +
                "   ) as t3 " +
                ") as t4 " +
                "where (进入时间<>离开时间) or (下个地点='e') " +
                "order by 车牌号,进入时间";

        Class.forName("impala的jdbc驱动类");
        try (Connection conn = DriverManager.getConnection("impala的jdbc")) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.last()) {
                vehicleParkResultSize = rs.getRow();
                rs.beforeFirst();
            }
            vehicleParkInfoArray = new VehicleParkInfo[vehicleParkResultSize];
            while (rs.next()) {
                vehicleParkInfo = new VehicleParkInfo();
                vehicleParkInfo.setLicenseNumber(rs.getString(1)); //车牌号
                vehicleParkInfo.setPoi(rs.getString(2)); //地理哈希
                vehicleParkInfo.setEnterTime(rs.getString(3)); //进入时间
                vehicleParkInfo.setLeaveTime(rs.getString(4)); //离开时间
                vehicleParkInfo.setIsGasStation(rs.getString(1));//0不是已知加油站，对于值是0的，就调用地图服务(比如天地图)获取POI
                vehicleParkInfoArray[i] = vehicleParkInfo;
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (vehicleParkResultSize > 0) {
            i = 0;
            long seconds;
            VehicleParkInfo vehicleParkInfoNext;
            int j = vehicleParkResultSize - 1;
            while (i < vehicleParkResultSize) {
                vehicleParkInfo = vehicleParkInfoArray[i];
                if (!vehicleParkInfo.getEnterTime().equals("0")) {
                    if (i != j) {
                        vehicleParkInfoNext = vehicleParkInfoArray[i + 1];
                        if ((vehicleParkInfo.getLicenseNumber().equals(vehicleParkInfoNext.getLicenseNumber())) && (!vehicleParkInfoNext.getLeaveTime().equals("0")) && (vehicleParkInfoNext.getEnterTime().equals("0"))) {
                            vehicleParkInfo.setLeaveTime(vehicleParkInfoNext.getLeaveTime());
                            seconds = ChronoUnit.SECONDS.between(LocalDateTime.parse(vehicleParkInfo.getEnterTime(), dtf1), LocalDateTime.parse(vehicleParkInfo.getLeaveTime(), dtf1));
                            //大于15分钟的停车点会保留下来
                            if (seconds > 900) {
                                vehicleParkInfo.setParkTime(seconds);
                                vehicleParkInfo.setParkTimeDesc(convertParkSecondsToParkTimeDesc(seconds));
                                WGS84Point point = GeoHash.fromGeohashString(vehicleParkInfo.getPoi()).getOriginatingPoint();
                                double lng = point.getLongitude();
                                double lat = point.getLatitude();
                                vehicleParkInfo.setParkLng(lng);
                                vehicleParkInfo.setParkLat(lat);
                                //!!!!!!!!!!!!!!!注意，这里要根据lng和lat调用地图服务接口，得到province,city,country,formatted_address
                                //调用地图服务的内容就给仲凯/张炎二位巨拿中的哪位处理了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈
                                vehicleParkInfoList.add(vehicleParkInfo);
                            }// end if(seconds>900)
                        }// end if ((vehicleParkInfo.getLicenseNumber().equals(vehicleParkInfoNext.getLicenseNumber())) && (!vehicleParkInfoNext.getLeaveTime().equals("0")))
                    }//end if (i != j)
                }// end if (!vehicleParkInfo.getEnterTime().equals("0"))
                i++;
            }// end while
        }//end if(vehicleParkResultSize>0)
        //**************************************************结束读取车辆坐标数据并分析停车点**************************************************
        //上面的步骤执行完，就获取了所有有完整进出停车区域的数据，也不存在进入某个区域没离开的记录了
        vehicleParkInfoArray = null;

        if (vehicleParkInfoList.size() > 0) {
            //这里执行数据库插入  <----------------这里要补上把vehicleParkInfoList内容插入mysql的逻辑，这个就不细写了，交给老张仲凯哈哈哈哈哈

            endTime = ldt.minusHours(2).format(dtf);//配送单出库的结束时间
            // <----------------这里要补上读取数据库获取配送单的逻辑，这个就不细写了，交给老张仲凯哈哈哈哈哈，记得出库时间范围在startTime和endTime之间
            deliveryList = new LinkedList<>(); //<----------------这个用来模拟配送单的Resultset嘿嘿


            for (Delivery deli : deliveryList) {
                flag = false; //每拿到一条配送单，先设为false
                for (VehicleParkInfo vp : vehicleParkInfoList) {
                    if (deli.getDepartTime().compareTo(vp.getEnterTime()) > 0) {
                        continue;
                    }
                    if (deli.getLicenseNumber().equals(vp.getLicenseNumber())) {
                        parkResult = new ParkResult();
                        parkResult.setDeliveryId(deli.getDeliveryId());//配送单号
                        parkResult.setOilDepot(deli.getOilDeport());//油库名称
                        parkResult.setGasStation(deli.getGasStation());//目标加油站
                        parkResult.setDepartTime(deli.getDepartTime());//出库时间
                        parkResult.setOilTyep(deli.getOilType());// 获取油品
                        parkResult.setParkLng(vp.getParkLng()); //停车区域经度
                        parkResult.setParkLat(vp.getParkLat()); //停车区域纬度
                        parkResult.setPoi(vp.getPoi()); //停车POI
                        parkResult.setProvince(vp.getProvince()); //省份
                        parkResult.setCity(vp.getCity()); //城市
                        parkResult.setConuntry(vp.getConuntry()); //区
                        parkResult.setLicenseNumber(vp.getLicenseNumber()); //车牌号
                        parkResult.setEnterTime(vp.getEnterTime()); //进入时间
                        parkResult.setLeaveTime(vp.getLeaveTime()); //离开时间
                        parkResult.setParkTimeDesc(vp.getParkTimeDesc()); //以文字描述的停车时间
                        parkResult.setParkTime(vp.getParkTime()); //以秒记的停车时间
                        parkResultList.add(parkResult);
                        if (deli.getGasStation().equals(vp.getPoi())) {
                            break;
                        }//end if (deli.getGasStation().equals(vp.getPoi()))
                    }// end if (deli.getLicenseNumber().equals(vp.getLicenseNumber()))
                }// end  for (VehicleParkInfo vp : vehicleParkInfoList)
                if (!flag) {//遍历完了，flag还是false，说明就没到目标油站
                    deliverForNextyList.add(deli); //注意这里我们只添加进这个list就可以，先不将这个list的值存入数据库中
                }
            }//end for (Delivery deli : deliveryList)
        }//end if (vehicleParkInfoList.size() > 0)

        //将parkResultList结果存入mysql  <----------------这里懒得写了哈哈哈，总之就是存入mysql

        //**************************************************结束遍历**************************************************

        //***************************执行到这里，就完成了对一个小时内的配送单的处理***************************
        //***************************下面开始丢数据库上回没匹配到的配送单的处理***************************
        deliveryList = new LinkedList<>(); //<----------------这个用来模拟配送单的Resultset
        //读取表三:停车点表，获得事先算好的车辆停车点，注意这里我们不是再读取坐标表计算停车点，而是直接读取停车点表
        vehicleParkInfoList = new LinkedList<>(); //<----------------这个用来模拟停车点的Resultset
        parkResultList = new LinkedList<>();
        for (Delivery deli : deliveryList) {
            flag = false; //每拿到一条配送单，先设为false
            for (VehicleParkInfo vp : vehicleParkInfoList) {
                if (deli.getDepartTime().compareTo(vp.getEnterTime()) > 0) {
                    continue;
                }
                if (deli.getLicenseNumber().equals(vp.getLicenseNumber())) {
                    parkResult = new ParkResult();
                    parkResult.setDeliveryId(deli.getDeliveryId());//配送单号
                    parkResult.setOilDepot(deli.getOilDeport());//油库名称
                    parkResult.setGasStation(deli.getGasStation());//目标加油站
                    parkResult.setDepartTime(deli.getDepartTime());//出库时间
                    parkResult.setOilTyep(deli.getOilType());// 获取油品
                    parkResult.setParkLng(vp.getParkLng()); //停车区域经度
                    parkResult.setParkLat(vp.getParkLat()); //停车区域纬度
                    parkResult.setPoi(vp.getPoi()); //停车POI
                    parkResult.setProvince(vp.getProvince()); //省份
                    parkResult.setCity(vp.getCity()); //城市
                    parkResult.setConuntry(vp.getConuntry()); //区
                    parkResult.setLicenseNumber(vp.getLicenseNumber()); //车牌号
                    parkResult.setEnterTime(vp.getEnterTime()); //进入时间
                    parkResult.setLeaveTime(vp.getLeaveTime()); //离开时间
                    parkResult.setParkTimeDesc(vp.getParkTimeDesc()); //以文字描述的停车时间
                    parkResult.setParkTime(vp.getParkTime()); //以秒记的停车时间
                    parkResultList.add(parkResult);
                    if (deli.getGasStation().equals(vp.getPoi())) {
                        break;
                    }//end if (deli.getGasStation().equals(vp.getPoi()))
                }// end if (deli.getLicenseNumber().equals(vp.getLicenseNumber()))
            }// end  for (VehicleParkInfo vp : vehicleParkInfoList)
            if (!flag) {//遍历完了，flag还是false，说明就没到目标油站
                deliverForNextyList.add(deli);
            }
        }//end for (Delivery deli : deliveryList)

        //将deliverForNextyList存入mysql表中
        //遍历parkResultList，从表五:配送单临时表中删除parkResultList中有的记录
        //将parkResultList存入结果表中，齐活
    }// end main

    private static final String convertParkSecondsToParkTimeDesc(long seconds) {
        long h = 0;
        long m = 0;
        long s = 0;
        long modv = 0;
        String result = "";
        if (seconds >= 3600) {
            h = seconds / 3600;
            modv = seconds % 3600;
            m = modv / 60;
            s = modv % 60;
            result = result + h + "小时";
            if (m > 0) {
                result = result + m + "分";
            }
            if (s > 0) {
                result = result + s + "秒";
            }
        } else if ((seconds < 3600) && (seconds >= 60)) {
            m = seconds / 60;
            s = seconds % 60;
            result = result + m + "分钟";
            if (s > 0) {
                result = result + s + "秒";
            }
        } else {
            result = result + seconds + "秒";
        }
        return result;
    }
}
