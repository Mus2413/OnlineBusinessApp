package com.mj.pacholiaonlinebusinessapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.R;
import com.mj.pacholiaonlinebusinessapp.model.AdminOrders;

public class AdminNewOrderActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private DatabaseReference ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_new_order);
        recyclerView=findViewById(R.id.recyclervieworderlist);
        ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerOptions<AdminOrders> options=new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef,AdminOrders.class).build();
        FirebaseRecyclerAdapter<AdminOrders,Adminorderplaceholder> adapter=new FirebaseRecyclerAdapter<AdminOrders, Adminorderplaceholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Adminorderplaceholder holder, final int position, @NonNull final AdminOrders model) {
                holder.username.setText("Name : " + model.getName());
                holder.userphn.setText("Phone Number : " + model.getPhonenumber());
                holder.useramount.setText("Total Amount : " + model.getTotalamount());
                holder.orderdate.setText("Date and Time : " + model.getName() + " and " + model.getTime());
                holder.useraddress.setText("Address: " + model.getAddress() + ", " + model.getCity());
                holder.vieworderbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uId=getRef(position).getKey();
                        Intent intent= new Intent(AdminNewOrderActivity.this, AdminUserProductsActivity.class);
                        intent.putExtra("uid",uId);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence option[]=new CharSequence[]{
                                "Yes",
                                "No"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrderActivity.this);
                        builder.setTitle("Have you shipped this order products?");
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0)
                                {
                                    String uId=getRef(position).getKey();
                                    RemoveOrder(uId);
                                }
                                else{
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public Adminorderplaceholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_check_order_layout,parent,false);

                return new Adminorderplaceholder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    public static class Adminorderplaceholder extends RecyclerView.ViewHolder {
        public TextView username,userphn,useramount,useraddress,orderdate;
        public Button vieworderbtn;
        public Adminorderplaceholder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.custusername);
            userphn=itemView.findViewById(R.id.custphonenumber);
            useraddress=itemView.findViewById(R.id.custaddress);
            useramount=itemView.findViewById(R.id.custtotalprice);
            orderdate=itemView.findViewById(R.id.custorderdate);
            vieworderbtn=itemView.findViewById(R.id.view_order_details);
        }
    }
    private void RemoveOrder(String uId) {
        ordersRef.child(uId).removeValue();
        FirebaseDatabase.getInstance().getReference().child("Cartlist").child("Adminview")
                .child(prevalent.currentOnlineUser.getPhone().toString())
                .removeValue();
    }
}
