package main.gps;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.sql.*;

public class YouKuDemo {
    public static void main(String[] args) {


        Jedis resource = getResource();


        String url = "jdbc:mysql://10.252.68.192:8001/slm";
        String name = "com.mysql.jdbc.Driver";
        String userName = "slm";
        String passWd = "1QAZ2wsx";


        try {
            Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            Connection connection = DriverManager.getConnection(url, userName, passWd);
            Statement statement = connection.createStatement();
            String sql = "SELECT depot_code,pos_x,pos_y from slm_oildepot t\n" +
                    "INNER JOIN slm_entitysinopec tc ON tc.GUID = t.entityId \n" +
                    "WHERE tc.SECRITY_CODE like '300028%'  and pos_x is not null and pos_y is not null\n" +
                    "union all \n" +
                    "SELECT depot_code,pos_x,pos_y from slm_oildepot t\n" +
                    "INNER JOIN slm_entitysinopec tc ON tc.GUID = t.entityId \n" +
                    "WHERE tc.SECRITY_CODE like '300029%' and pos_x is not null and pos_y is not null";
            ResultSet resultSet = statement.executeQuery(sql);

            String key = "c_oil_site";

            while (resultSet.next()) {


                String member = resultSet.getString("depot_code");
                double longitude = resultSet.getDouble("pos_x");
                double latitude = resultSet.getDouble("pos_y");
                //将gps经纬度转为高德经纬度
                double[] transform = Gps2GaoDeDemo.transform(latitude, longitude);


                resource.geoadd(key, transform[1], transform[0], member);


            }


            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    private static Jedis getResource() {
        String host = "10.252.68.192";
        int port = 8004;
        String password = "PCITC2021@pcitc";
        JedisShardInfo jedis = new JedisShardInfo(host, port);
        jedis.setPassword(password);

        Jedis resource = jedis.createResource();
        return resource;
    }
}
