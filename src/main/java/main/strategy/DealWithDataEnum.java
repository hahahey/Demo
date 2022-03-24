package main.strategy;

import org.apache.commons.lang3.StringUtils;

public enum DealWithDataEnum {
    /**
     * 山西实时
     */
    SX_REAL("sx_real", new ShanXiRealStrategy()),
    /**
     * 山西历史
     */
    SX_HIS("sx_his", new ShanXiHisStrategy()),
    /**
     * 山东车牌号信息
     */
    SD_TRUCK("sd_truck", new ShanDongRealAndHisStrategyTruck()),

    /**
     * 山东时间及经纬度信息
     */
    SD_DATE_INFO("sd_date_info", new ShanDongRealAndHisStrategyDateInfo()),

    /**
     * 河北实时和历史
     */
    HB_REAL_HIS("hb_real_his", new HebeiRealAndHisStrategy());





    private String name;
    private DataStrategy dataStrategy;


    DealWithDataEnum(String name, DataStrategy dataStrategy) {
        this.name = name;
        this.dataStrategy = dataStrategy;
    }

    /**
     *  根据传入的策略名称寻找具体的执行类
     * @param name
     * @return
     */
    public static DataStrategy getDataStrategyFromName(String name) {
        DataStrategy dataStrategy = null;
        for (DealWithDataEnum value : DealWithDataEnum.values()) {
            if(StringUtils.equalsIgnoreCase(value.getName(),name)){
                dataStrategy = value.getDataStrategy();
                break;
            }
        }
        return dataStrategy;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public DataStrategy getDataStrategy() {
        return dataStrategy;
    }

    public void setDataStrategy(DataStrategy dataStrategy) {
        this.dataStrategy = dataStrategy;
    }

}
