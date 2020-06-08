package com.mj.pacholiaonlinebusinessapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mj.pacholiaonlinebusinessapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddnewProductActivity extends AppCompatActivity {
private String categoryname,description,price,nameproduct,savecurrentdate,savecurrenttime,productrandomkey,downloadimageurl,perpricequantity;
private Button btnaddnewproduct;
private ImageView imgaddproduct;
private EditText productname,productdescription,productprice,productpricequantity;
private String imageuri;
private static final int Gallerypic=1;
private StorageReference productimgref;
private DatabaseReference Productref,dumproductref;
private ProgressDialog Loadingbar;
private static int  i=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addnew_product);
        categoryname=getIntent().getExtras().get("category").toString();
        productimgref= FirebaseStorage.getInstance().getReference().child("ProductImages");
        btnaddnewproduct=findViewById(R.id.add_new_product);
        imgaddproduct=findViewById(R.id.productimage);
        productdescription=findViewById(R.id.productdescription);
        productname=findViewById(R.id.productname);
        productprice=findViewById(R.id.productprice);
        productpricequantity=findViewById(R.id.productpricequantity);
        Productref=FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname);
        dumproductref=FirebaseDatabase.getInstance().getReference().child("ProductsSearch");
        Loadingbar=new ProgressDialog(this);
        imgaddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Opengallery();
            }


        });
        btnaddnewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validateproductdata();
            }
        });

    }



    private void Opengallery() {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,Gallerypic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallerypic && resultCode==RESULT_OK && data!=null)
        {
            imageuri=data.getData().toString();
            imgaddproduct.setImageURI(Uri.parse(imageuri));

        }
    }
    private void Validateproductdata() {
        description=productdescription.getText().toString();
        price=productprice.getText().toString();
        nameproduct=productname.getText().toString();
        perpricequantity=productpricequantity.getText().toString();
        if(imageuri==null)
        {
            Toast.makeText(AdminAddnewProductActivity.this,"Choose a product image",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description))
        {
            Toast.makeText(AdminAddnewProductActivity.this,"Please enter product description...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price))
        {
            Toast.makeText(AdminAddnewProductActivity.this,"Please enter product price...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(nameproduct))
        {
            Toast.makeText(AdminAddnewProductActivity.this,"Please enter product name...",Toast.LENGTH_SHORT).show();
        }
        else
        {

            StoreProductInfo();
        }
    }

    private void StoreProductInfo() {
        Loadingbar.setTitle("Add New Product");
        Loadingbar.setMessage("Please wait while we are adding the new product.");
        Loadingbar.setCanceledOnTouchOutside(false);
        Loadingbar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MM/dd/yyyy");
        savecurrentdate=currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss");
        savecurrenttime=currenttime.format(calendar.getTime());

        //unique key
        productrandomkey=savecurrenttime + i;
        i++;

        final StorageReference filepath=productimgref.child(Uri.parse(imageuri).getLastPathSegment() + productrandomkey + "jpg");
        final UploadTask  uploadTask= filepath.putFile(Uri.parse(imageuri));
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message =e.toString();
                Toast.makeText(AdminAddnewProductActivity.this,"Error: " + message ,Toast.LENGTH_SHORT).show();
                Loadingbar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddnewProductActivity.this,"Image uploaded successfully...",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadimageurl=filepath.getDownloadUrl().toString();
                        return(filepath.getDownloadUrl());
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            Uri downloadUrl = task.getResult();
                            downloadimageurl = downloadUrl.toString();
                            Toast.makeText(AdminAddnewProductActivity.this,"Getting product image url successfully....",Toast.LENGTH_SHORT).show();
                            saveprodutinfotodatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveprodutinfotodatabase() {
        HashMap<String,Object> productmap=new HashMap<>();
        productmap.put("pid",productrandomkey);
        productmap.put("date",savecurrentdate);
        productmap.put("time",savecurrenttime);
        productmap.put("description",description);
        productmap.put("pname",nameproduct);
        productmap.put("price",price);
        productmap.put("imageurl",downloadimageurl);
        productmap.put("category",categoryname);
        productmap.put("perpricequantity",perpricequantity);
        dumproductref.child(productrandomkey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(AdminAddnewProductActivity.this,"Product is added successfully in dummyproducts....",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String message=task.getException().toString();
                    Toast.makeText(AdminAddnewProductActivity.this,"Error: " + message ,Toast.LENGTH_SHORT).show();

                }
            }
        });
        Productref.child(productrandomkey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Intent intent =new Intent(AdminAddnewProductActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);
                    Loadingbar.dismiss();
                    Toast.makeText(AdminAddnewProductActivity.this,"Product is added successfully....",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Loadingbar.dismiss();
                    String message=task.getException().toString();
                    Toast.makeText(AdminAddnewProductActivity.this,"Error: " + message ,Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
