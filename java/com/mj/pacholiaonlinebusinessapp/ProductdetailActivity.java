package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.model.Products;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductdetailActivity extends AppCompatActivity {
    private Button addtocart;
    private ImageView productimg;
    private TextView txtproductdetailname,txtproductdetaildesc,txtproductdetailprice,txtproductdetailnamefinalprice;
   private String  productID="",productprice,price,quantityperprice,state="Normal";
   private Spinner spinner;
    private DatabaseReference productref;
   private int value;
   private String categoryname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        categoryname=getIntent().getExtras().get("category").toString();
        addtocart=findViewById(R.id.addtocart);
        spinner=findViewById(R.id.dropdownllist);
        txtproductdetaildesc=findViewById(R.id.product_description_detail);
        txtproductdetailname=findViewById(R.id.product_name_detail);
        txtproductdetailnamefinalprice=findViewById(R.id.product_finalprice_detail);
        txtproductdetailprice=findViewById(R.id.product_price_detail);
        productimg=findViewById(R.id.product_image_detail);
        productID=getIntent().getStringExtra("pid");
        productref= FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname);


            String[] items = new String[]{"10gm", "20gm", "50gm", "100gm", "200gm", "500gm", "1kg", "1.5kg", "2kg", "2.5kg", "3kg", "3.5kg", "4kg", "4.5kg"
                    , "5kg", "5.5kg", "10kg", "15kg", "20kg",
                    "500ml","1lt","1.5lt","2lt","5lt","10lt","15lt","20lt","1pcs","2pcs","3pcs","4pcs","5pcs","6pcs","7pcs","8pcs","9pcs","10pcs"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
            spinner.setAdapter(adapter);


        getproductdetails(productID);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state.equals("Order Placed") | state.equals("Order Shipped")){

                    Toast.makeText(ProductdetailActivity.this,"You can place more orders,Once your order is shipped or confirmed",Toast.LENGTH_LONG).show();
            }
                else{
                    addtocartlist();}

        }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkorderstate();
    }

    private void addtocartlist() {
        String savecurtime,savcurdate;
        Calendar calfordate = Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MM/dd/yyyy");
        savcurdate=currentdate.format(calfordate.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss");
        savecurtime=currenttime.format(calfordate.getTime());
       final  DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference().child("Cartlist");
        final HashMap<String,Object>carthashmap=new HashMap<>();
        carthashmap.put("date",savcurdate);
        carthashmap.put("time",savecurtime);
        carthashmap.put("pid",productID);
        carthashmap.put("productname",txtproductdetailname.getText().toString());
        carthashmap.put("productdesc",txtproductdetaildesc.getText().toString());
        carthashmap.put("productprice",price);
        carthashmap.put("discount",null);
        carthashmap.put("category",categoryname);
        carthashmap.put("perpricequantity",quantityperprice);
        carthashmap.put("productquantity",spinner.getSelectedItem().toString());
        carthashmap.put("productquantityamount",spinner.getSelectedItemPosition());
        cartlistref.child("Userview").child(prevalent.currentOnlineUser.getPhone().toString())
                .child("ProductsItem").child(productID).updateChildren(carthashmap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    cartlistref.child("Adminview").child(prevalent.currentOnlineUser.getPhone().toString()).child("ProductsItem")
                            .child(productID).updateChildren(carthashmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ProductdetailActivity.this,"Add to cart Successfully...",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProductdetailActivity.this,UserProductDisplayActivity.class);
                                        intent.putExtra("category",categoryname);
                                        intent.putExtra("person","user");
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });

    }

    private void getproductdetails(String productID) {

        productref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Products products=dataSnapshot.getValue(Products.class);
                    txtproductdetailname.setText(products.getPname());
                    txtproductdetaildesc.setText(products.getDescription());
                    productprice="Rs- " + products.getPrice() +"/-" + products.getPerpricequantity();
                    txtproductdetailprice.setText(productprice);
                    price=products.getPrice();
                    quantityperprice=products.getPerpricequantity();
                    Picasso.get().load(products.getImageurl()).into(productimg);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void checkorderstate(){
        DatabaseReference orderef=FirebaseDatabase.getInstance().getReference().child("Orders").child(prevalent.currentOnlineUser.getPhone().toString());
        orderef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String shippingstate= dataSnapshot.child("state").getValue().toString();
                    if(shippingstate.equals("shipped")){
                        state="Order Shipped";
                    }
                    else if(shippingstate.equals("Not shipped")) {
                        state="Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

