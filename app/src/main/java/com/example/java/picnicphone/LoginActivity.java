package com.example.java.picnicphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onMainPageClick(View view) {
        Intent intent=new Intent(this,MemberActivity.class);
        startActivity(intent);
    }

    public void backMain2(View view) {
        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}
