package main.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author locks
 * @date 2022-02-21 14:07
 */
public class Context {
    private String name;
    private final DataStrategy dataStrategy;
    private final String configPath;

    public Context(String name, String configPath) {
        this.name = name;
        this.dataStrategy = DealWithDataEnum.getDataStrategyFromName(name);
         this.configPath = configPath;
    }


    public Map<String, String> initProperty() {
        return new HashMap<>(10);
    }


    /**
     * 数据处理
     *
     * @param propMap
     */
    public void dealWithData(Map<String, String> propMap) {
        dataStrategy.dealWithData(propMap);
    }
}
