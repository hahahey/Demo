package main.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisDemo {
  public static void main(String[] args) throws InterruptedException {

    String host = "10.252.68.192";
    int port = 8004;
    String password = "PCITC2021@pcitc";
    JedisShardInfo jedis = new JedisShardInfo(host, port);
    jedis.setPassword(password);

    Jedis resource = jedis.createResource();
    // resource.set("c_sd_123456","鲁12345");

//    String key = "c_platenumber_expire" + "鲁RB1160";
//
//    Map<String, String> stringStringMap = resource.hgetAll(key);
//    for (Map.Entry<String, String> stringStringEntry : stringStringMap.entrySet()) {
//      System.out.println(stringStringEntry.getKey() + "___" + stringStringEntry.getValue());
//    }
//
//    System.out.println(resource.ttl(key));
//    System.out.println(resource.exists(key));


//    Set<String> keys = resource.keys("c_platenumber_expire晋M1156挂");
//    for (String key : keys) {
//
//      System.out.println(key +  "    " + resource.get(key));
//    }

    //Map<String, String> str = resource.hgetAll("c_京ABA962");
//    Map<String, String> str = resource.hgetAll("c_鲁RB1160");
//    for (Map.Entry<String, String> stringStringEntry : str.entrySet()) {
//      System.out.println(stringStringEntry.getKey() + "  " + stringStringEntry.getValue());
//    }


    System.out.println(resource.exists("testkey"));
    System.out.println(resource.get("testkey"));

//    Set<String> keys = resource.keys("c_京ABA962");
//    System.out.println(keys);
//    for (String key : keys) {
//      System.out.println(key);
//      resource.del(key);
//    }

    Long hset = resource.hset("test_lzk", "key2", "value2");
    System.out.println(hset);
//    Long test_lzk = resource.expire("test_lzk", 100);
//    System.out.println(test_lzk);
    System.out.println(resource.ttl("test_lzk"));
  }

  public static Map<String, String> hgetAll(Jedis resource,String key) {
    Map<String, String> valueMap = null;
      valueMap = resource.hgetAll(key);
    return valueMap;
  }
}
