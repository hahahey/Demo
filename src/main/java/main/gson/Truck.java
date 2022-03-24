package main.gson;

public class Truck {
    private String truckMc;
    private String truckId;
    private String cysMc;

    public String getTruckMc() {
        return truckMc;
    }

    public void setTruckMc(String truckMc) {
        this.truckMc = truckMc;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public String getCysMc() {
        return cysMc;
    }

    public void setCysMc(String cysMc) {
        this.cysMc = cysMc;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "truckMc='" + truckMc + '\'' +
                ", truckId='" + truckId + '\'' +
                ", cysMc='" + cysMc + '\'' +
                '}';
    }
}
