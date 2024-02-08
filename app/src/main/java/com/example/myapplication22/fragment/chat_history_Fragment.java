package com.example.myapplication22.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.R;
import com.example.myapplication22.adapters.adapter_chat_user;
import com.example.myapplication22.models.item_chat_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class  chat_history_Fragment extends Fragment {
    public chat_history_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static chat_history_Fragment newInstance(String param1, String param2) {
        chat_history_Fragment fragment = new chat_history_Fragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        TextView status = view.findViewById(R.id.data);
        adapter_chat_user myadapter = null;
        ArrayList<item_chat_user> datalist = new ArrayList<item_chat_user>();
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String UserId = auth.getCurrentUser().getUid();
        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        datalist = new ArrayList<>();
        myadapter = new adapter_chat_user(this, datalist);
        recyclerView.setAdapter(myadapter);

        datalist.clear();

        //users list
        Query query =  dbroot.collection("Users").document(UserId).collection("Wishlist");

        ArrayList<item_chat_user> finalDatalist = datalist;
        adapter_chat_user finalMyadapter = myadapter;
        adapter_chat_user finalMyadapter1 = myadapter;


        datalist.clear();

        dbroot.collection("Users").document(UserId).collection("ChatWith").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                    Log.d("oppuid",snapshot.getString("id"));
                    String oppId = snapshot.getString("id");
                    Query query =  dbroot.collection("Users").whereEqualTo("id",oppId);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    finalDatalist.add(document.toObject(item_chat_user.class));}
                                finalMyadapter.notifyDataSetChanged();
                                if(finalMyadapter1.getItemCount()==0){
                                    status.setText("No Data Found");
                                    Log.d("empty","List is Empty");
                                    // Toast toast = Toast.makeText(getContext(),"No Rides in History",Toast.LENGTH_LONG);
                                    Toast.makeText(getActivity(), "no record found",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    status.setText("");
                                }
                            }
                            else
                            {

                                Log.d("datalist","Empty");
                            }
                        }
                    });
                }
            }});




        myadapter.notifyDataSetChanged();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userId = auth.getCurrentUser().getUid();
        firestore.collection("Users").document(userId).update("online",true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userId = auth.getCurrentUser().getUid();
        firestore.collection("Users").document(userId).update("online",false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_history_, container, false);
    }
}