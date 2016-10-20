package com.example.myapplication22;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by 51099 on 2016/10/17.
 */
public class SendActivity extends BaseActivity {
    //public static final int SHOW_LOCATION=0;  // add 1019
    //Button button=(Button) findViewById(R.id.send);  //add 1019

    private String TAG="SendActivity";
    private TextView positionTextView;
    private LocationManager locationManager;
    private String provider;
    //private Location location2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);

        positionTextView = (TextView) findViewById(R.id.position_text_view);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);

        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            LogUtil.d(TAG,"use GPS_PROVIDER");
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
            LogUtil.d(TAG,"use NETWORK_PROVIDER");
        } else {
            //当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(this, "No location provider use", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            //显示当前设备的位置信息
            showLocation(location);
            LogUtil.d(TAG,"showLocation(location)");
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //关闭程序时将监听器移除
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(locationListener);
            LogUtil.d(TAG,"+removeUpdates");
        }
    }

    LocationListener locationListener=new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogUtil.d(TAG,"onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider){
            LogUtil.d(TAG,"onProviderEnabled");
        }
        @Override
        public void onProviderDisabled(String provider){
            LogUtil.d(TAG,"onProviderDisabled");
        }

        @Override
        public void onLocationChanged(Location location) {
            //更新当前设备的位置信息
            showLocation(location);
            LogUtil.d(TAG,"onLocationChanged");
        }
    };

    private void showLocation(Location location){
        String currentPosition ="latitude is "+ location.getLatitude() + "\n" + "longitude is " + location.getLongitude();
        positionTextView.setText(currentPosition);
        LogUtil.d(TAG,"ShowPosition");
    }
}
