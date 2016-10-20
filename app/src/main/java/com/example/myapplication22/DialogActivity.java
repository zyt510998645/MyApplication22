package com.example.myapplication22;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 51099 on 2016/10/16.
 */
public class DialogActivity extends BaseActivity {
    private  ProgressBar progressBar;
    private ImageView imageview;
    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        Button button4= (Button)findViewById(R.id.button4);
        Button button5=(Button)findViewById(R.id.button5);
        Button button6=(Button)findViewById(R.id.button6);

        imageview =(ImageView)findViewById(R.id.imageView);

        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        seekbar=(SeekBar) findViewById(R.id.seekBar);


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
                        if(seekbar.getProgress()==50) {
                            LogUtil.d("zhangyt", which + "  ok");
                            imageview.setImageResource(R.drawable.hhh);
                        }
                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        LogUtil.d("zhangyt",which+"  cancel");
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
}
