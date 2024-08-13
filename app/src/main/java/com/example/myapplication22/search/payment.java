  package com.example.myapplication22.search;

  import android.content.Intent;
  import android.os.Bundle;
  import android.util.Log;
  import android.view.View;
  import android.widget.Button;
  import android.widget.EditText;
  import android.widget.ImageButton;
  import android.widget.TextView;

  import androidx.annotation.NonNull;
  import androidx.appcompat.app.AppCompatActivity;


  import com.example.myapplication22.R;
  import com.google.android.gms.tasks.OnFailureListener;
  import com.google.android.gms.tasks.OnSuccessListener;
  import com.google.firebase.auth.FirebaseAuth;
  import com.google.firebase.firestore.DocumentSnapshot;
  import com.google.firebase.firestore.FirebaseFirestore;

  import java.util.HashMap;
  import java.util.Map;

public class payment extends AppCompatActivity {

    ImageButton back;
    TextView txt_cost,txt_seats,txt_total;
    EditText upiId;
    Button pay;
    String docId,userId;
    Integer cost,seat,total;
    FirebaseFirestore dbroot;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().hide();

        userId = auth.getCurrentUser().getUid();

        back = findViewById(R.id.back);
        txt_cost = findViewById(R.id.txt_bs_cost);
        txt_seats = findViewById(R.id.bookedSeats);
        txt_total = findViewById(R.id.totalCost);
        upiId = findViewById(R.id.edUpiId);
        pay = findViewById(R.id.btn_pay);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //docId

        docId = getIntent().getStringExtra("docId");
        Log.d("id",docId);

        //fetchdat
        dbroot =FirebaseFirestore.getInstance();
        fetchdata(docId);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaddata(docId);
                openbookingstatus(docId);
            }
        });
    }

    private void uploaddata(String docId) {
        Map<String,Object> data =new HashMap<>();
        data.put("totalcost",total.toString());
        data.put("bookUpiId",upiId.getText().toString());
        data.put("status","b");
        dbroot.collection("Upload Ride").document(docId).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                HashMap rideId = new HashMap();
                rideId.put("rideId",docId);
                dbroot.collection("Users").document(
                        userId).collection("History").add(rideId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void fetchdata(String docId) {  
        Log.d("fetch","started");
        dbroot.collection("Upload Ride").document(docId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cost = Integer.parseInt(documentSnapshot.getString("cost"));
                seat = Integer.parseInt(documentSnapshot.getString("bookedseat"));
                total = cost*seat;

                txt_cost.setText("Rs. " +cost);
                txt_seats.setText("X " + seat);
                txt_total.setText("Rs. " + total);
            }
        }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void openbookingstatus(String docId){
        //Log.d("id",docId);
        Intent intent = new Intent(this, booking_status.class);
        intent.putExtra("docId",docId);
        startActivity(intent);
    }
}