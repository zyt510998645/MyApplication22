package com.example.myapplication22;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by 51099 on 2016/10/16.
 */
public class NormalActivity extends BaseActivity {
    private ProgressBar progressBar;
    private ImageView imageView;
    private ImageView imageView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);

        Button button1= (Button) findViewById(R.id.send_request);
        Button button3=(Button) findViewById(R.id.button3);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishAll();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (progressBar.getVisibility()==View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                    imageView=(ImageView) findViewById(R.id.image_view);
                    imageView.setImageResource(R.drawable.hhh);
                } else {
                    progressBar.setVisibility(View.GONE);
                    imageView1=(ImageView) findViewById(R.id.image_view);
                    imageView1.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });
    }
}
