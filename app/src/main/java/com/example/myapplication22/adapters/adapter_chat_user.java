package com.example.myapplication22.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.R;
import com.example.myapplication22.chat;
import com.example.myapplication22.fragment.chat_history_Fragment;
import com.example.myapplication22.models.item_chat_user;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_chat_user extends RecyclerView.Adapter<adapter_chat_user.viewholder> {
    chat_history_Fragment context;

    ArrayList<item_chat_user> datalist;


    public adapter_chat_user(chat_history_Fragment context, ArrayList<item_chat_user> list) {
        this.context = context;
        this.datalist = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_chat,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        item_chat_user item = datalist.get(position);
    holder.name.setText(item.getName());
    if(item.getOnline())
    {
        holder.online.setText("Online");
        holder.online.setTextColor(Color.parseColor("#008000"));
    }
    else{
        holder.online.setText("Offline");
    }
    //profile
    Picasso.get().load(item.getProfile()).into(holder.profile);

    //onclick
        holder.touchable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.touchable.getContext(), chat.class);
                i.putExtra("oppId",item.getId());
                i.putExtra("oppName",item.getName());
                holder.touchable.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView name,online;
        ImageView profile;
        LinearLayout touchable;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Name);
            online = itemView.findViewById(R.id.online);
            profile = itemView.findViewById(R.id.imageprofile);
            touchable = itemView.findViewById(R.id.touchablell);
        }
    }

}
