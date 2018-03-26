package com.hjy.retrofitapi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * <pre>
 *     author : HJY
 *     time   : 2018/03/14/15:01
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class NetworkUtils {
    public static final int NETTYPE_NO = 0;
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_4G = 4;
    public static final int NETTYPE_UNKOWN = -1;
    public static final String China_Mobile = "CMCC";
    public static final String China_Unicom = "CUCC";
    public static final String China_Telecom = "CTCC";
    public static final String China_Unkown = "unkown";


    /**
     * 判断网络是否是可用
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        return NetworkUtils.getAvailableNetWorkInfo(context) == null;
    }
    public static int ProvidersName = -1;
    /**
     * 判断是否是3g网络
     * @return
     */
    public static boolean isThirdGeneration(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int netWorkType = telephonyManager.getNetworkType();
        switch (netWorkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:

                return false;
            default:
                return true;
        }
    }

    public static boolean isWifi(Context context) {

        NetworkInfo networkInfo = getAvailableNetWorkInfo(context);

        if (networkInfo != null) {

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }

        }

        return false;
    }

    public static NetworkInfo getAvailableNetWorkInfo(Context context) {
        NetworkInfo activeNetInfo = null;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (activeNetInfo != null && activeNetInfo.isAvailable()) {
            return activeNetInfo;
        } else {
            return null;
        }
    }

    public static String getNetWorkType(Context context) {

        String netWorkType = "2";
        NetworkInfo netWorkInfo = getAvailableNetWorkInfo(context);

        if (netWorkInfo != null) {
            if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                netWorkType = "1";
            } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = "0";
            }
        }
        return netWorkType;
    }

    public static int getNetType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
            if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                return NETTYPE_WIFI;
            } else {
                TelephonyManager telephonyManager = (TelephonyManager)context
                        .getSystemService(Context.TELEPHONY_SERVICE);

                switch (telephonyManager.getNetworkType()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return NETTYPE_2G;
                    case TelephonyManager.NETWORK_TYPE_LTE:   //4G
                        return NETTYPE_4G;
                    default:
                        return NETTYPE_3G;
                }
            }
        } else {
            return NETTYPE_NO;
        }
    }



    /**
     *
     */
    public static String getNetTypeForWebView(Context context){
        String type = "unknown";
        if (NetworkUtils.isWifi(context)){
            type = "wifi";
        }
        else {
            int netGeneration = NetworkUtils.getNetGeneration(context);
            switch (netGeneration){
                case NetworkUtils.NETTYPE_2G:
                    type = "2G";
                    break;
                case NetworkUtils.NETTYPE_3G:
                    type = "3G";
                    break;
                case NetworkUtils.NETTYPE_4G:
                    type = "4G";
                    break;
                case NetworkUtils.NETTYPE_UNKOWN:
                    type = "unknown";
                    break;
                case NetworkUtils.NETTYPE_NO:
                    type = "none";
                    break;
            }
        }
        return type;
    }


    /**
     * 获取当前移动网络是第几代
     */
    private static int getNetGeneration(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE);
            assert telephonyManager != null;
            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NETTYPE_2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NETTYPE_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:

                    return NETTYPE_4G;
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return NETTYPE_UNKOWN;
                default:
                    return NETTYPE_UNKOWN;
            }
        }
        else {
            return NETTYPE_NO;
        }
    }

    /**
     * 获取是哪家运营商
     */
    public static String getTelecomOperators(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        String imsi = "";
        if (networkInfo != null && networkInfo.isAvailable()) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE);
            assert telephonyManager != null;
            imsi = telephonyManager.getSubscriberId();
        }
        if(!TextUtils.isEmpty(imsi)){
            if(imsi.startsWith("46000") || imsi.startsWith("46002")){
                return China_Mobile;
            }
            else if(imsi.startsWith("46001")){
                return China_Unicom;
            }
            else if(imsi.startsWith("46003")){
                return China_Telecom;
            }
        }
        return China_Unkown;
    }
}
