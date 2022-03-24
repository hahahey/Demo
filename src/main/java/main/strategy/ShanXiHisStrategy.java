package main.strategy;

import java.util.Map;

/**
 * @author locks
 * @date 2022-02-21 14:01
 */
public class ShanXiHisStrategy implements DataStrategy{
    static {
        System.out.println("ShanXiHisStrategy");
    }
    @Override
    public void dealWithData(Map<String, String> propMap) {
        System.out.println("ShanXiHisStrategy");
    }
}
