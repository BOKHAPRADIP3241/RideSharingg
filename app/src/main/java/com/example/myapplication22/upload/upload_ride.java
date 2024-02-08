package com.example.myapplication22.upload;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication22.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class
upload_ride extends AppCompatActivity {

    Button upload_btn;
    EditText txt_from,txt_to,txt_seats,txt_cost,txt_car;
    TextView txt_date,txt_time;
    ImageButton back;
    DatePickerDialog.OnDateSetListener dateSetListener;
    String doc_id;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Boolean checkdata = false;

    FirebaseFirestore dbroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ride);

        getSupportActionBar().hide();

        //findviewbyid

        upload_btn = (Button)findViewById(R.id.btn_upload);

        txt_from = (EditText)findViewById(R.id.txt_from);
        txt_to = (EditText)findViewById(R.id.txt_to);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_time = (TextView) findViewById(R.id.txt_time);
        txt_seats = (EditText)findViewById(R.id.txt_seat);
        txt_cost = (EditText)findViewById(R.id.txt_cost);
        txt_car = (EditText)findViewById(R.id.txt_car);

        back = (ImageButton) findViewById(R.id.btn_back);

        //back

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //date

        Calendar calendar =Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(upload_ride.this, android.R.style.Theme_Holo_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                txt_date.setText(date);
            }
        };

        //time

        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(upload_ride.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String time = hourOfDay + ":" + minute;

                        SimpleDateFormat f24h = new SimpleDateFormat("hh:mm");

                        try {
                            Date date = f24h.parse(time);
                            SimpleDateFormat f12h = new SimpleDateFormat("hh:mm aa");
                            txt_time.setText(f12h.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        //Firebase firestore

        dbroot = FirebaseFirestore.getInstance();

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkdata = checkalldata();
                if(checkdata) {
                    uploaddata();
                }
            }
        });
    }

    //validation

    private Boolean checkalldata() {
        if (txt_from.length() == 0) {
            txt_from.setError("This field is required");
            return false;
        }

        else if (txt_to.length() == 0) {
            txt_to.setError("This field is required");
            return false;
        }

        else if (txt_date.length() == 0) {
            txt_date.setError("This field is required");
            return false;
        }

        else if (txt_time.length() == 0) {
            txt_time.setError("This field is required");
            return false;
        }

        else if (txt_seats.length() == 0) {
            txt_seats.setError("This field is required");
            return false;
        }

        else if (txt_cost.length() == 0) {
            txt_cost.setError("This field is required");
            return false;
        }

        else if (txt_car.length() == 0) {
            txt_car.setError("This field is required");
            return false;
        }
        // after all validation return true.
        else return true;
    }

    //Upload data Function

    private void uploaddata() {
        Map<String,String> ride =new HashMap<>();
        Map<String,Object> id =new HashMap<>();
        ride.put("from",txt_from.getText().toString().trim().toUpperCase());
        ride.put("to",txt_to.getText().toString().trim().toUpperCase());
        ride.put("date",txt_date.getText().toString().trim());
        ride.put("time",txt_time.getText().toString().trim());
        ride.put("seats",txt_seats.getText().toString().trim());
        ride.put("cost",txt_cost.getText().toString().trim());
        ride.put("car",txt_car.getText().toString().trim().toUpperCase());
        ride.put("ride_id",doc_id);
        ride.put("upUserId",userId);
        ride.put("status","n");
        ride.put("bookedseat","0");
        ride.put("totalcost","0");

        dbroot.collection("Upload Ride").add(ride)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    doc_id = documentReference.getId();
                        id.put("ride_id",doc_id);
                        dbroot.collection("Upload Ride").document(doc_id).update(id).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                HashMap rideId = new HashMap();
                                rideId.put("rideId",doc_id);
                                dbroot.collection("Users").document(
                                        userId).collection("History").add(rideId);
                                openuploadstatus();
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


    private void openuploadstatus() {
        Intent i = new Intent(this, upload_status.class);
        i.putExtra("docId",doc_id);
        startActivity(i);
    }
}