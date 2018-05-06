package com.microwise.common.util;

/**
 * 坐标转换工具类
 * <p>
 * 各地图API坐标系统比较与转换;
 * <p>
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * <p>
 * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系;
 * <p>
 * BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
 * <p>
 * 搜狗坐标系、图吧坐标系等，也是在GCJ02基础上加密而成的。
 *
 * @author 王耕
 * @date 2015-1-30
 */
public class GpsUtil {
    private static double pi = 3.1415926535897932384626;
    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;

    /**
     * 84 to 火星坐标系 (GCJ-02)
     * Gps=>Google
     *
     * @param lat
     * @param lon
     * @return
     */
    public static Gps gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return null;
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;

        return new Gps(mgLat, mgLon);
    }

    /**
     * 火星坐标系 (GCJ-02) to 84
     * Google=>Gps
     *
     * @param lon
     * @param lat
     * @return
     */
    public Gps gcj_To_Gps84(double lat, double lon) {
        Gps gps = transform(lat, lon);
        double lontitude = lon * 2 - gps.getWgLon();
        double latitude = lat * 2 - gps.getWgLat();

        return new Gps(latitude, lontitude);

    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法
     * Google=>Baidu
     *
     * @param gg_lat
     * @param gg_lon
     */
    public static Gps gcj02_To_Bd09(double gg_lat, double gg_lon) {
        double x = gg_lon, y = gg_lat;

        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);

        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);

        double bd_lon = z * Math.cos(theta) + 0.0065;

        double bd_lat = z * Math.sin(theta) + 0.006;

        return new Gps(bd_lat, bd_lon);
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法
     * Baidu=>Google
     *
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static Gps bd09_To_Gcj02(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;

        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);

        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);

        double gg_lon = z * Math.cos(theta);

        double gg_lat = z * Math.sin(theta);

        return new Gps(gg_lat, gg_lon);
    }

    /**
     * 火星坐标系 (GCJ-02) to 84
     *
     * @param lat
     * @param lon
     * @return
     */
    private Gps transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new Gps(lat, lon);
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;

        return new Gps(mgLat, mgLon);
    }

    /**
     * 经度转换计算算法
     *
     * @param x
     * @param y
     * @return
     */
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 纬度转换计算算法
     *
     * @param x
     * @param y
     * @return
     */
    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 判断坐标是否超出中国范围
     *
     * @param lat
     * @param lon
     * @return
     */
    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    /**
     * 坐标实体，内部类
     */
    public static class Gps {
        private double wgLat;
        private double wgLon;

        public Gps(double wgLat, double wgLon) {
            this.wgLat = wgLat;
            this.wgLon = wgLon;
        }

        public double getWgLat() {
            return wgLat;
        }

        public void setWgLat(double wgLat) {
            this.wgLat = wgLat;
        }

        public double getWgLon() {
            return wgLon;
        }

        public void setWgLon(double wgLon) {
            this.wgLon = wgLon;
        }

        @Override
        public String toString() {
            return wgLat + "," + wgLon;
        }
    }
}
