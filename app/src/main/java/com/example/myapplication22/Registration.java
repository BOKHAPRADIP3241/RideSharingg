package com.example.myapplication22;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText first_name, last_name, mobile_number, email, address;
    Button profile,adharcard,licence,submit;
    Uri profileUri,adharUri,licenceUri;
    RadioButton male,female,other;
    TextView profilePath,adharPath,licencePath;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    StorageReference storageRef;
    String p,a,l, number,uId;
    Map<String,String> userMap;
    Map<String,String> reg = new HashMap<>();
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
//        getSupportActionBar().setTitle("Registration");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other =findViewById(R.id.other);
        male = findViewById(R.id.other);
        first_name = findViewById(R.id.full_name);
        last_name = findViewById(R.id.last_name);
        mobile_number = findViewById(R.id.mobile_number);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        profilePath = findViewById(R.id.path_photo);
        adharPath = findViewById(R.id.path_aadhar);
        licencePath = findViewById(R.id.path_licence);
        profile = findViewById(R.id.photo_btn);
        adharcard = findViewById(R.id.aadhar_btn);
        licence = findViewById(R.id.licence_btn);
        submit = findViewById( R.id.submit_btn);
        number = getIntent().getStringExtra("mobile number");


        uId = auth.getCurrentUser().getUid();
        Log.d("register",uId);

        mobile_number.setText("+91 " + number);
        userMap = new HashMap();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(first_name.getText().toString().isEmpty()){
                    first_name.setError("Enter First-Name");
                }
                else if (last_name.getText().toString().isEmpty()){
                    last_name.setError("Enter Last-Name");
                }
                else if(email.getText().toString().isEmpty()){
                    email.setError("Enter Email");
                }
                else if(address.getText().toString().isEmpty()){
                    address.setError("Enter Address");
                }
                else {
                    userMap.put("name", first_name.getText().toString().trim() + " " + last_name.getText().toString().trim());
                    userMap.put("mobileNumber", mobile_number.getText().toString().trim());
                    userMap.put("email", email.getText().toString().trim());
                    userMap.put("address", address.getText().toString());
                    userMap.put("id",uId);
                    reg.put("mobileNumber", mobile_number.getText().toString().trim());
                    reg.put("id",uId);
                    if (male.isChecked()){
                        userMap.put("gender","Male");
                    }
                    else if (female.isChecked()){
                        userMap.put("gender","Female");
                    }
                    else if(other.isChecked()){
                        userMap.put("gender","Other");
                    }
                    storageRef.child("Images/" + uId + "/profile/")
                            .putFile(profileUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    taskSnapshot
                                            .getStorage()
                                            .getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    p=uri.toString();
                                                    if (p.isEmpty()){
                                                        firestore.collection("Users").document(uId).update("profile","");
                                                    }
                                                    else{
                                                        firestore.collection("Users").document(uId).update("profile",p);
                                                    }
                                                }
                                            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            storageRef.child("Images/" + uId + "/adharcard/")
                                                    .putFile(adharUri)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            taskSnapshot
                                                                    .getStorage()
                                                                    .getDownloadUrl()
                                                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri) {
                                                                            a=uri.toString();
                                                                            if (a.isEmpty()){
                                                                                firestore.collection("Users").document(uId).update("adharcard","");
                                                                            }
                                                                            else{
                                                                                firestore.collection("Users").document(uId).update("adharcard",a);
                                                                            }
                                                                        }
                                                                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    storageRef.child("Images/" + uId + "/licence/")
                                                                            .putFile(adharUri)
                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                    taskSnapshot
                                                                                            .getStorage()
                                                                                            .getDownloadUrl()
                                                                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                @Override
                                                                                                public void onSuccess(Uri uri) {
                                                                                                    l=uri.toString();
                                                                                                    if (l.isEmpty()){
                                                                                                        firestore.collection("Users").document(uId).update("licence","");
                                                                                                    }
                                                                                                    else{
                                                                                                        firestore.collection("Users").document(uId).update("licence",l);
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                }
                                                                            });
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });


                    firestore.collection("Registered Users").document().set(reg).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            firestore.collection("Users").document(uId).set(userMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Intent i= new Intent(Registration.this, bottom_navigation.class);
                                            i.putExtra("uid",uId);
                                            startActivity(i);
                                        }
                                    });
                            Log.d("registered","User Registered");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("registered","Not Registered");
                        }
                    });

                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(1);
            }
        });

        adharcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(2);
            }
        });

        licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
chooseImage(3);
            }
        });
    }
    private  void chooseImage(int code){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null && data.getData()!=null){
            if(requestCode == 1){
                profileUri = data.getData();
                String profile_path = profileUri.toString();
                profilePath.setText(profileUri.toString().substring(profile_path.lastIndexOf("/")+1));
            }
            else if (requestCode==2){
                adharUri = data.getData();
                String adhar_path = adharUri.toString();
                adharPath.setText(adharUri.toString().substring(adhar_path.lastIndexOf("/")+1));
            }else if (requestCode ==3){
                licenceUri = data.getData();
                String licence_path = licenceUri.toString();
                licencePath.setText(licenceUri.toString().substring(licence_path.lastIndexOf("/")+1));
            }
            else{
                Log.d("upload image","something went wrong");
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        other.setChecked(true);

        firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                    Log.d("reg",snapshot.toString());
                  }
             }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("reg","Failed" + e.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.signOut();
    }
}