package com.example.myapplication22;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

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
    //for mp3
    public static boolean run=true;
    private Button play;
    private Button pause;
    private Button stop;
    private MediaPlayer mediaPlayer=new MediaPlayer();

    //for mp3
    public static final String TAG="DialogActivity";
    private  SeekBar progressBar;
    private ImageView imageview;
    private SeekBar seekbar;
    private TextView mustime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        //for mp3
        mustime = (TextView)findViewById(R.id.shichang);
        play = (Button)findViewById(R.id.button1);
        pause = (Button)findViewById(R.id.button2);
        stop = (Button)findViewById(R.id.button3);
        initMediaPlayer();//初始化MediaPlayer
//        play.setOnClickListener((View.OnClickListener) this);
//        pause.setOnClickListener((View.OnClickListener) this);
//        stop.setOnClickListener((View.OnClickListener) this);
        run=true;
        new Thread(new Runnable() {
            @Override
            public void run(){
                while (run) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if(mediaPlayer.isPlaying()) {
                        progressBar.setProgress((mediaPlayer.getCurrentPosition() * 100 / mediaPlayer.getDuration()));
                    }
                    LogUtil.e(TAG,""+run);
                }
            }
        }).start();
        //for mp3
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
        progressBar=(SeekBar) findViewById(R.id.seekBar);
        seekbar=(SeekBar) findViewById(R.id.progress_bar);
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
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(progressBar.getProgress()*(mediaPlayer.getDuration()/100));
                //mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG,"Music play");
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG," Music pause");
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG,"Music stop");
                mediaPlayer.reset();
                initMediaPlayer();
            }
        });
    }
    //广播监听1
    @Override
    protected void onDestroy() {
          DialogActivity.run=false;
          mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
        super.onDestroy();
        //for mp3
        if(mediaPlayer != null){
            LogUtil.d(TAG,"return MainActivity");
        }
        //for mp3
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
    //for mp3
    private void initMediaPlayer(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            mediaPlayer.setDataSource(file.getPath());//制定音频文件的路径
            mediaPlayer.prepare();//让MediaPlayer进入准备状态
            LogUtil.d(TAG,"时长"+mediaPlayer.getDuration()/1000);
            mustime.setText("AT:"+(mediaPlayer.getDuration()/1000)+"s");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //for mp3
}