package com.example.myapplication22.upload;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication22.R;
import com.example.myapplication22.bottom_navigation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class upload_status extends AppCompatActivity {

    TextView txt_us_from,txt_us_to,txt_us_date,txt_us_time,txt_us_seats,txt_us_cost,txt_us_car;
    FirebaseFirestore dbroot;
    ImageButton back;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_status);

        getSupportActionBar().hide();

        txt_us_from = (TextView)findViewById(R.id.txt_us_from);
        txt_us_to = (TextView)findViewById(R.id.txt_us_to);
        txt_us_date = (TextView)findViewById(R.id.txt_us_date);
        txt_us_time = (TextView)findViewById(R.id.txt_us_time);
        txt_us_seats = (TextView)findViewById(R.id.txt_us_seats);
        txt_us_cost = (TextView)findViewById(R.id.txt_us_cost);
        txt_us_car = (TextView)findViewById(R.id.txt_us_car);

        back = (ImageButton) findViewById(R.id.btn_back);

        //back

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        docId = getIntent().getStringExtra("docId");

        fetchdata();

        Button home_btn = findViewById(R.id.home_btn);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhome();
            }
        });
    }

    private void fetchdata()
    {
        DocumentReference document = FirebaseFirestore.getInstance().collection("Upload Ride").document(docId);
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                txt_us_from.setText(documentSnapshot.getString("from"));
                txt_us_to.setText(documentSnapshot.getString("to"));
                txt_us_date.setText(documentSnapshot.getString("date"));
                txt_us_time.setText(documentSnapshot.getString("time"));
                txt_us_seats.setText(documentSnapshot.getString("seats"));
                txt_us_cost.setText("Rs. " + documentSnapshot.getString("cost"));
                txt_us_car.setText(documentSnapshot.getString("car"));
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Ride Upload Fail Try Again...",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openhome() {
        startActivity(new Intent(this, bottom_navigation.class));
    }
}