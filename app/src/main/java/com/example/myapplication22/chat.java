package com.example.myapplication22;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.adapters.adapter_message;
import com.example.myapplication22.models.item_message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class chat extends AppCompatActivity {

    ImageButton back;
    EditText getMSG;
    TextView receiverName;
    String msg,userId,oppId,oppName;
    ImageButton send;

    RecyclerView rv;
    adapter_message myadapter;
    ArrayList<item_message> datalist;

    FirebaseFirestore firestore;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().hide();

        back = findViewById(R.id.back);

        rv = findViewById(R.id.recyclerview);

        getMSG = findViewById(R.id.getmessage);
        send = findViewById(R.id.send);
        receiverName = findViewById(R.id.Nameofspecificuser);
        msg = getMSG.getText().toString();
        userId = auth.getCurrentUser().getUid();
        oppId = getIntent().getStringExtra("oppId");
        oppName = getIntent().getStringExtra("oppName");

        firestore = FirebaseFirestore.getInstance();

        datalist = new ArrayList<>();
        myadapter = new adapter_message(datalist, this);
        rv.setAdapter(myadapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setStackFromEnd(true);
        llm.setSmoothScrollbarEnabled(true);
        llm.setReverseLayout(false);

        rv.setLayoutManager(llm);

        firestore.collection("Users").document(oppId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                receiverName.setText(documentSnapshot.getString("name"));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//send_btn
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getMSG.getText().toString().isEmpty())
                {

                }
                else{
                HashMap map = new HashMap();
                map.put("msg",getMSG.getText().toString());
                map.put("time",Calendar.getInstance().getTime());
                map.put("sendBy",userId);
                firestore
                        .collection("Chats")
                        .document(userId)
                        .collection(userId+"_"+oppId)
                        .document(getcurrenttime())
                        .set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                firestore.collection("Chats")
                                        .document(oppId)
                                        .collection(oppId+"_"+userId)
                                        .document(getcurrenttime())
                                        .set(map);
                            }
                        });

                getMSG.setText("");
            }
            }
        });




//messages
            firestore.collection("Chats").document(userId).collection(userId+"_"+oppId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                datalist.clear();
                for(DocumentSnapshot snapshot:value){
                    Log.d("msg",snapshot.getString("msg"));
                    datalist.add(snapshot.toObject(item_message.class));
                }
                myadapter.notifyDataSetChanged();
                rv.smoothScrollToPosition(rv.getAdapter().getItemCount()-1);
            }
        });
    }

    private String getcurrenttime() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return f.format(new Date());
    }

    private void sendMSG(String msg){

    }
}