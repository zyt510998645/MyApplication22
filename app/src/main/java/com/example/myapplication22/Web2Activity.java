package com.example.myapplication22;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 51099 on 2016/10/17.
 */
public class Web2Activity extends BaseActivity implements View.OnClickListener {
    private String TAG = "Web2Activity";
    public static final int SHOW_RESPONSE=0;
    private Button sendRequest;
    private TextView responseText;
    String response;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String) msg.obj;
                    responseText.setText(response);
                    LogUtil.d( TAG,"+Response");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web2_layout);
        sendRequest=(Button) findViewById(R.id.send_request);
        responseText=(TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick (View v){
        if(v.getId()==R.id.send_request){
            sendRequestWithHttpURLConnection();
            LogUtil.d(TAG,"OnClick");
        }
    }
    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtil.d(TAG, "run: start to get url request");
                    HttpClient httpClient=new DefaultHttpClient();
                    HttpGet httpGet=new HttpGet("http://www.baidu.com");
                    HttpResponse httpResponse=httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode()==200){
                        LogUtil.d(TAG,"Request and Response SUCCESS");
                        HttpEntity entity=httpResponse.getEntity();
                        response= EntityUtils.toString(entity,"utf-8");
                    }
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = response.toString();

                    handler.sendMessage(message);
                    LogUtil.d(TAG,"+handleMessage or sendMessage");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            }).start();

        }
}
