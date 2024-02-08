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
import com.example.myapplication22.adapters.adapter_item_wishlist;
import com.example.myapplication22.models.item_wishlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class wishlist_Fragment extends Fragment {
    public wishlist_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static wishlist_Fragment newInstance(String param1, String param2) {
        wishlist_Fragment fragment = new wishlist_Fragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.history);
        TextView status = view.findViewById(R.id.txtstatus);
        adapter_item_wishlist myadapter = null;
        ArrayList<item_wishlist> datalist = new ArrayList<item_wishlist>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String UserId = auth.getCurrentUser().getUid();
        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        datalist = new ArrayList<>();
        myadapter = new adapter_item_wishlist(this, datalist);
        recyclerView.setAdapter(myadapter);

        datalist.clear();

        Query query =  dbroot.collection("Users").document(UserId).collection("Wishlist");

        ArrayList<item_wishlist> finalDatalist = datalist;
        adapter_item_wishlist finalMyadapter = myadapter;
        adapter_item_wishlist finalMyadapter1 = myadapter;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        finalDatalist.add(document.toObject(item_wishlist.class));}
                    finalMyadapter.notifyDataSetChanged();
                    if(finalMyadapter1.getItemCount()==0){
                        status.setText("No Data Found");
                        Log.d("empty","List is Empty");
                       // Toast toast = Toast.makeText(getContext(),"No Rides in wishlist",Toast.LENGTH_LONG);
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

        myadapter.notifyDataSetChanged();

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
        return inflater.inflate(R.layout.fragment_wishlist_, container, false);


    }
}