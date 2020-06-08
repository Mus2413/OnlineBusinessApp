package com.mj.pacholiaonlinebusinessapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mj.pacholiaonlinebusinessapp.HomeActivity;
import com.mj.pacholiaonlinebusinessapp.MainActivity;
import com.mj.pacholiaonlinebusinessapp.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView imgdaal,imgrice,imgaata,imgdaliya,imgsoya,imgmasala,imgbodysoaps,imgwashingsoaps,imgfairlovely,
    imgcookingoil,imghairoil,imgtoothpaste;
    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4,linearLayout5,linearLayout6;
    private Button sloganbtn,logoutbtn,checkneworderbtn,maintainbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        imgaata=findViewById(R.id.aataimages);
        imgbodysoaps=findViewById(R.id.bodyessentialsimage);
        imgdaal=findViewById(R.id.daalimage);
        imgrice=findViewById(R.id.riceimage);
        imgdaliya=findViewById(R.id.daliyaimage);
        imgsoya=findViewById(R.id.soyaimages);
        imgtoothpaste=findViewById(R.id.toothpasteimage);
        imghairoil=findViewById(R.id.hairoilimage);
        imgwashingsoaps=findViewById(R.id.washingshopimages);
        imgfairlovely=findViewById(R.id.fairlovelyimages);
        imgcookingoil=findViewById(R.id.cookingoilimage);
        imgsoya=findViewById(R.id.soyaimages);
        imgmasala=findViewById(R.id.masalaimage);
        linearLayout1=findViewById(R.id.linear1_category_layout);
        linearLayout2=findViewById(R.id.linear2_category_layout);
        linearLayout3=findViewById(R.id.linear3_category_layout);
        linearLayout4=findViewById(R.id.linear4_category_layout);
        linearLayout5=findViewById(R.id.linear5_btnlayout);
        linearLayout6=findViewById(R.id.linear6_btnlayout);
        logoutbtn=findViewById(R.id.admin_logout_btn);
        checkneworderbtn=findViewById(R.id.check_new_order);
        sloganbtn=findViewById(R.id.slogan_category);


        maintainbutton=findViewById(R.id.admin_maintain_btn);
        maintainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
                finish();
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
        });
        checkneworderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, AdminNewOrderActivity.class);
                startActivity(intent);
            }
        });

        imgaata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, AdminAddnewProductActivity.class);
                intent.putExtra("category","Wheat");
                startActivity(intent);

            }
        });
        imgdaal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Pulses");
                startActivity(intent);

            }
        });
        imgrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Rice");
                startActivity(intent);

            }
        });
        imgcookingoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Cookingoil");
                startActivity(intent);

            }
        });
        imgmasala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Spices");
                startActivity(intent);

            }
        });
        imgbodysoaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Bodysoaps");
                startActivity(intent);

            }
        });
        imgtoothpaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Toothpaste");
                startActivity(intent);

            }
        });
        imghairoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Hairoil");
                startActivity(intent);

            }
        });
        imgfairlovely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Beauty");
                startActivity(intent);

            }
        });
        imgwashingsoaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Washingsoaps");
                startActivity(intent);

            }
        });
        imgdaliya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","Cereals");
                startActivity(intent);

            }
        });

        imgsoya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddnewProductActivity.class);
                intent.putExtra("category","soya");
                startActivity(intent);

            }
        });

    }

}
