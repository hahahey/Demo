package main.vehicleinfo;

/**
 * 本来保存进入并且驶离了目标油站的配送期间的停车点
 * 所有字段为结果表中的字段
 */
public class ParkResult {
    private String deliveryId; //配送单号
    private String licenseNumber; //车牌号
    private String oilDepot; //油库
    private String gasStation; //油站
    private String oilTyep; //油品类别
    private String departTime;//出库时间
    private String poi; //POI
    private String province; //停车区域所在的省
    private String city; //停车区域所在的市
    private String conuntry; //停车区域所在的区
    private String address; //停车地址
    private String enterTime; //进入停车区域时间
    private String leaveTime; //离开停车区域时间
    private String parkTimeDesc; //停车时间，以**小时**分这种文字描述
    private long parkTime; //停车时间，秒数
    private double parkLng; //停车区域经度
    private double parkLat; //停车区域纬度

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOilTyep() {
        return oilTyep;
    }

    public void setOilTyep(String oilTyep) {
        this.oilTyep = oilTyep;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getOilDepot() {
        return oilDepot;
    }

    public void setOilDepot(String oilDepot) {
        this.oilDepot = oilDepot;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public double getParkLng() {
        return parkLng;
    }

    public void setParkLng(double parkLng) {
        this.parkLng = parkLng;
    }

    public double getParkLat() {
        return parkLat;
    }

    public void setParkLat(double parkLat) {
        this.parkLat = parkLat;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConuntry() {
        return conuntry;
    }

    public void setConuntry(String conuntry) {
        this.conuntry = conuntry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getParkTimeDesc() {
        return parkTimeDesc;
    }

    public void setParkTimeDesc(String parkTimeDesc) {
        this.parkTimeDesc = parkTimeDesc;
    }

    public long getParkTime() {
        return parkTime;
    }

    public void setParkTime(long parkTime) {
        this.parkTime = parkTime;
    }
}
