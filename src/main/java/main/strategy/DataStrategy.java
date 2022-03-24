package main.strategy;

import java.util.Map;

public interface DataStrategy {
    /**
     * 数据处理算法接口
     */
    void dealWithData(Map<String,String> propMap);
}
