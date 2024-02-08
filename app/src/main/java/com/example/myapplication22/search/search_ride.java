
package com.example.myapplication22.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.R;
import com.example.myapplication22.adapters.adapter_item_ridelist;
import com.example.myapplication22.models.item_ridelist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class search_ride extends AppCompatActivity {

    Button addToWishlist;

    EditText search_by_from, search_by_to;
    TextView search_by_date;

    RecyclerView recyclerView;
    adapter_item_ridelist myadapter;
    ArrayList<item_ridelist> datalist;
    private String userId;
    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();

    DatePickerDialog.OnDateSetListener dateSetListener;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ride);

        getSupportActionBar().hide();
        ImageButton back = (ImageButton) findViewById(R.id.btn_back);
        auth=FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        //back

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //date
        search_by_date = findViewById(R.id.txt_date);

        Calendar calendar =Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        search_by_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(search_ride.this, android.R.style.Theme_Holo_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                search_by_date.setText(date);
            }
        };


        //database

        recyclerView = findViewById(R.id.ride_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        datalist = new ArrayList<>();
        myadapter = new adapter_item_ridelist(this, datalist);
        recyclerView.setAdapter(myadapter);

        //search

        search_by_from = (EditText)findViewById(R.id.search_by_from);
        search_by_to = (EditText)findViewById(R.id.search_by_to);
        search_by_date = (TextView) findViewById(R.id.txt_date);

        //from

        search_by_from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                datalist.clear();

                CollectionReference cr = dbroot.collection("Upload Ride");

                Query query = cr.whereEqualTo("from",s.toString().toUpperCase());

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                item_ridelist obj = document.toObject(item_ridelist.class);
                            datalist.add(obj);}
                            myadapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Log.d("tag","No Records found",task.getException());
                        }
                    }
                });
            }
        });

        //to

        search_by_to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                CollectionReference cr = dbroot.collection("Upload Ride");

                Query query = cr.whereEqualTo("to",s.toString().toUpperCase());

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                document.getId();
                                item_ridelist obj = document.toObject(item_ridelist.class);
                                datalist.add(obj);}
                            myadapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Log.d("tag","No Records found",task.getException());
                        }
                    }
                });
            }
        });

        //date

        search_by_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                CollectionReference cr = dbroot.collection("Upload Ride");

                Query query = cr.whereEqualTo("date",s.toString().toUpperCase());

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                document.getId();
                                item_ridelist obj = document.toObject(item_ridelist.class);
                                datalist.add(obj);}
                            myadapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Log.d("tag","No Records found",task.getException());
                        }
                    }
                });
            }
        });

        //add ride to wishlist

        addToWishlist = findViewById(R.id.btn_add_wishlist);
        addToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addridetowishlist();
            }
        });
    }

    private void addridetowishlist() {
        Map<String,String> ride =new HashMap<>();
        ride.put("from",search_by_from.getText().toString().toUpperCase());
        ride.put("to",search_by_to.getText().toString().toUpperCase());
        ride.put("date",search_by_date.getText().toString());
        ride.put("docId","0");
        dbroot.collection("Users").document(userId).collection("Wishlist").add(ride).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String docId = documentReference.getId();
                Log.d("wid",docId);
                dbroot.collection("Users").document(userId).collection("Wishlist").document(docId).update("docId",docId);
                addToWishlist.setText("Added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addToWishlist.setText("Fail to add");
            }
        });
    }

    private void openridedetails() {
        startActivity(new Intent(this, ride_details.class));
    }
}