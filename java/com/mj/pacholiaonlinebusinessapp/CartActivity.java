package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;
import com.mj.pacholiaonlinebusinessapp.model.Cart;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recview;
    private TextView txttotalprice,txtmsg1;
    private Button nextbutton;
    private double Overalltotalprice=0;
    RecyclerView.LayoutManager layoutManager;
    double OnetypeProductTprice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recview=findViewById(R.id.recyclerviewcartlist);
        txttotalprice=findViewById(R.id.txtfinalprice);
        nextbutton=findViewById(R.id.nextbutton);
        txtmsg1=findViewById(R.id.msg1);
        layoutManager= new LinearLayoutManager(this);
        recview.setLayoutManager(layoutManager);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(CartActivity.this,ConfirmCartActivity.class);
                intent.putExtra("Totalprice",String.valueOf(Overalltotalprice));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkorderstate();
        final DatabaseReference cartlistref= FirebaseDatabase.getInstance().getReference().child("Cartlist");
        FirebaseRecyclerOptions<Cart> Options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartlistref.child("Userview")
                        .child(prevalent.currentOnlineUser.getPhone().toString())
                        .child("ProductsItem"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart,cartviewholder> adapter=new FirebaseRecyclerAdapter<Cart, cartviewholder>(Options) {
            @Override
            protected void onBindViewHolder(@NonNull cartviewholder holder, int position, @NonNull final Cart model) {
                holder.txtproductquantity.setText("Product Quantity = " + model.getProductquantity());
                holder.txtproductprice.setText("Product Price = " + model.getProductprice() + "/- " + model.getPerpricequantity());
                holder.txtproductname.setText("Product Name = " + model.getProductname());
                int index= model.getProductquantityamount();


                    double amountarr[]={0.01,0.02,0.05,0.1,0.2,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,10,15,20,0.5,1,1.5,2,5,10,15,20,1,2,3,4,5,6,7,8,9,10};
                    OnetypeProductTprice=((Integer.valueOf(model.getProductprice()))*amountarr[index]);
                    Overalltotalprice=Overalltotalprice+OnetypeProductTprice;




                txttotalprice.setText("Total Price = Rs- " + String.valueOf(Overalltotalprice) + "/-");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence option[]=new CharSequence[]{
                                "Edit","Delete"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options");
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0)
                                {

                                    Intent intent=new Intent(CartActivity.this,ProductdetailActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                                if(i==1)
                                {
                                    cartlistref.child("Userview").child(prevalent.currentOnlineUser.getPhone().toString()).child("ProductsItem")
                                            .child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(CartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                cartviewholder cartviewholder= new cartviewholder(view);
                return cartviewholder;
            }
        };
        recview.setAdapter(adapter);
        adapter.startListening();
    }


    private void checkorderstate(){
        DatabaseReference orderef=FirebaseDatabase.getInstance().getReference().child("Orders").child(prevalent.currentOnlineUser.getPhone().toString());
        orderef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String shippingstate= dataSnapshot.child("state").getValue().toString();
                    String username= dataSnapshot.child("name").getValue().toString();
                    if(shippingstate.equals("shipped")){
                        txttotalprice.setText("Dear, " + username + "\nYour Order has been placed successfully and your total Price = Rs- " + Overalltotalprice +"/-");
                        recview.setVisibility(View.GONE);
                        txtmsg1.setText("Congratulations, Your final order has been shipped successfully. Soon, It will be verified.\n" +
                                "Thank you!!!\"");
                        txtmsg1.setVisibility(View.VISIBLE);
                        nextbutton.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this, "You can purchase more items, once you received your placed order", Toast.LENGTH_SHORT).show();
                    }
                    else if(shippingstate.equals("Not shipped")){
                        txttotalprice.setText("Dear, " + username + "\nShipping state : Not Shipped");
                        recview.setVisibility(View.GONE);
                        txtmsg1.setVisibility(View.VISIBLE);
                        nextbutton.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this, "You can purchase more items, once you received your placed order", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
