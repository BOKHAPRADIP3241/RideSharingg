package com.example.myapplication22.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.myapplication22.R;
import com.example.myapplication22.imageView;
import com.example.myapplication22.login;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_Fragment extends Fragment {
    EditText fullName, mobileNumber, email, address;
    Button logOut,editProfile,adharcard,licence;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String uId;
    ImageView profile;
    boolean isEditMode;
    Map<String,Object> userMap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile_Fragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static profile_Fragment newInstance(String param1, String param2) {
        profile_Fragment fragment = new profile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        auth = FirebaseAuth.getInstance();
        isEditMode = false;
        firestore = FirebaseFirestore.getInstance();
        fullName = view.findViewById(R.id.full_name);
        mobileNumber =view.findViewById(R.id.mobile_number);
        email = view.findViewById(R.id.email);
        profile = view.findViewById(R.id.upProfile);
        address = view.findViewById(R.id.address);
        logOut = view.findViewById(R.id.logout);
        editProfile = view.findViewById(R.id.edit_profile);
        adharcard = view.findViewById(R.id.aadhar_btn);
        licence = view.findViewById(R.id.licence_btn);
        userMap = new HashMap<>();
        uId = auth.getCurrentUser().getUid();

        fullName.setEnabled(false);
        email.setEnabled(false);
        address.setEnabled(false);

        firestore.collection("Users").document(uId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.getString("name").isEmpty()){
                    fullName.setText(documentSnapshot.getString("name"));

                }
                if(!documentSnapshot.getString("mobileNumber").isEmpty()){
                    mobileNumber.setText(documentSnapshot.getString("mobileNumber"));
                }
                if(!documentSnapshot.getString("email").isEmpty()){
                    email.setText(documentSnapshot.getString("email"));
                }
                if(!documentSnapshot.getString("address").isEmpty()){
                    address.setText(documentSnapshot.getString("address"));
                }
                if(!documentSnapshot.getString("profile").isEmpty()){
                    Picasso
                            .get()
                            .load(documentSnapshot.getString("profile"))
                            .into(profile);
                }
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        adharcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Intent i = new Intent(getActivity(), imageView.class);
                        i.putExtra("url",documentSnapshot.getString("adharcard"));
                        i.putExtra("title","Adharcard");
                        startActivity(i);
                    }
                });
            }
        });

        licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Intent i = new Intent(getActivity(), imageView.class);
                        i.putExtra("url",documentSnapshot.getString("licence"));
                        i.putExtra("title","Licence");
                        startActivity(i);
                    }
                });
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Intent i = new Intent(getActivity(), imageView.class);
                        i.putExtra("url",documentSnapshot.getString("profile"));
                        i.putExtra("title","Profile Pic");
                        startActivity(i);
                    }
                });
            }
        });
    }

    void signOut(){
        auth.signOut();
        startActivity(new Intent(getActivity(), login.class));
    }

    @SuppressLint("SetTextI18n")
    void editMode(){
        if (!isEditMode){
            fullName.setEnabled(true);
            email.setEnabled(true);
            address.setEnabled(true);
            isEditMode=true;
            editProfile.setText("Submit");
        }
        else {
            userMap.put("name", fullName.getText().toString().trim());
            userMap.put("email", email.getText().toString().trim());
            userMap.put("address", address.getText().toString());
            firestore.collection("Users").document(uId).update(userMap);
            profile.setEnabled(false);
            fullName.setEnabled(false);
            email.setEnabled(false);
            address.setEnabled(false);
            isEditMode=false;
            editProfile.setText("Edit Profile");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}