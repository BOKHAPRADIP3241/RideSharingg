package com.example.myapplication22;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class imageView extends AppCompatActivity {

    private String url;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        imageView = findViewById(R.id.imageView);

        url = getIntent().getStringExtra("url");
        Picasso
                .get()
                .load(url)
                .into(imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}