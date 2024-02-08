package com.example.myapplication22.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.R;
import com.example.myapplication22.models.item_ridelist;
import com.example.myapplication22.search.ride_details;

import java.util.ArrayList;

public class adapter_item_ridelist extends RecyclerView.Adapter<adapter_item_ridelist.viewholder> {

    Context context;

    ArrayList<item_ridelist> datalist;

    public adapter_item_ridelist(Context context, ArrayList<item_ridelist> list) {
        this.context = context;
        this.datalist = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ridelist,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        item_ridelist item = datalist.get(position);
        //UploadRide item = new UploadRide();
        holder.txt_s_from.setText(item.getFrom());
        holder.txt_s_to.setText(item.getTo());
        holder.txt_s_date.setText(item.getDate().toString());
        holder.txt_s_time.setText(item.getTime().toString());
        holder.txt_s_seats.setText(item.getSeats());
        holder.txt_s_cost.setText(item.getCost());
        holder.card_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.txt_s_from.getContext(), ride_details.class);
                intent.putExtra("docId",item.getRide_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.txt_s_from.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView txt_s_from,txt_s_to,txt_s_date,txt_s_time,txt_s_seats,txt_s_cost;
        LinearLayout card_ll;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            card_ll = itemView.findViewById(R.id.Touchlayout);
            txt_s_from = itemView.findViewById(R.id.txtFrom);
            txt_s_to = itemView.findViewById(R.id.txtTo);
            txt_s_date = itemView.findViewById(R.id.txtDate);
            txt_s_time = itemView.findViewById(R.id.txtTime);
            txt_s_seats = itemView.findViewById(R.id.txtBookedSeats);
            txt_s_cost = itemView.findViewById(R.id.txtTotalpayment);
        }
    }

}
