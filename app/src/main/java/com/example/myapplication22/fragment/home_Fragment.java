package com.example.myapplication22.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication22.R;
import com.example.myapplication22.help;
import com.example.myapplication22.search.search_ride;
import com.example.myapplication22.upload.upload_ride;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class home_Fragment extends Fragment {
    public home_Fragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static home_Fragment newInstance(String param1, String param2) {
        home_Fragment fragment = new home_Fragment();

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView from,to,date,time,car;
        String userId;

//        userId
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        from = view.findViewById(R.id.txt_us_from);
        to = view.findViewById(R.id.txt_us_to);
        date = view.findViewById(R.id.txt_us_date);
        time = view.findViewById(R.id.txt_us_time);
        car = view.findViewById(R.id.txt_us_car);

        LinearLayout upload_btn = view.findViewById(R.id.btn_upload);
        LinearLayout search_btn = view.findViewById(R.id.search_btn);
        LinearLayout help_support = view.findViewById(R.id.help_support);
        LinearLayout touchable = view.findViewById(R.id.touchable);

        upload_btn.setClickable(true);
        search_btn.setClickable(true);
        help_support.setClickable(true);
        touchable.setClickable(true);

//        help
        help_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), help.class));
            }
        });

//        upload
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), upload_ride.class));
            }
        });
//Upcoming
        FirebaseFirestore.getInstance().collection("Users").document(userId).collection("History").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot:queryDocumentSnapshots) {
                    Log.d("rideId", snapshot.getString("rideId"));
                    if(snapshot.exists()) {
                        DocumentReference document = FirebaseFirestore.getInstance().collection("Upload Ride").document(snapshot.getString("rideId"));
                        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                from.setText(documentSnapshot.getString("from"));
                                to.setText(documentSnapshot.getString("to"));
                                date.setText(documentSnapshot.getString("date"));
                                time.setText(documentSnapshot.getString("time"));
                                car.setText(documentSnapshot.getString("car"));

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        from.setText("No Rides");
                                    }
                                });
                    }
                    break;
                }
            }
        });

//search
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), search_ride.class));
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_, container, false);
    }
}