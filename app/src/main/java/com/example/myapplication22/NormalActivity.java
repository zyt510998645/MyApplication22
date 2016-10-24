package com.example.myapplication22;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by 51099 on 2016/10/16.
 */
public class NormalActivity extends BaseActivity implements View.OnClickListener {
    private ProgressBar progressBar;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);

        Button button1= (Button) findViewById(R.id.send_request);
        Button play=(Button) findViewById(R.id.button1);
        Button pause=(Button) findViewById(R.id.button2);
        Button replay= (Button)findViewById(R.id.button3);
        videoView=(VideoView)findViewById(R.id.videoView);
        
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        //progressBar.setVisibility(View.VISIBLE);
        //if (progressBar.getVisibility()==View.GONE)
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishAll();
            }
        });
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(NormalActivity.this);
        initVideoPath();
    }
    private void initVideoPath(){
        File file=new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        videoView.setVideoPath(file.getPath());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.button2:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.button3:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(videoView != null){
            videoView.suspend();
        }
    }
}
