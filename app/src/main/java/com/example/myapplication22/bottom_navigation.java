package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.example.myapplication22.fragment.chat_history_Fragment;
import com.example.myapplication22.fragment.history_fragment;
import com.example.myapplication22.fragment.home_Fragment;
import com.example.myapplication22.fragment.profile_Fragment;
import com.example.myapplication22.fragment.wishlist_Fragment;
import com.example.myapplication22.search.search_ride;
import com.example.myapplication22.upload.upload_ride;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class bottom_navigation extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String mobile_number;
    private FirebaseFirestore firestore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        getSupportActionBar().hide();
        firestore = FirebaseFirestore.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new home_Fragment());
        transaction.commit();

        mobile_number = getIntent().getStringExtra("mobile_number");

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("access",userId);

//
        FirebaseFirestore.getInstance().collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Boolean access = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("access", document.getId() + " => " + document.getData());
                                if(document.getId().equals(userId)){
                                    Log.d("access", "allowed");
                                    access = true;
                                    break;
                                }
                                else
                                {
                                    Log.d("access", "denied");
                                }
                            }

                            if(!access)
                            {
                                Intent i = new Intent(bottom_navigation.this,Registration.class);
                                i.putExtra("mobile number",mobile_number);
                                startActivity(i);
                            }

                        } else {
                            Log.w("access", "Error getting documents.", task.getException());
                        }
                    }
                });
//

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(item.getItemId() == R.id.home){
                    transaction.replace(R.id.container,new home_Fragment());
                    transaction.commit();

                }
                else if(item.getItemId() == R.id.ride_history) {
                    transaction.replace(R.id.container, new history_fragment());
                    transaction.commit();
                }
                else if(item.getItemId() == R.id.chat_history) {
                    transaction.replace(R.id.container, new chat_history_Fragment());
                    transaction.commit();

                }
                else if(item.getItemId() == R.id.wishlist) {
                    transaction.replace(R.id.container, new wishlist_Fragment());
                    transaction.commit();

                }
                else {}
//                switch (item.getItemId())
//                {
//                    case R.id.home :
//                        transaction.replace(R.id.container,new home_Fragment());
//                        break;
//                    case R.id.ride_history :
//                        transaction.replace(R.id.container,new history_fragment());
//                        break;
//                    case R.id.chat_history :
//                        transaction.replace(R.id.container,new chat_history_Fragment());
//                        break;
//                    case R.id.wishlist :
//                        transaction.replace(R.id.container,new wishlist_Fragment());
//                        break;
//                    case R.id.profile :
//                        transaction.replace(R.id.container,new profile_Fragment());
//                        break;
//                }
//                transaction.commit();
                return true;
            }
        });
    }

    private void opensearch() {
        startActivity(new Intent(this, search_ride.class));
    }

    private void openupload() {
        startActivity(new Intent(this, upload_ride.class));
    }

    private void openuploadride() {
        startActivity(new Intent(this,upload_ride.class));
    }
}