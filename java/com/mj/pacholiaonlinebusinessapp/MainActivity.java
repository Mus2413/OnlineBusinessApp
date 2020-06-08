package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.model.users;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    private TextView txtSlogan,getTxtSlogan2,txtlogo;
    private Button btnjoin,btnlogin;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSlogan=findViewById(R.id.appSlogan);
        getTxtSlogan2=findViewById(R.id.appSlogan2);
        btnjoin=findViewById(R.id.main_join_now_btn);
        btnlogin=findViewById(R.id.main_login_btn);
        LoadingBar=new ProgressDialog(this);
        Paper.init(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

       String UserPhonekey=Paper.book().read(prevalent.Userphonekey);
       String UserPasswordkey=Paper.book().read(prevalent.Userpasswordkey);
       if(UserPhonekey !=" " && UserPasswordkey!=" ")
       {
           if(!TextUtils.isEmpty(UserPhonekey) && !TextUtils.isEmpty(UserPasswordkey))
           {
               Allowaccess(UserPhonekey,UserPasswordkey);

               LoadingBar.setTitle("Already logged in");
               LoadingBar.setMessage("Please wait......");
               LoadingBar.setCanceledOnTouchOutside(false);
               LoadingBar.show();
           }
       }

    }

    private void Allowaccess(final String phone, final String password) {


        final DatabaseReference Rootreference;
        Rootreference = FirebaseDatabase.getInstance().getReference();
        Rootreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()) {
                    users usersdata = dataSnapshot.child("Users").child(phone).getValue(users.class);
                    if (usersdata.getPhone().equals(phone)) {
                        if (usersdata.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            prevalent.currentOnlineUser=usersdata;
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, " Account with " + phone + " number does not exists.", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
