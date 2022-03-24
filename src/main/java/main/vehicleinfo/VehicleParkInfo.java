package main.vehicleinfo;

public class VehicleParkInfo {
    private String licenseNumber; //车牌号
    private String poi; //停车点名称，如果是油站就是油站名，否则用地图服务的POI名称
    private String isGasStation; //是否加油站，1是0不是，对于0的点，我们会去调用地图服务获取地址
    private String enterTime; //进入停车区域时间
    private String leaveTime; //离开停车区域时间
    private String parkTimeDesc; //停车时间，以**小时**分这种文字描述
    private long parkTime; //停车时间，秒数
    private String province; //停车区域所在的省
    private String city; //停车区域所在的市
    private String conuntry; //停车区域所在的区
    private String address; //停车地址
    private double parkLng; //停车区域经度
    private double parkLat; //停车区域纬度

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public String getIsGasStation() {
        return isGasStation;
    }

    public void setIsGasStation(String isGasStation) {
        this.isGasStation = isGasStation;
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
}
