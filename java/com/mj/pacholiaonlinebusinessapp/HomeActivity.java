package com.mj.pacholiaonlinebusinessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminAddnewProductActivity;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminCategoryActivity;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminMaintainProductsActivity;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminNewOrderActivity;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.ViewHolder.ProductViewHolder;
import com.mj.pacholiaonlinebusinessapp.model.Products;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ImageView imgdaal,imgrice,imgaata,imgdaliya,imgsoya,imgmasala,imgbodysoaps,imgwashingsoaps,imgfairlovely,
            imgcookingoil,imghairoil,imgtoothpaste;
    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4,linearLayout5,linearLayout6;
private  String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        if(bundle!=null)
        {
            type = getIntent().getExtras().get("Admin").toString();
        }
        else{
            type="Users";
        }
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



        imgaata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, UserProductDisplayActivity.class);
                intent.putExtra("category","Wheat");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgdaal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Pulses");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Rice");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgcookingoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Cookingoil");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgmasala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Spices");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgbodysoaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Bodysoaps");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgtoothpaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Toothpaste");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imghairoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Hairoil");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgfairlovely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Beauty");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgwashingsoaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Washingsoaps");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });
        imgdaliya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","Cereals");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });

        imgsoya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,UserProductDisplayActivity.class);
                intent.putExtra("category","soya");
                intent.putExtra("person",type);
                startActivity(intent);

            }
        });



        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        ProductsRef.keepSynced(true);


        Paper.init(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!type.equals("Admin")){
                    startActivity(new Intent(HomeActivity.this, CartActivity.class));
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);
       if(!type.equals("Admin"))
       {
           if(prevalent.currentOnlineUser.getName()!=null){
               userNameTextView.setText(prevalent.currentOnlineUser.getName().toString());}
           if(prevalent.currentOnlineUser.getImage()!=null){
               Picasso.get().load(prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);}
       }

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(type.equals("Admin")){
            startActivity(new Intent(HomeActivity.this, AdminCategoryActivity.class));}
            AlertDialog.Builder ad1=new AlertDialog.Builder(this);
            ad1.setMessage("Are you sure you want to exit? ");
            ad1.setCancelable(false);


            ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {


                }
            });

            ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                    //Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    //startActivity(intent);

                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            });
            AlertDialog alert=ad1.create();
            alert.show();
           // super.onBackPressed();

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

       if (id == R.id.action_settings)
        {
            if(!type.equals("Admin"))
            {
                startActivity(new Intent(HomeActivity.this,Settings.class));
            }

      }

                return super.onOptionsItemSelected(item);
            }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            if(!type.equals("Admin")){
            startActivity(new Intent(HomeActivity.this,CartActivity.class));}

        }

        else if (id == R.id.nav_search)
        {
            if(!type.equals("Admin")){
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_settings)
        {
            if(!type.equals("Admin")){
                Intent intent = new Intent(HomeActivity.this, Settings.class);
                startActivity(intent);
            }

        }
        else if (id == R.id.nav_logout)
        {
            if(!type.equals("Admin")){
                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
