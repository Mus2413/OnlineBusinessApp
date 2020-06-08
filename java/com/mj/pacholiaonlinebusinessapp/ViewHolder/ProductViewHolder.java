package com.mj.pacholiaonlinebusinessapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mj.pacholiaonlinebusinessapp.Interface.ItemClickListener;
import com.mj.pacholiaonlinebusinessapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder{
    public TextView txtProductName, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);

        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
    }

}
