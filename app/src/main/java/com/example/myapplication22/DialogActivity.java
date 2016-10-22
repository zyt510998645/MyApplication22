package com.example.myapplication22;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 51099 on 2016/10/16.
 */
public class DialogActivity extends BaseActivity {
    //广播监听1
    private static int CurrentSysVol,CurrentMusVol;
    private static int maxSysVol,maxMusVol;
    private IntentFilter intentFilter1;
    private AudioChangeReceiver audioChangeReceiver;
    private AudioManager mAudioManager;
    //广播监听2
    public static final String TAG="DialogActivity";
    private  ProgressBar progressBar;
    private ImageView imageview;
    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        //广播监听1
        intentFilter1 = new IntentFilter();
        intentFilter1.addAction("android.media.VOLUME_CHANGED_ACTION");
        audioChangeReceiver = new AudioChangeReceiver();
        registerReceiver(audioChangeReceiver,intentFilter1);
        //广播监听2
        Button button4= (Button)findViewById(R.id.button4);
        Button button5=(Button)findViewById(R.id.button5);
        Button button6=(Button)findViewById(R.id.button6);

        imageview =(ImageView)findViewById(R.id.imageView);

        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        seekbar=(SeekBar) findViewById(R.id.seekBar);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);//实例化音频服务
        seekbar.setProgress(mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC )*5/3);  //将系统媒体音量写入条框seekbar

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {        //与button4.setOnClickListener类似的方法
            /**
             * 拖动条停止拖动的时候调用
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtil.d(TAG,"onStopTrackingTouch");
            }
            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LogUtil.d(TAG,"onStartTrackingTouch");
            }
            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                mAudioManager.setStreamVolume(mAudioManager.STREAM_MUSIC,(seekbar.getProgress()*3/5) ,mAudioManager.FLAG_PLAY_SOUND);
                LogUtil.d(TAG,"Vol Change to "+seekbar.getProgress());
                //将条框中的数值写入系统媒体音量
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress1= seekbar.getProgress();
                progress1=progress1+5;
                seekbar.setProgress(progress1);

                int progress= progressBar.getProgress();
                progress=progress+5;
                progressBar.setProgress(progress1);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog= new AlertDialog.Builder(DialogActivity.this);
                dialog.setTitle("This is a Dialog");
                dialog.setMessage("Something important!");
                dialog.setCancelable(false);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                            LogUtil.d(TAG, which + "  ok");
                            imageview.setImageResource(R.drawable.hhh);
                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        LogUtil.d(TAG,which+"  cancel");
                        imageview.setImageResource(R.drawable.zxc);
                    }
                });

                dialog.show();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog=new ProgressDialog(DialogActivity.this);
                progressDialog.setTitle("This is a ProgressDialog");
                progressDialog.setMessage("Laoding.....");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }
        });

    }
    //广播监听1
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(audioChangeReceiver);
    }
    class AudioChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            //Toast.makeText(context,"CurrentMusVol:"+(mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC )*5/3),Toast.LENGTH_SHORT).show();
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            maxSysVol = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
            CurrentSysVol= mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
            LogUtil.d(TAG,"MaxSysVol=" + maxSysVol+"      CurrentSysVol=" + CurrentSysVol);
            maxMusVol = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            CurrentMusVol = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
            LogUtil.d(TAG,"MaxMusVol=" + maxMusVol+"      CurrentMusVol=" + CurrentMusVol);
            seekbar.setProgress((CurrentMusVol*5/3));  // 将系统音量写入seekbar
            LogUtil.d(TAG,"Vol Change to "+(CurrentMusVol*5/3));
            LogUtil.d(TAG,"\n");
        }
    }
    //广播监听2
}