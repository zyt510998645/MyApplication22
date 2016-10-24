package com.example.myapplication22;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by 51099 on 2016/10/16.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        LogUtil.d("BaseActivity","Goto :"+getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        LogUtil.d("BaseActivity","Destroy :"+getClass().getSimpleName());
    }
}
