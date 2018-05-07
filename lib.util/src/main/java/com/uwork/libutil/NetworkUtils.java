package com.uwork.libutil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 李栋杰
 * @time 2018/2/6  下午5:55
 * @desc App网络管理
 */
public class NetworkUtils {

    /**
     * 方法汇总
     *
     * getNetworkState          : 获取当前手机连接的网络类型
     isNetworkConnected       : 判断网络是否连接
     openNetSetting           : 打开网络设置界面
     is3gConnected            : 检测3G是否连接
     getNetworkTypeName       : 获取网络类型名称
     getCurrentNetworkState   : 获取当前网络的状态
     getCurrentNetworkSubtype : 获取当前网络的具体类型
     isConnectedByState       : 判断当前网络是否已经连接
     isConnectingByState      : 判断当前网络是否正在连接
     isDisconnectedByState    : 判断当前网络是否已经断开
     isDisconnectingByState   : 判断当前网络是否正在断开
     isSuspendedByState       : 判断当前网络是否已经暂停
     isMobileByType           : 判断当前网络的类型是否是移动网络
     isWifiByType             : 判断当前网络的类型是否是Wifi
     isUNKNOWNBySubtype       : 判断当前网络的具体类型是否是UNKNOWN
     getWifiState             : 获取Wifi的状态，需要ACCESS_WIFI_STATE权限
     isWifiOpen               : 判断Wifi是否打开，需要ACCESS_WIFI_STATE权限
     setWifi                  : 设置Wifi，需要CHANGE_WIFI_STATE权限
     isMobileNetworkOpen      : 判断移动网络是否打开，需要ACCESS_NETWORK_STATE权限
     getIpAddress             : 获取本机IP地址
     setDataEnabled           : 设置数据流量状态
     getWifiScanResults       : 获取wifi列表
     getScanResultsByBSSID    : 过滤扫描结果
     getWifiConnectionInfo    : 获取wifi连接信息
     getProxy                 : 获得Proxy地址
     */

    //未找到合适匹配网络类型
    public static final int TYPE_NO = 0;

    //中国移动CMNET网络(中国移动GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立)
    public static final int TYPE_MOBILE_CMNET = 1;

    //中国移动CMWAP网络(中国移动GPRS接入方式之一,主要为手机WAP上网而设立)
    public static final int TYPE_MOBILE_CMWAP = 2;

    //中国联通UNIWAP网络(中国联通划分GPRS接入方式之一, 主要为手机WAP上网而设立)
    public static final int TYPE_MOBILE_UNIWAP = 3;

    //中国联通3GWAP网络
    public static final int TYPE_MOBILE_3GWAP = 4;

    //中国联通3HNET网络
    public static final int TYPE_MOBLIE_3GNET = 5;

    //中国联通UNINET网络(中国联通划分GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立)
    public static final int TYPE_MOBILE_UNINET = 6;

    //WIFI网络
    public static final int TYPE_WIFI = 10;

    /**
     * 网络类型 - 无连接
     */
    public static final int NETWORK_TYPE_NO_CONNECTION = -1231545315;

    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_3G = "eg";
    public static final String NETWORK_TYPE_2G = "2g";
    public static final String NETWORK_TYPE_WAP = "wap";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 获取当前手机连接的网络类型
     *
     * @param context 上下文
     * @return int 网络类型
     */
    public static int getNetworkState(Context context) {
        //获取ConnectivityManager对象
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获得当前网络信息
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            //获取网络类型
            int currentNetWork = networkInfo.getType();
            //手机网络类型
            if (currentNetWork == ConnectivityManager.TYPE_MOBILE) {
                if(networkInfo.getExtraInfo() != null){
                    if (networkInfo.getExtraInfo().equals("cmnet")) {
                        //当前网络为中国移动CMNET网络
                        return TYPE_MOBILE_CMNET;
                    }
                    if (networkInfo.getExtraInfo().equals("cmwap")) {
                        //当前网络为中国移动CMWAP网络
                        return TYPE_MOBILE_CMWAP;
                    }
                    if(networkInfo.getExtraInfo().equals("uniwap")) {
                        //AppNetworkMgr", "当前网络为中国联通UNIWAP网络
                        return TYPE_MOBILE_UNIWAP;
                    }
                    if(networkInfo.getExtraInfo().equals("3gwap")) {
                        //当前网络为中国联通3GWAP网络
                        return TYPE_MOBILE_3GWAP;
                    }
                    if(networkInfo.getExtraInfo().equals("3gnet")) {
                        //当前网络为中国联通3GNET网络
                        return TYPE_MOBLIE_3GNET;
                    }
                    if(networkInfo.getExtraInfo().equals("uninet")) {
                        //当前网络为中国联通UNINET网络
                        return TYPE_MOBILE_UNINET;
                    }
                    if(networkInfo.getExtraInfo().equals("ctwap")) {
                        //当前网络为中国电信CTWAP网络
                        return TYPE_MOBILE_UNINET;
                    }
                    if(networkInfo.getExtraInfo().equals("ctnet")) {
                        //当前网络为中国电信CTNET网络
                        return TYPE_MOBILE_UNINET;
                    }
                }
                //WIFI网络类型
            }else if (currentNetWork == ConnectivityManager.TYPE_WIFI) {
                //当前网络为WIFI网络
                return TYPE_WIFI;
            }
        }
        //当前网络为不是我们考虑的网络
        return TYPE_NO;
    }

    /**
     * 判断网络是否连接
     *
     * @param context 上下文
     * @return boolean 网络连接状态
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            //获取连接对象
            if (mNetworkInfo != null) {
                //判断是TYPE_MOBILE网络
                if(ConnectivityManager.TYPE_MOBILE == mNetworkInfo.getType()){
                    //判断移动网络连接状态
                    NetworkInfo.State STATE_MOBILE = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                    if (STATE_MOBILE == NetworkInfo.State.CONNECTED) {
                        return mNetworkInfo.isAvailable();
                    }
                }
                //判断是TYPE_WIFI网络
                if(ConnectivityManager.TYPE_WIFI == mNetworkInfo.getType()){
                    //判断WIFI网络状态
                    NetworkInfo.State STATE_WIFI = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                    if (STATE_WIFI == NetworkInfo.State.CONNECTED) {
                        return mNetworkInfo.isAvailable();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @param activity Activity
     * @return boolean 网络连接状态
     */
    public static boolean isNetworkConnected(Activity activity){
        boolean falg = false;
        @SuppressLint("WrongConstant") ConnectivityManager mConnectivityManager = (ConnectivityManager) activity.getApplicationContext().getSystemService("connectivity");
        if (mConnectivityManager == null){
            return falg;
        }
        NetworkInfo[] arrayOfNetworkInfo = mConnectivityManager.getAllNetworkInfo();
        if (arrayOfNetworkInfo != null){
            for (int j = 0; j < arrayOfNetworkInfo.length; j++){
                if (arrayOfNetworkInfo[j].getState() == NetworkInfo.State.CONNECTED){
                    falg = true;
                    break;
                }
            }
        }
        return falg;
    }

    /**
     * 打开网络设置界面
     *
     * @param activity Activity
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 检测3G是否连接
     *
     * @param context 上下文
     * @return 结果
     */
    public static boolean is3gConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get network type
     *
     * @param context context
     * @return NetworkType
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null
                ? null
                : connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }


    /**
     * Get network type name
     *
     * @param context context
     * @return NetworkTypeName
     */
    public static String getNetworkTypeName(Context context) {
        ConnectivityManager manager
                = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        String type = NETWORK_TYPE_DISCONNECT;
        if (manager == null ||
                (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return type;
        }
        ;

        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                type = NETWORK_TYPE_WIFI;
            }
            else if ("MOBILE".equalsIgnoreCase(typeName)) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                type = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context)
                        ? NETWORK_TYPE_3G
                        : NETWORK_TYPE_2G)
                        : NETWORK_TYPE_WAP;
            }
            else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }


    /**
     * Whether is fast mobile network
     *
     * @param context context
     * @return FastMobileNetwork
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager
                = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }


    /**
     * 获取当前网络的状态
     *
     * @param context 上下文
     * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
     */
    public static NetworkInfo.State getCurrentNetworkState(Context context) {
        NetworkInfo networkInfo
                = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getState() : null;
    }


    /**
     * 获取当前网络的类型
     *
     * @param context 上下文
     * @return 当前网络的类型。具体类型可参照ConnectivityManager中的TYPE_BLUETOOTH、TYPE_MOBILE、TYPE_WIFI等字段。当前没有网络连接时返回NetworkUtils.NETWORK_TYPE_NO_CONNECTION
     */
    public static int getCurrentNetworkType(Context context) {
        NetworkInfo networkInfo
                = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null
                ? networkInfo.getType()
                : NETWORK_TYPE_NO_CONNECTION;
    }


    /**
     * 获取当前网络的具体类型
     *
     * @param context 上下文
     * @return 当前网络的具体类型。具体类型可参照TelephonyManager中的NETWORK_TYPE_1xRTT、NETWORK_TYPE_CDMA等字段。当前没有网络连接时返回NetworkUtils.NETWORK_TYPE_NO_CONNECTION
     */
    public static int getCurrentNetworkSubtype(Context context) {
        NetworkInfo networkInfo
                = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null
                ? networkInfo.getSubtype()
                : NETWORK_TYPE_NO_CONNECTION;
    }


    /**
     * 判断当前网络是否已经连接
     *
     * @param context 上下文
     * @return 当前网络是否已经连接。false：尚未连接
     */
    public static boolean isConnectedByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTED;
    }


    /**
     * 判断当前网络是否正在连接
     *
     * @param context 上下文
     * @return 当前网络是否正在连接
     */
    public static boolean isConnectingByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTING;
    }


    /**
     * 判断当前网络是否已经断开
     *
     * @param context 上下文
     * @return 当前网络是否已经断开
     */
    public static boolean isDisconnectedByState(Context context) {
        return getCurrentNetworkState(context) ==
                NetworkInfo.State.DISCONNECTED;
    }


    /**
     * 判断当前网络是否正在断开
     *
     * @param context 上下文
     * @return 当前网络是否正在断开
     */
    public static boolean isDisconnectingByState(Context context) {
        return getCurrentNetworkState(context) ==
                NetworkInfo.State.DISCONNECTING;
    }


    /**
     * 判断当前网络是否已经暂停
     *
     * @param context 上下文
     * @return 当前网络是否已经暂停
     */
    public static boolean isSuspendedByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.SUSPENDED;
    }


    /**
     * 判断当前网络是否处于未知状态中
     *
     * @param context 上下文
     * @return 当前网络是否处于未知状态中
     */
    public static boolean isUnknownByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.UNKNOWN;
    }


    /**
     * 判断当前网络的类型是否是蓝牙
     *
     * @param context 上下文
     * @return 当前网络的类型是否是蓝牙。false：当前没有网络连接或者网络类型不是蓝牙
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static boolean isBluetoothByType(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return false;
        }
        else {
            return getCurrentNetworkType(context) ==
                    ConnectivityManager.TYPE_BLUETOOTH;
        }
    }


    /**
     * 判断当前网络的类型是否是移动网络
     *
     * @param context 上下文
     * @return 当前网络的类型是否是移动网络。false：当前没有网络连接或者网络类型不是移动网络
     */
    public static boolean isMobileByType(Context context) {
        return getCurrentNetworkType(context) ==
                ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 判断当前网络的类型是否是Wifi
     *
     * @param context 上下文
     * @return 当前网络的类型是否是Wifi。false：当前没有网络连接或者网络类型不是wifi
     */
    public static boolean isWifiByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断当前网络的具体类型是否是GPRS
     * EVDO_Bam context 上下文
     *
     * @return false：当前网络的具体类型是否是GPRS。false：当前没有网络连接或者具体类型不是GPRS
     */
    public static boolean isGPRSBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) ==
                TelephonyManager.NETWORK_TYPE_GPRS;
    }





    /**
     * 获取Wifi的状态，需要ACCESS_WIFI_STATE权限
     *
     * @param context 上下文
     * @return 取值为WifiManager中的WIFI_STATE_ENABLED、WIFI_STATE_ENABLING、WIFI_STATE_DISABLED、WIFI_STATE_DISABLING、WIFI_STATE_UNKNOWN之一
     * @throws Exception 没有找到wifi设备
     */
    public static int getWifiState(Context context) throws Exception {
        WifiManager wifiManager = ((WifiManager) context.getSystemService(
                Context.WIFI_SERVICE));
        if (wifiManager != null) {
            return wifiManager.getWifiState();
        }
        else {
            throw new Exception("wifi device not found!");
        }
    }

    /**
     * 判断Wifi是否打开，需要ACCESS_WIFI_STATE权限
     *
     * @param context 上下文
     * @return true：打开；false：关闭
     */
    public static boolean isWifiOpen(Context context) throws Exception {
        int wifiState = getWifiState(context);
        return wifiState == WifiManager.WIFI_STATE_ENABLED ||
                wifiState == WifiManager.WIFI_STATE_ENABLING
                ? true
                : false;
    }


    /**
     * 设置Wifi，需要CHANGE_WIFI_STATE权限
     *
     * @param context 上下文
     * @param enable wifi状态
     * @return 设置是否成功
     */
    public static boolean setWifi(Context context, boolean enable)
            throws Exception {
        //如果当前wifi的状态和要设置的状态不一样
        if (isWifiOpen(context) != enable) {
            ((WifiManager) context.getSystemService(
                    Context.WIFI_SERVICE)).setWifiEnabled(enable);
        }
        return true;
    }

    /**
     * 判断移动网络是否打开，需要ACCESS_NETWORK_STATE权限
     *
     * @param context 上下文
     * @return true：打开；false：关闭
     */
    public static boolean isMobileNetworkOpen(Context context) {
        return (((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE)).isConnected();
    }

    /**
     * 获取本机IP地址
     *
     * @return null：没有网络连接
     */
    public static String getIpAddress() {
        try {
            NetworkInterface nerworkInterface;
            InetAddress inetAddress;
            for (Enumeration<NetworkInterface> en
                 = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                nerworkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr
                     = nerworkInterface.getInetAddresses();
                     enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
            return null;
        } catch (SocketException ex) {
            ex.printStackTrace();
            return null;
        }
    }



    /**
     * 获取wifi列表
     * @param context
     * @return
     */
    public static List<ScanResult> getWifiScanResults(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.startScan() ? wifiManager.getScanResults() : null;
    }

    /**
     * 过滤扫描结果
     * @param context
     * @param bssid
     * @return
     */
    public static ScanResult getScanResultsByBSSID(Context context, String bssid) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        ScanResult scanResult = null;
        boolean f = wifiManager.startScan();
        if (!f) {
            getScanResultsByBSSID(context, bssid);
        }
        List<ScanResult> list = wifiManager.getScanResults();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                scanResult = list.get(i);
                if (scanResult.BSSID.equals(bssid)) {
                    break;
                }
            }
        }
        return scanResult;
    }

    /**
     * 获取wifi连接信息
     * @param context
     * @return
     */
    public static WifiInfo getWifiConnectionInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo();
    }

    /**
     * 获得Proxy地址
     *
     * @param context 上下文
     * @return Proxy地址
     */
    public static String getProxy(Context context) {
        String proxy = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isAvailable()) {
                String stringExtraInfo = networkinfo.getExtraInfo();
                if (stringExtraInfo != null && ("cmwap".equals(stringExtraInfo) || "uniwap".equals(stringExtraInfo))) {
                    proxy = "10.0.0.172:80";
                } else if (stringExtraInfo != null && "ctwap".equals(stringExtraInfo)) {
                    proxy = "10.0.0.200:80";
                }
            }
        }

        return proxy;
    }
}
