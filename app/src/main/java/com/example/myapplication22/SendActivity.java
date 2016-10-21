package com.example.myapplication22;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by 51099 on 2016/10/17.
 */
public class SendActivity extends BaseActivity {
    public static final int SHOW_LOCATION=0;  // add 1019

    private String TAG="SendActivity";
    private TextView positionTextView;
    private TextView positionTextView2;//add 10/20
    private LocationManager locationManager;
    private String provider;
    //private Location location2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        Button button=(Button) findViewById(R.id.send);  //add 1019
        positionTextView2 = (TextView) findViewById(R.id.position_text_view2);//add 10/20
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
            showshowLocation(location);  //add 10/20
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
        public Location location11=null;
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogUtil.d(TAG,"onStatusChanged");
            showLocation(location11);
            showshowLocation(location11); //add 10/20
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
            location11 = location;
            //更新当前设备的位置信息
            showLocation(location);
            showshowLocation(location); //add 10/20
            LogUtil.d(TAG,"onLocationChanged");
        }
    };

    private void showLocation(Location location){
        String currentPosition ="latitude is "+ location.getLatitude() + "\n" + "longitude is " + location.getLongitude();
        positionTextView.setText(currentPosition);
        LogUtil.d(TAG,"ShowPosition");
    }
    //add 10/20
    private void showshowLocation(final Location location){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //组装反向地理编码的接口地址
                    StringBuilder url= new StringBuilder();
                    url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
                    url.append(location.getLatitude()).append(",");
                    //double zhang1=location.getLatitude();
                    //LogUtil.i(TAG,"zhang"+zhang1);
                    url.append(location.getLongitude());
                    url.append("&sensor=false");
                    HttpClient httpClient=new DefaultHttpClient();
                    HttpGet httpGet=new HttpGet(url.toString());
                    //在请求消息中指定语言，保证服务器返回中文数据
                    httpGet.addHeader("Accept-language","zh-CN");
                    LogUtil.d(TAG,"333");
                    HttpResponse httpResponse= httpClient.execute(httpGet);
                    LogUtil.d(TAG,"33333333333");
                    LogUtil.d(TAG,"zhang"+httpResponse.getStatusLine().getStatusCode());
                    if(httpResponse.getStatusLine().getStatusCode()==200){
                        LogUtil.d(TAG,"4444");
                        HttpEntity entity= httpResponse.getEntity();
                        String response= EntityUtils.toString(entity,"utf-8");
                        JSONObject jsonObject=new JSONObject(response);
                        //获取results节点下的位置信息
                        JSONArray resultArray=jsonObject.getJSONArray("results");
                        LogUtil.d(TAG,"44444444444");
                        if (resultArray.length() > 0){
                            LogUtil.d(TAG,"55555555555");
                            JSONObject subObject =resultArray.getJSONObject(0);
                            //取出格式化后的位置信息
                            String address = subObject.getString("formatted_address");
                            Message message=new Message();
                            message.what = SHOW_LOCATION;
                            message.obj = address;
                            SendActivity.this.handler.sendMessage(message);
                            LogUtil.d(TAG,"had sendMessage to handler!!!!!!!!!");
                        }
                    } else {
                        LogUtil.e(TAG, "cannot !!!!!!!!!!!!");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler=new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case SHOW_LOCATION:
                  String currentPosition = (String) msg.obj;
                  positionTextView2.setText(currentPosition);
                  break;
              default:
                  break;
          }
      }
    };
}
