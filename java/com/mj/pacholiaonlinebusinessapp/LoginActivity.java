package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminCategoryActivity;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.model.users;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

private TextView txtlogin , txtforgetpass,txtadmin,txtnotadmin;
private EditText edtlogin,edtpass;
private LinearLayout linearLayout;
private CheckBox checkBox;
private Button btnloginuser;
private String parentdbname="Users";
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtlogin=findViewById(R.id.logintxt);
        txtforgetpass=findViewById(R.id.forgetpass);
        txtadmin=findViewById(R.id.admin_panel_link);
        txtnotadmin=findViewById(R.id.not_admin_panel_link);
        btnloginuser=findViewById(R.id.login_btn);
        checkBox=findViewById(R.id.remember_me_checkbox);
        linearLayout=findViewById(R.id.linear_layout);
        edtlogin=findViewById(R.id.loginphoneinput);
        edtpass=findViewById(R.id.loginpassinput);
        LoadingBar=new ProgressDialog(this);
        btnloginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginuser();
            }
        });
        Paper.init(this);

        txtadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnloginuser.setText("Login Admin");
                txtadmin.setVisibility(View.INVISIBLE);
                txtnotadmin.setVisibility(View.VISIBLE);
                parentdbname="Admins";
            }
        });
        txtnotadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnloginuser.setText("Login");
                txtadmin.setVisibility(View.VISIBLE);
                txtnotadmin.setVisibility(View.INVISIBLE);
                parentdbname="Users";
            }
        });
        txtforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });
    }
    private void loginuser(){

        String phone=edtlogin.getText().toString();
        String password=edtpass.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();}
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();}
        else{
            LoadingBar.setTitle("Login Account");
            LoadingBar.setMessage("Please wait while we are checking the credentials.");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();
            Allowaccesstoaccount(phone,password);
        }
    }
    private void Allowaccesstoaccount(final String phone, final String password){

        if(checkBox.isChecked())
        {
            Paper.book().write(prevalent.Userphonekey,phone);
            Paper.book().write(prevalent.Userpasswordkey,password);
        }


        final DatabaseReference Rootreference;
        Rootreference = FirebaseDatabase.getInstance().getReference();
        Rootreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentdbname).child(phone).exists()){
                    users usersdata= dataSnapshot.child(parentdbname).child(phone).getValue(users.class);

                    if(usersdata.getPhone().equals(phone))
                    {
                        if(usersdata.getPassword().equals(password))
                        {
                            if(parentdbname.equals("Admins"))
                            {

                                Toast.makeText(LoginActivity.this, "Welcome Admin, You are logged in Successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if(parentdbname.equals("Users"))
                            {

                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                prevalent.currentOnlineUser= usersdata;
                                startActivity(intent);
                            }

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Incorrect password",Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this," Account with " + phone +" number does not exists.",Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                
            }

        });
    }
}
