package com.example.aman.hospitalappointy;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView mSplashText;
    private ImageView mSplashLogo;
    private Animation uptodown;
    private Animation downtoup;
//    private TextView d,n,t,a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mSplashText = (ImageView) findViewById(R.id.splash_logo1);
        mSplashLogo = (ImageView) findViewById(R.id.splash_logo);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
//        d=(TextView)findViewById(R.id.divya);
//        n=(TextView)findViewById(R.id.narendra);
//        t=(TextView)findViewById(R.id.tejas);
//        a=(TextView)findViewById(R.id.aman);


        mSplashText.setAnimation(downtoup);
        mSplashLogo.setAnimation(uptodown);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally
                {
                    Intent main_Intent= new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(main_Intent);
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
