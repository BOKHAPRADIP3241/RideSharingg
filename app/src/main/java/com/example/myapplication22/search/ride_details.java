package com.example.myapplication22.search;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication22.R;
import com.example.myapplication22.bottom_navigation;
import com.example.myapplication22.chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ride_details extends AppCompatActivity {
    ImageButton back,googlemap;
    TextView upName;
    ImageView upProfile;
    Button btnBook,btnChat;
    TextView from,to,date,time,cost,seats,car;
    EditText pickup,drop,bookseats;
    String docId, upUserID,txtfrom,txtto;
    Boolean checkdata = false;

    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
    FirebaseAuth auth =FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        getSupportActionBar().hide();

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upProfile = (ImageView)findViewById(R.id.upProfile);
        upName = (TextView)findViewById(R.id.upName);

        googlemap = findViewById(R.id.googlemap);

        from = (TextView)findViewById(R.id.txt_us_from);
        to = (TextView)findViewById(R.id.txt_us_to);
        date = (TextView)findViewById(R.id.txt_us_date);
        time = (TextView)findViewById(R.id.txt_us_time);
        cost = (TextView)findViewById(R.id.txt_us_costs);
        seats = (TextView)findViewById(R.id.txt_us_seats);
        car = (TextView)findViewById(R.id.txt_us_car);

        pickup = (EditText) findViewById(R.id.edPickup);
        drop = (EditText) findViewById(R.id.edDrop);
        bookseats = (EditText) findViewById(R.id.edbookSeat);

        btnBook = findViewById(R.id.btn_book);
        btnChat =findViewById(R.id.btn_chat);

//get docid and uploaderuserid
        docId = getIntent().getStringExtra("docId");
        Log.d("Ridedetailid",docId);
        //Firebase firestore

        fetchdata(docId);


        Log.d("userId","userId: "+userId);
        Log.d("userId","upUserId: "+upUserID);

        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSource = from.getText().toString().trim();
                String sDestination = to.getText().toString().trim();

                if (from.equals("") && to.equals("")) {
                    Toast.makeText(getApplicationContext()
                            , "Enter both location", Toast.LENGTH_SHORT).show();
                } else {
                    DisplayTrack(sSource, sDestination);
                }
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkdata = checkalldata();
                if(checkdata) {
                    Log.d("upload","requested");
                    uploaddata();
                }
            }
        });



//        btnChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // openhome();
//            }
//        });
    }

    //fetchdata fun

    private void fetchdata(String docId) {
            DocumentReference document = dbroot.collection("Upload Ride").document(docId);
            document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    from.setText(documentSnapshot.getString("from"));
                    txtfrom = documentSnapshot.getString("from");
                    to.setText(documentSnapshot.getString("to"));
                    txtto = documentSnapshot.getString("to");
                    date.setText(documentSnapshot.getString("date"));
                    time.setText(documentSnapshot.getString("time"));
                    seats.setText("Available Seats : " + documentSnapshot.getString("seats"));
                    cost.setText("Rs. " + documentSnapshot.getString("cost"));
                    car.setText("Car Name : " + documentSnapshot.getString("car"));

                    dbroot.collection("Users").document(documentSnapshot.getString("upUserId")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot1) {
                            upName.setText(documentSnapshot1.getString("name"));
                            Picasso
                                    .get()
                                    .load(documentSnapshot1.getString("profile"))
                                    .into(upProfile);
                            upUserID = documentSnapshot1.getString("id");


//                            Chat button
                            btnChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    chatBtn(userId,upUserID);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("name","Error to load data");
                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Ride Upload Fail Try Again...",Toast.LENGTH_LONG).show();
                        }
                    });
    }

//    upload fun

    private void chatBtn(String userId,String upUserID){
        HashMap msg = new HashMap();
        msg.put("msg","Hii");
        msg.put("time", Calendar.getInstance().getTime());
        msg.put("sendBy",userId);
        dbroot.collection("Chats").document(userId).collection(userId+"_"+upUserID).document(getcurrenttime()).set(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dbroot.collection("Chats").document(upUserID).collection(upUserID+"_"+userId).document(getcurrenttime()).set(msg).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        HashMap map = new HashMap();
                        map.put("id",upUserID);
                        dbroot.collection("Users").document(userId).collection("ChatWith").document(upUserID).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                HashMap map = new HashMap();
                                map.put("id",userId);
                                dbroot.collection("Users").document(upUserID).collection("ChatWith").document(userId).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent i = new Intent(ride_details.this, chat.class);
                                        i.putExtra("oppId",upUserID);
                                        startActivity(i);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
//googlemap
    private String getcurrenttime() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return f.format(new Date());
    }

    private void DisplayTrack(String sSource, String sDestination) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/" + sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void uploaddata() {
        Map<String,Object> data =new HashMap<>();
        data.put("pickup",pickup.getText().toString().trim().toUpperCase());
        data.put("drop",drop.getText().toString().trim().toUpperCase());
        data.put("bookedseat",bookseats.getText().toString().trim());

        dbroot.collection("Upload Ride").document(docId)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        openpayment();
                        Log.d("update", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("update", "Error writing document", e);
                    }
                });

    }
    //upload pickup,drop,bookedseat

    private Boolean checkalldata() {
        if (pickup.length() == 0) {
            pickup.setError("This field is required");
            return false;
        } else if (drop.length() == 0) {
            drop.setError("This field is required");
            return false;
        } else if (bookseats.length() == 0) {
            bookseats.setError("This field is required");
            return false;
        }
        else return true;
    }

    public void  openpayment(){
        Intent intent = new Intent(this, payment.class);
        intent.putExtra("docId",docId);
        startActivity(intent);
    }
    public void openhome(){
        Intent intent = new Intent(this, bottom_navigation.class);
        startActivity(intent);
    }
}