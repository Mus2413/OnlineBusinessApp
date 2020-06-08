package com.mj.pacholiaonlinebusinessapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mj.pacholiaonlinebusinessapp.Interface.ItemClickListener;

public class cartviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtproductname,txtproductprice,txtproductquantity;
    private ItemClickListener listener;

    public cartviewholder(@NonNull View itemView) {
        super(itemView);
        txtproductname=itemView.findViewById(R.id.cartproductname);
        txtproductprice=itemView.findViewById(R.id.cartproductprice);
        txtproductquantity=itemView.findViewById(R.id.cartproductquantity);

    }

    @Override
    public void onClick(View v) {
listener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
