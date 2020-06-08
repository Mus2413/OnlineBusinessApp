package com.mj.pacholiaonlinebusinessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mj.pacholiaonlinebusinessapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {
private EditText changename,changeprice,changedescription;
private Button changebutton,deletebutton;
private ImageView changeimage;
private String  productID="",categoryname="";
private DatabaseReference productsref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryname=getIntent().getExtras().get("category").toString();
        productID=getIntent().getStringExtra("pid");
        productsref= FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname).child(productID);
        setContentView(R.layout.activity_admin_maintain_products);
        changename=findViewById(R.id.change_product_name);
        changedescription=findViewById(R.id.change_product_description);
        changeimage=findViewById(R.id.change_product_image);
        changeprice=findViewById(R.id.change_product_price);
        changebutton=findViewById(R.id.apply_changes_btn);
        deletebutton=findViewById(R.id.delete_product_btn);
        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applychanges();
            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteproduct();
            }
        });
        displayproductinfo();
    }

    private void deleteproduct() {
        productsref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(AdminMaintainProductsActivity.this,"Product is deleted Successfully...",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(AdminMaintainProductsActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void applychanges() {

        String pname=changename.getText().toString();
        String pdescription=changedescription.getText().toString();
        String pprice=changeprice.getText().toString();
        if(pname.equals("")){
            Toast.makeText(AdminMaintainProductsActivity.this,"Please enter Product name.",Toast.LENGTH_LONG).show();
        }
        else if(pdescription.equals("")){
            Toast.makeText(AdminMaintainProductsActivity.this,"Please enter Product description.",Toast.LENGTH_LONG).show();
        }
        else if(pprice.equals("")){
            Toast.makeText(AdminMaintainProductsActivity.this,"Please enter Product price.",Toast.LENGTH_LONG).show();
        }
        else{
            HashMap<String,Object> productmap=new HashMap<>();
            productmap.put("pid",productID);
            productmap.put("description",pdescription);
            productmap.put("pname",pname);
            productmap.put("price",pprice);
            productsref.updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainProductsActivity.this,"Changes applied successfully....",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(AdminMaintainProductsActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {

                        String message=task.getException().toString();
                        Toast.makeText(AdminMaintainProductsActivity.this,"Error: " + message ,Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }



    }

    private void displayproductinfo() {
        productsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String pname=dataSnapshot.child("pname").getValue().toString();
                    String pprice=dataSnapshot.child("price").getValue().toString();
                    String pdescription=dataSnapshot.child("description").getValue().toString();
                    String pimage=dataSnapshot.child("imageurl").getValue().toString();

                    changename.setText(pname);
                    changedescription.setText(pdescription);
                    changeprice.setText(pprice);
                    Picasso.get().load(pimage).into(changeimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
