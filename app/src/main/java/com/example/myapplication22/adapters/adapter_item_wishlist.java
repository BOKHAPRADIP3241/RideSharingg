package com.example.myapplication22.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication22.R;
import com.example.myapplication22.fragment.wishlist_Fragment;
import com.example.myapplication22.models.item_wishlist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class adapter_item_wishlist extends RecyclerView.Adapter<adapter_item_wishlist.viewholder> {

    wishlist_Fragment context;

    ArrayList<item_wishlist> datalist;

    public adapter_item_wishlist(wishlist_Fragment context, ArrayList<item_wishlist> list) {
        this.context = context;
        this.datalist = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
            item_wishlist item = datalist.get(position);
            //UploadRide item = new UploadRide();
            holder.txt_from.setText(item.getFrom());
            holder.txt_to.setText(item.getTo());
            holder.txt_date.setText(item.getDate());
            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore.getInstance().collection("Users").document(holder.userid).collection("Wishlist").document(item.getDocId())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    holder.itemView.setVisibility(View.GONE);

                                    Log.d("delete", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("delete", "Error deleting document", e);
                                }
                            });
                    datalist.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView txt_from,txt_to,txt_date,status;
        ImageButton btn_delete;
        String userid;

        FirebaseAuth auth = FirebaseAuth.getInstance();

        public viewholder(@NonNull View itemView) {
            super(itemView);
            userid = auth.getCurrentUser().getUid();
            txt_from = itemView.findViewById(R.id.txt_from);
            txt_to = itemView.findViewById(R.id.txt_to);
            txt_date = itemView.findViewById(R.id.txt_date);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

}
