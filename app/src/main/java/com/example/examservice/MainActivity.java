package com.example.examservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static int TIME = 2500 ;

    ImageView logoView ;
    TextView txtView ;
    Animation slide, bounce ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME);

        logoView = findViewById(R.id.logoImgView);
        txtView = findViewById(R.id.textImgView);
        slide = AnimationUtils.loadAnimation(this, R.anim.slide_animation);
        bounce = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        logoView.startAnimation(slide);
        txtView.startAnimation(bounce);
    }

}
