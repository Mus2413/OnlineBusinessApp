package com.mj.pacholiaonlinebusinessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.R;
import com.mj.pacholiaonlinebusinessapp.cartviewholder;
import com.mj.pacholiaonlinebusinessapp.model.Cart;

public class AdminUserProductsActivity extends AppCompatActivity {
private RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
private DatabaseReference cartlistref;
String userid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        userid=getIntent().getStringExtra("uid");
        recyclerView=findViewById(R.id.recyclervieworderdetail);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cartlistref= FirebaseDatabase.getInstance().getReference().child("Cartlist");
    }

    @Override
    protected void onStart() {
        super.onStart();
          FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>()
                  .setQuery(cartlistref.child("Adminview")
                          .child(userid)
                          .child("ProductsItem"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, cartviewholder> adapter=new FirebaseRecyclerAdapter<Cart, cartviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cartviewholder holder, int position, @NonNull Cart model) {
                holder.txtproductquantity.setText("Product Quantity = " + model.getProductquantity());
                holder.txtproductprice.setText("Product Price = " + model.getProductprice() + "/- " + model.getPerpricequantity());
                holder.txtproductname.setText("Product Name = " + model.getProductname());
            }

            @NonNull
            @Override
            public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                cartviewholder cartviewholder= new cartviewholder(view);
                return cartviewholder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
