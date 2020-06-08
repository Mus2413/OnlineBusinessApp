package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminMaintainProductsActivity;
import com.mj.pacholiaonlinebusinessapp.ViewHolder.ProductViewHolder;
import com.mj.pacholiaonlinebusinessapp.model.Products;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class UserProductDisplayActivity extends AppCompatActivity {
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private  String categoryname="";
    private  String type="";
    private TextView txtcategoryname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_display);
        categoryname=getIntent().getExtras().get("category").toString();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            type = getIntent().getExtras().get("person").toString();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabusercart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!type.equals("Admin")){
                    startActivity(new Intent(UserProductDisplayActivity.this, CartActivity.class));
                }

            }
        });

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname);
        ProductsRef.keepSynced(true);
        txtcategoryname=findViewById(R.id.categoryname);
        txtcategoryname.setText(categoryname);
        Paper.init(this);
        recyclerView = findViewById(R.id.Recyclercategory);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }



    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductPrice.setText("Rs " + model.getPrice() + "/-" + model.getPerpricequantity());
                        Picasso.get().load(model.getImageurl()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(type.equals("Admin")){
                                    Intent intent=new Intent(UserProductDisplayActivity.this, AdminMaintainProductsActivity.class);
                                    intent.putExtra("category",categoryname);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent=new Intent(UserProductDisplayActivity.this,ProductdetailActivity.class);
                                    intent.putExtra("category",categoryname);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }

                            }
                        });
                    }
                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };


        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
