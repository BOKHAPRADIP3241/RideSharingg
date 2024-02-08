package com.example.myapplication22.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication22.R;
import com.example.myapplication22.bottom_navigation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class booking_status extends AppCompatActivity {
    ImageButton back;
    TextView txtFrom,txtTo,txtDate,txtTime,txtSeat,txtCar,txtCost,txtPaymentDetails;
    Button home;
    String docID;
    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);

        getSupportActionBar().hide();

        docID = getIntent().getStringExtra("docId");
        Log.d("bsid",docID);

        back = findViewById(R.id.back);

        txtFrom = findViewById(R.id.txt_bs_from);
        txtTo = findViewById(R.id.txt_bs_to);
        txtDate = findViewById(R.id.txt_bs_date);
        txtTime =findViewById(R.id.txt_bs_time);
        txtSeat = findViewById(R.id.txt_bs_seat);
        txtCar = findViewById(R.id.txt_bs_car);
        txtCost = findViewById(R.id.txt_bs_cost);
        txtPaymentDetails = findViewById(R.id.txt_bs_paydet);

        home = findViewById(R.id.home_btn);
//back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//fetchdata
        dbroot.collection("Upload Ride").document(docID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                txtFrom.setText(documentSnapshot.getString("from"));
                txtTo.setText((documentSnapshot.getString("to")));
                txtDate.setText(documentSnapshot.getString("date"));
                txtTime.setText(documentSnapshot.getString("time"));
                txtCar.setText(documentSnapshot.getString("car"));
                txtCost.setText("Rs. " + documentSnapshot.getString("totalcost"));
                txtPaymentDetails.setText("UPI Id : " +documentSnapshot.getString("bookUpiId"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("fetch","error in fetching from database");
            }
        });
//home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhome();
            }
        });
    }

    private void openhome() {
        startActivity(new Intent(this, bottom_navigation.class));
    }
}