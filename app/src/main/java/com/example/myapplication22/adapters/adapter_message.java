package com.example.myapplication22.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.R;
import com.example.myapplication22.models.item_message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class adapter_message extends RecyclerView.Adapter{

    ArrayList<item_message> datalist;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;

    public adapter_message(ArrayList<item_message> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sendermessage,parent,false);
            return  new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.receivermessage,parent,false);
            return  new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        item_message item = datalist.get(position);
        if(item.getSendBy().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECIEVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        item_message item = datalist.get(position);

        if(holder.getClass() == SenderViewHolder.class){

            FirebaseFirestore.getInstance().collection("Users").document(item.getSendBy()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    ((SenderViewHolder)holder).send_name.setText(documentSnapshot.getString("name"));
                }
            });

            ((SenderViewHolder)holder).send_msg.setText(item.getMsg());

        }
        else{
            FirebaseFirestore.getInstance().collection("Users").document(item.getSendBy()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    ((RecieverViewHolder)holder).rec_name.setText(documentSnapshot.getString("name"));
                }
            });
            ((RecieverViewHolder)holder).rec_msg.setText(item.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    //    reciever
    public class  RecieverViewHolder extends  RecyclerView.ViewHolder {

        TextView rec_name,rec_msg;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_name = itemView.findViewById(R.id.rec_name);
            rec_msg = itemView.findViewById(R.id.rec_message);
        }
    }
//    sender
public class  SenderViewHolder extends  RecyclerView.ViewHolder {

    TextView send_msg,send_name;

    public SenderViewHolder(@NonNull View itemView) {
        super(itemView);
        send_name = itemView.findViewById(R.id.send_name);
        send_msg = itemView.findViewById(R.id.send_message);
    }
}


}
