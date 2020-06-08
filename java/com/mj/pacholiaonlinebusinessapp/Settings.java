package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {

    private TextView txtclose,txtupdate,txtprofilepicture;
    private EditText edtphonenumber,edtname,edtaddress;
    private CircleImageView imgprofilepic;
    private AppBarLayout appBarLayout;
    private Button securitybtn;


    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        txtclose=findViewById(R.id.close);
        txtupdate=findViewById(R.id.update);
        txtprofilepicture=findViewById(R.id.change_profile_picture);
        edtaddress=findViewById(R.id.setting_adress);
        edtphonenumber=findViewById(R.id.setting_phone_number);
        edtname=findViewById(R.id.setting_full_name);
        imgprofilepic=findViewById(R.id.settingsprofileimage);
        appBarLayout=findViewById(R.id.appbarsettings);
        securitybtn=findViewById(R.id.setting_security);
        Userinfodisplay(imgprofilepic,edtname,edtphonenumber,edtaddress);
        storageProfilePrictureRef= FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        txtupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });
        txtprofilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(Settings.this);
            }
        });
        securitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settings.this,ResetPasswordActivity.class);
                intent.putExtra("check","settings");
                startActivity(intent);
            }
        });
    }



    private void Userinfodisplay(final CircleImageView imgprofilepic, final EditText edtname, final EditText edtphonenumber, final EditText edtaddress) {
        DatabaseReference Usererf= FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.currentOnlineUser.getPhone());
        Usererf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("image").exists()){
                        String image =dataSnapshot.child("image").getValue().toString();
                        String name =dataSnapshot.child("name").getValue().toString();
                        String phone =dataSnapshot.child("phone").getValue().toString();
                        String address =dataSnapshot.child("address").getValue().toString();
                        Picasso.get().load(image).into(imgprofilepic);
                        edtaddress.setText(address);
                        edtname.setText(name);
                        edtphonenumber.setText(phone);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", edtname.getText().toString());
        userMap. put("address", edtaddress.getText().toString());
        userMap. put("phoneOrder", edtphonenumber.getText().toString());
        ref.child(prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);
       Intent intent =new Intent(Settings.this,HomeActivity.class);
       startActivity(intent);
        Toast.makeText(Settings.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            imgprofilepic.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(Settings.this, Settings.class));
            finish();
        }
    }
    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(edtname.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(edtaddress.getText().toString()))
        {
            Toast.makeText(this, "Name is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(edtphonenumber.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }
    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(prevalent.currentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap. put("name", edtname.getText().toString());
                                userMap. put("address", edtaddress.getText().toString());
                                userMap. put("phoneOrder", edtphonenumber.getText().toString());
                                userMap. put("image", myUrl);
                                ref.child(prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                                progressDialog.dismiss();
                                Intent intent =new Intent(Settings.this,HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(Settings.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Settings.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }
}
