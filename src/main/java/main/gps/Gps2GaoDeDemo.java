package main.gps;

public class Gps2GaoDeDemo {
    //椭球参数
    static double pi = 3.14159265358979324;
    static double a = 6378245.0;
    static double ee = 0.00669342162296594323;

    /// <summary>
    /// Gps转高德
    /// </summary>
    /// <param name="wgLat">GPS纬度</param>
    /// <param name="wgLon">GPS经度</param>
    /// <param name="latlng">转化后的坐标</param>
    public static double[] transform(double wgLat, double wgLon)
    {
        double[] latlng = new double[2];//转化后的坐标
        if (outOfChina(wgLat, wgLon))
        {
            latlng[0] = wgLat;
            latlng[1] = wgLon;
            return latlng;
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        latlng[0] = wgLat + dLat;
        latlng[1] = wgLon + dLon;
        return latlng;
    }

    private static Boolean outOfChina(double lat, double lon)
    {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y)
    {
        double  ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y)
    {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
    public static void main(String[] args) {

        Double lon = 112.732405;
        Double lat = 37.669526;





        double[] transform = transform(lat,lon);
        System.out.println(transform[0]);
        System.out.println(transform[1]);


    }
}
