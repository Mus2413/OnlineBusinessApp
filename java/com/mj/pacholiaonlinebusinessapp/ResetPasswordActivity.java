package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.model.users;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {
private String check="";
private TextView txtresetpasswordtitle,txttitleques;
private EditText phonenumberid,question1,question2,question3;
private Button resetpasswordbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        check= getIntent().getStringExtra("check");
        txtresetpasswordtitle=findViewById(R.id.txtresetpasswordheading);
        txttitleques=findViewById(R.id.title_questions);
        phonenumberid=findViewById(R.id.edtphonenumberid);
        question1=findViewById(R.id.question_1);
        question2=findViewById(R.id.question_2);
        question3=findViewById(R.id.question_3);
        resetpasswordbtn=findViewById(R.id.verify_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        phonenumberid.setVisibility(View.GONE);

        if(check.equals("settings")){
            txtresetpasswordtitle.setText("Set Questions");

            txttitleques.setText("Please set Answers for the following security questions");
            resetpasswordbtn.setText("Set");
            displayanswers();
            resetpasswordbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setanswers();

                }
            });

        }
        else if(check.equals("login")){
            phonenumberid.setVisibility(View.VISIBLE);
            resetpasswordbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifydata();
                }
            });
        }
    }
    private void setanswers(){
        String questionno1=question1.getText().toString().toLowerCase();
        String questionno2=question2.getText().toString().toLowerCase();
        String questionno3=question3.getText().toString().toLowerCase();
        if(questionno1.equals("") && questionno2.equals("") && questionno3.equals("")){
            Toast.makeText(ResetPasswordActivity.this,"Please answer all questions",Toast.LENGTH_LONG).show();
        }
        else{
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(prevalent.currentOnlineUser.getPhone().toString());

            HashMap<String,Object> userdatamap =new HashMap<>();
            userdatamap.put("answer1",questionno1);
            userdatamap.put("answer2",questionno2);
            userdatamap.put("answer3",questionno3);
            ref.child("Security Questions").updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this,"Security questions set successfully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }


    private void displayanswers(){
      final  DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users")
                .child(prevalent.currentOnlineUser.getPhone().toString());
        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String ans1=dataSnapshot.child("answer1").getValue().toString();
                    String ans2=dataSnapshot.child("answer2").getValue().toString();
                    String ans3=dataSnapshot.child("answer3").getValue().toString();
                    question1.setText(ans1);
                    question2.setText(ans2);
                    question3.setText(ans3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void verifydata(){

        final String phone=phonenumberid.getText().toString();
        final String questionno1=question1.getText().toString().toLowerCase();
        final String questionno2=question2.getText().toString().toLowerCase();
        final String questionno3=question3.getText().toString().toLowerCase();
        if(!phone.equals("") && !questionno1.equals("") && !questionno2.equals("") &&!questionno3.equals("")){
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(phone);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.hasChild("Security Questions")) {
                            String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();
                            String ans3 = dataSnapshot.child("Security Questions").child("answer3").getValue().toString();

                            if (!ans1.equals(questionno1)) {
                                Toast.makeText(ResetPasswordActivity.this, "First answer is incorrect", Toast.LENGTH_LONG).show();
                            } else if (!ans2.equals(questionno2)) {
                                Toast.makeText(ResetPasswordActivity.this, "Second answer is incorrect", Toast.LENGTH_LONG).show();
                            } else if (!ans3.equals(questionno3)) {
                                Toast.makeText(ResetPasswordActivity.this, "Third answer is incorrect", Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("New Password");
                                final EditText newpass = new EditText(ResetPasswordActivity.this);
                                newpass.setHint("Write password here...");
                                builder.setView(newpass);
                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!newpass.getText().toString().equals("")) {
                                            ref.child("password").setValue(newpass.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(ResetPasswordActivity.this, "Password changed Successfully", Toast.LENGTH_LONG).show();
                                                                startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();

                            }
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "You have not set Security questions.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(ResetPasswordActivity.this, "User with this number does not exists.", Toast.LENGTH_LONG).show();
                    }

                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(ResetPasswordActivity.this, "Please fill the details.", Toast.LENGTH_LONG).show();
        }

    }
}
