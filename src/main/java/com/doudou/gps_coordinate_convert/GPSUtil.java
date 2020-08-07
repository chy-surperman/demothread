package com.doudou.gps_coordinate_convert;

import java.io.PrintStream;
import java.util.Arrays;

public class GPSUtil
{
    public static double pi = 3.141592653589793D;
    public static double x_pi = 52.35987755982988D;
    public static double a = 6378245.0D;
    public static double ee = 0.006693421622965943D;
    private static double EARTH_RADIUS = 6378.137D;

    public static double transformLat(double x, double y)
    {
        double ret = -100.0D + 2.0D * x + 3.0D * y + 0.2D * y * y + 0.1D * x * y + 0.2D * Math.sqrt(Math.abs(x));
        ret += (20.0D * Math.sin(6.0D * x * pi) + 20.0D * Math.sin(2.0D * x * pi)) * 2.0D / 3.0D;
        ret += (20.0D * Math.sin(y * pi) + 40.0D * Math.sin(y / 3.0D * pi)) * 2.0D / 3.0D;
        ret += (160.0D * Math.sin(y / 12.0D * pi) + 320.0D * Math.sin(y * pi / 30.0D)) * 2.0D / 3.0D;
        return ret;
    }

    public static double transformLon(double x, double y)
    {
        double ret = 300.0D + x + 2.0D * y + 0.1D * x * x + 0.1D * x * y + 0.1D * Math.sqrt(Math.abs(x));
        ret += (20.0D * Math.sin(6.0D * x * pi) + 20.0D * Math.sin(2.0D * x * pi)) * 2.0D / 3.0D;
        ret += (20.0D * Math.sin(x * pi) + 40.0D * Math.sin(x / 3.0D * pi)) * 2.0D / 3.0D;
        ret += (150.0D * Math.sin(x / 12.0D * pi) + 300.0D * Math.sin(x / 30.0D * pi)) * 2.0D / 3.0D;
        return ret;
    }

    public static double[] transform(double lat, double lon)
    {
        if (outOfChina(lat, lon)) {
            return new double[] { lat, lon };
        }
        double dLat = transformLat(lon - 105.0D, lat - 35.0D);
        double dLon = transformLon(lon - 105.0D, lat - 35.0D);
        double radLat = lat / 180.0D * pi;
        double magic = Math.sin(radLat);
        magic = 1.0D - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = dLat * 180.0D / (a * (1.0D - ee) / (magic * sqrtMagic) * pi);
        dLon = dLon * 180.0D / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[] { mgLat, mgLon };
    }

    public static boolean outOfChina(double lat, double lon)
    {
        if ((lon < 72.004D) || (lon > 137.8347D)) {
            return true;
        }
        if ((lat < 0.8293D) || (lat > 55.8271D)) {
            return true;
        }
        return false;
    }

    public static double[] gps84_To_Gcj02(double lat, double lon)
    {
        if (outOfChina(lat, lon)) {
            return new double[] { lat, lon };
        }
        double dLat = transformLat(lon - 105.0D, lat - 35.0D);
        double dLon = transformLon(lon - 105.0D, lat - 35.0D);
        double radLat = lat / 180.0D * pi;
        double magic = Math.sin(radLat);
        magic = 1.0D - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = dLat * 180.0D / (a * (1.0D - ee) / (magic * sqrtMagic) * pi);
        dLon = dLon * 180.0D / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[] { mgLat, mgLon };
    }

    public static double[] gcj02_To_Gps84(double lat, double lon)
    {
        double[] gps = transform(lat, lon);
        double lontitude = lon * 2.0D - gps[1];
        double latitude = lat * 2.0D - gps[0];
        return new double[] { latitude, lontitude };
    }

    public static double[] gcj02_To_Bd09(double lat, double lon)
    {
        double x = lon;double y = lat;
        double z = Math.sqrt(x * x + y * y) + 2.0E-5D * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 3.0E-6D * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065D;
        double tempLat = z * Math.sin(theta) + 0.006D;
        double[] gps = { tempLat, tempLon };
        return gps;
    }

    public static double[] bd09_To_Gcj02(double lat, double lon)
    {
        double x = lon - 0.0065D;double y = lat - 0.006D;
        double z = Math.sqrt(x * x + y * y) - 2.0E-5D * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 3.0E-6D * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = { tempLat, tempLon };
        return gps;
    }

    public static double[] gps84_To_bd09(double lat, double lon)
    {
        double[] gcj02 = gps84_To_Gcj02(lat, lon);
        double[] bd09 = gcj02_To_Bd09(gcj02[0], gcj02[1]);
        return bd09;
    }

    public static double[] bd09_To_gps84(double lat, double lon)
    {
        double[] gcj02 = bd09_To_Gcj02(lat, lon);
        double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);

        gps84[0] = retain6(gps84[0]);
        gps84[1] = retain6(gps84[1]);
        return gps84;
    }

    private static double retain6(double num)
    {
        String result = String.format("%.6f", new Object[] { Double.valueOf(num) });
        return Double.valueOf(result).doubleValue();
    }

    private static double rad(double d)
    {
        return d * 3.141592653589793D / 180.0D;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));

        s *= EARTH_RADIUS;
        s = Math.round(s * 10000.0D) / 10000.0D;
        s *= 1000.0D;
        return s;
    }

    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(gcj02_To_Gps84(28.226034D, 113.153991D)));

        System.out.println(Arrays.toString(gps84_To_Gcj02(28.230375D, 113.146891D)));
    }
}
