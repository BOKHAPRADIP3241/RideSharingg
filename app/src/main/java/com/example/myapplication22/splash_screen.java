package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class splash_screen extends AppCompatActivity {
    private String userId= null;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        Log.d("splash", auth.getCurrentUser().getUid());
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //check



                finally {
                    Intent i;
                    if(userId!=null){
                        i = new Intent(splash_screen.this, bottom_navigation.class);
                    }
                    else {
                        i = new Intent(splash_screen.this, login.class);
                    }
                    startActivity(i);
                }
            }
        };thread.start();
    }
}