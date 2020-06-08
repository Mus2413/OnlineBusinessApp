package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtregister, txtnotadminregister;
    private EditText edtregname, edtregphone, edtregpass;
    private Button btnregister;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtregister = findViewById(R.id.registertxt);
        txtnotadminregister = findViewById(R.id.not_admin_panel_link_register);
        edtregname = findViewById(R.id.registernameinput);
        edtregphone = findViewById(R.id.registerphoneinput);
        edtregpass = findViewById(R.id.registerpassinput);
        btnregister = findViewById(R.id.register_btn);
        LoadingBar=new ProgressDialog(this);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount(){
        String name=edtregname.getText().toString();
        String phone=edtregphone.getText().toString();
        String password=edtregpass.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();}
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();}
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();}
        else
        {
            LoadingBar.setTitle("Create Account");
            LoadingBar.setMessage("Please wait while we are checking the credentials.");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();
            Validatephonenum(name,phone,password);
        }
    }
    private void Validatephonenum(final String name, final String phone, final String password)
    {
        final DatabaseReference Rootreference;
        Rootreference = FirebaseDatabase.getInstance().getReference();
        Rootreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(phone).exists())
                {
                    HashMap<String,Object> userdatamap =new HashMap<>();
                    userdatamap.put("phone",phone);
                    userdatamap.put("name",name);
                    userdatamap.put("password",password);

                    Rootreference.child("Users").child(phone).updateChildren(userdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Congratulations, Your account have been created !",Toast.LENGTH_SHORT).show();
                                        LoadingBar.dismiss();
                                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        LoadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network Error!,Please try again...",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"User already exists",Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Please try again using another number",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
