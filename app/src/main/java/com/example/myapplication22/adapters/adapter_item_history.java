package com.example.myapplication22.adapters;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.R;
import com.example.myapplication22.fragment.history_fragment;
import com.example.myapplication22.models.item_history;
import com.example.myapplication22.search.booking_status;
import com.example.myapplication22.upload.upload_status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class adapter_item_history extends RecyclerView.Adapter<adapter_item_history.viewholder> {

    history_fragment context;

    ArrayList<item_history> datalist;

    public adapter_item_history(history_fragment context, ArrayList<item_history> list) {
        this.context = context;
        this.datalist = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        item_history item = datalist.get(position);
        holder.txt_from.setText(item.getFrom());
        holder.txt_to.setText(item.getTo());
        holder.txt_date.setText(item.getDate());
        holder.txt_time.setText(item.getTime());
        holder.bookedseat.setText(item.getBookedseat() + "/" + item.getSeats());
        holder.totalPayment.setText("Rs." + item.getTotalcost());

        if (item.getStatus().equals("n"))
        {
            holder.status.setText("Not Booked Yet");
            holder.status.setTextColor(Color.parseColor("#f4c430"));
        }
        else if (item.getStatus().equals("b")){
            holder.status.setText("✔ Booked");
            holder.status.setTextColor(Color.parseColor("#008000"));
        }
        else if (item.getStatus().equals("d"))
        {
            holder.status.setText("✔ Completed");
            holder.status.setTextColor(Color.parseColor("#008000"));
        }
        else if (item.getStatus().equals("c")){
            holder.status.setText("✖ Canceled");
            holder.status.setTextColor(Color.parseColor("#F44336"));
        }

        // touchlayout

        holder.touchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(item.getStatus().equals("n"))
                {
                    i = new Intent(holder.touchlayout.getContext(), upload_status.class);
                }
                else{
                    i = new Intent(holder.touchlayout.getContext(), booking_status.class);
                }
                i.putExtra("docId",item.getRide_id());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.touchlayout.getContext().startActivity(i);
            }
        });

        //cancel

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the object of
                // AlertDialog Builder class
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder( v.getContext());
                builder.setMessage("Do you want to Cancel the Ride ?");
                builder.setTitle("Alert !");
                builder.setCancelable(false);

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        // When the user click yes button
                                        FirebaseFirestore.getInstance().collection("Upload Ride").document(item.getRide_id())
                                                .update("status","c")
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        holder.status.setText("✖ Canceled");
                                                        holder.status.setTextColor(Color.parseColor("#F44336"));                                                        Log.d("cancel", "DocumentSnapshot successfully canceled!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("cancel", "Error canceling document", e);
                                                    }
                                                });
                                        notifyDataSetChanged();
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();


            }
        });

        //complete
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the object of
                // AlertDialog Builder class
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder( v.getContext());

                // Set the message show for the Alert time
                builder.setMessage("Have you completed ride ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        // When the user click yes button
                                        FirebaseFirestore.getInstance().collection("Upload Ride").document(item.getRide_id())
                                                .update("status","d")
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        holder.status.setText("✔ Completed");
                                                        holder.status.setTextColor(Color.parseColor("#008000"));
                                                        notifyDataSetChanged();
                                                        Log.d("done", "DocumentSnapshot successfully canceled!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("done", "Error canceling document", e);
                                                    }
                                                });
                                        notifyDataSetChanged();
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();


            }
        });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView txt_from,txt_to,txt_date,txt_time,bookedseat,totalPayment,status;
        LinearLayout touchlayout;
        Button cancel, complete;
        String userid = "lUDxJ3gbgjPtpBx5bqkR8mvux0B3";

        @SuppressLint("CutPasteId")
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txt_from = (TextView) itemView.findViewById(R.id.txtFrom);
            txt_to = itemView.findViewById(R.id.txtTo);
            txt_date = itemView.findViewById(R.id.txtDate);
            txt_time = itemView.findViewById(R.id.txtTime);
            bookedseat = itemView.findViewById(R.id.txtBookedSeats);
            totalPayment = itemView.findViewById(R.id.txtTotalpayment);
            status = itemView.findViewById(R.id.status);

            touchlayout= itemView.findViewById(R.id.Touchlayout);

            cancel = itemView.findViewById(R.id.btnCancel);
            complete = itemView.findViewById(R.id.btnComplete);
        }
    }

}
