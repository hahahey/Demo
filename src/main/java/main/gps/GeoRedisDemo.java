package main.gps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GeoRedisDemo {
    public static void main(String[] args) {
        String host = "10.252.68.192";
        int port = 8004;
        String password="PCITC2021@pcitc";
        JedisShardInfo jedis = new JedisShardInfo(host,port);
        jedis.setPassword(password);

        Jedis resource = jedis.createResource();
        //删除 c_oil_site 下所有数据
//        Set<String> c_oil_site = resource.zrange("c_oil_site", 0, -1);
//        for (String s : c_oil_site) {
//            resource.zrem("c_oil_site",s);
//        }

        List<String> list = new ArrayList<>();
        list.add("{\"plateNumber\": \"晋F88888\",\"mapLng\": \"113.1773368\",\"mapLat\": \"39.8172269\",\"heartTime\": \"2021-08-13 14:00:54\"}");
        list.add("{\"plateNumber\": \"晋F88888\",\"mapLng\": \"113.1773368\",\"mapLat\": \"39.8172269\",\"heartTime\": \"2021-08-14 14:00:54\"}");
        list.add("{\"plateNumber\": \"晋F99999\",\"mapLng\": \"113.1773368\",\"mapLat\": \"39.8172269\",\"heartTime\": \"2021-08-13 14:00:54\"}");
        list.add("{\"plateNumber\": \"晋F99999\",\"mapLng\": \"113.1773368\",\"mapLat\": \"39.8172269\",\"heartTime\": \"2021-08-14 14:00:54\"}");

        JsonParser parser = new JsonParser();

        String redisKey = "c_oil_site";
        SimpleDateFormat formatYMD = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatterYMDHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (String s : list) {


            JsonObject object = parser.parse(s).getAsJsonObject();
            double mapLng = object.get("mapLng").getAsDouble();
            double mapLat = object.get("mapLat").getAsDouble();
            String plateNumber = object.get("plateNumber").getAsString();
            String heartTime = object.get("heartTime").getAsString();

            try {
                heartTime = formatYMD.format(formatterYMDHms.parse(heartTime));
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            String key = "oil_"+plateNumber.concat(heartTime);

            Map<String,String> map = new HashMap<>();
            //匹配和油库距离 小于 500米的油库坐标
            List<GeoRadiusResponse> geoRadiusLists = resource.georadius(redisKey, mapLng, mapLat, 500.0, GeoUnit.M);
            for (GeoRadiusResponse geoRadiusList : geoRadiusLists) {
                String youKuName = geoRadiusList.getMemberByString();
                map.put(youKuName,heartTime);
            }
            resource.hmset(key,map);
            resource.expire(key,10);

        }

        //获取指定距离内的地点
        List<GeoRadiusResponse> c_oil_site = resource.georadius("c_oil_site", 111.092, 35.15, 10, GeoUnit.KM,GeoRadiusParam.geoRadiusParam().withCoord().withDist() );

        for (GeoRadiusResponse geoRadiusResponse : c_oil_site) {
            System.out.println(new String(geoRadiusResponse.getMember()));

            System.out.println(geoRadiusResponse.getCoordinate().getLongitude());
            System.out.println(geoRadiusResponse.getCoordinate().getLatitude());
            System.out.println(geoRadiusResponse.getDistance());

        }


    }
}
