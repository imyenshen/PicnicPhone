package com.example.java.picnicphone;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Drawable p01 = null;
        Drawable p02 = null;
        Drawable p03 = null;

        Resources res = getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            p01 = res.getDrawable(R.drawable.a1, null);
            p02 = res.getDrawable(R.drawable.a2, null);
            p03 = res.getDrawable(R.drawable.a3, null);
        } else {
            p01 = res.getDrawable(R.drawable.a1);
            p02 = res.getDrawable(R.drawable.a2);
            p03 = res.getDrawable(R.drawable.a3);
        }

        animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(false);
        int duration = 2000;
        animationDrawable.addFrame(p01, duration);
        animationDrawable.addFrame(p02, duration);
        animationDrawable.addFrame(p03, duration);

        ImageView ivPicture = (ImageView) findViewById(R.id.ivPicture);
        ivPicture.setBackground(animationDrawable);
        animationDrawable.start();

    }
    public void onLoginClick(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
