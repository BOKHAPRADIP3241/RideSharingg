package com.example.myapplication22;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class specificchat extends AppCompatActivity {
    String userId,upUserId;
    ImageButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificchat);
        userId = getIntent().getStringExtra("userId");
        upUserId = getIntent().getStringExtra("upUserId");
    }
}
