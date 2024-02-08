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
import com.example.myapplication22.adapters.adapter_item_history;
import com.example.myapplication22.models.item_history;
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

public class history_fragment extends Fragment {
    public history_fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static history_fragment newInstance(String param1, String param2) {
        history_fragment fragment = new history_fragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        RecyclerView recyclerView = view.findViewById(R.id.history);
        TextView status = view.findViewById(R.id.txtstatus);
        adapter_item_history myadapter = null;
        ArrayList<item_history> datalist = new ArrayList<item_history>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String UserId = auth.getCurrentUser().getUid();
        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        datalist = new ArrayList<>();
        myadapter = new adapter_item_history(this, datalist);
        recyclerView.setAdapter(myadapter);

        datalist.clear();
        ArrayList<item_history> finalDatalist = datalist;
        adapter_item_history finalMyadapter = myadapter;
        adapter_item_history finalMyadapter1 = myadapter;

        datalist.clear();

        dbroot.collection("Users").document(userId).collection("History").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                    Log.d("rideId",snapshot.getString("rideId"));
                    String rideId = snapshot.getString("rideId");
                    Query query =  dbroot.collection("Upload Ride").whereEqualTo("ride_id",rideId);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        finalDatalist.add(document.toObject(item_history.class));
                    }
                    finalMyadapter.notifyDataSetChanged();
                    if(finalMyadapter.getItemCount()==0){
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
        return inflater.inflate(R.layout.fragment_ride_history_, container, false);
    }
}