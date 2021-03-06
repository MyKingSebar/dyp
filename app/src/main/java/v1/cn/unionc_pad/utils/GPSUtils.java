package v1.cn.unionc_pad.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.baidu.aip.fl.utils.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v1.cn.unionc_pad.data.SPUtil;

/**
 * Created by Administrator on 2018/4/17.
 * 获取用户的地理位置
 */
public class GPSUtils {

    private static Context mContext;
    private static GPSUtils mInstance;
    public static final String TAG = "LocationUtils";

    private GPSUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static GPSUtils getInstance(Context context){
        if (mInstance == null){
            synchronized (GPSUtils.class){
                if (mInstance == null){
                    mInstance = new GPSUtils(context);
                    mContext = context;

                }
            }
            initLocation();
        }
        return mInstance;
    }

    static LocationManager locationManager;
    static Location location;
    static String provider;

    public static void initLocation() {

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            LogUtil.d(TAG,"No location provider to use");
            return;
        }

        location = locationManager.getLastKnownLocation(provider);
    /*if (location != null) {
        // 显示当前设备的位置信息
        showLocation(location);
    }*/
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);


    }

    /**
     * 获取位置信息
     * @return
     */
    public static Map<String, String> getLocation() {
        return showLocation(location);
    }


    public static Map<String, String> showLocation(Location location) {
        if(location == null)return null;
        Map<String, String> map = new HashMap<>();
        map.put("longitude", String.valueOf(location.getLongitude()));
        map.put("latitude", String.valueOf(location.getLatitude()));
        return map;
    }

    public static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void remove() {
        if (locationManager != null) {
            // 关闭程序时将监听器移除
            locationManager.removeUpdates(locationListener);
        }
    }
}