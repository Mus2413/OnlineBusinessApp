package com.mj.pacholiaonlinebusinessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminCategoryActivity;
import com.mj.pacholiaonlinebusinessapp.Admin.AdminMaintainProductsActivity;
import com.mj.pacholiaonlinebusinessapp.Prevalent.prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmCartActivity extends AppCompatActivity {
    private EditText nameedttxt, phnedttxt, addressedttxt, cityedttxt;
    private Button Confirmorderbtn;
    private String totalamount = "", shippingstatus = "";
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cart);
        totalamount = getIntent().getStringExtra("Totalprice");
        Toast.makeText(ConfirmCartActivity.this, "Total Price = Rs- " + totalamount + "/-", Toast.LENGTH_SHORT).show();
        nameedttxt = findViewById(R.id.edtcnfrmname);
        phnedttxt = findViewById(R.id.edtcnfrmnum);
        addressedttxt = findViewById(R.id.edtcnfrmaddress);
        cityedttxt = findViewById(R.id.edtcnfrmcity);
        Confirmorderbtn = findViewById(R.id.shipmentbutton);
        Confirmorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkallmethodsfill();



                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ConfirmCartActivity.this, default_notification_channel_id
                )
                        .setSmallIcon(R.drawable.ic_launcher_notification_foreground)
                        .setContentTitle("Order")
                        .setSound(alarmSound)
                        .setContentText("You order has been placed successfully.");
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .build();
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new
                            NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.WHITE);
                    notificationChannel.enableVibration(true);
                   notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationChannel.setSound(alarmSound, audioAttributes);
                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    assert mNotificationManager != null;
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
                assert mNotificationManager != null;
                mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());


            }


        });

    }
    private void checkallmethodsfill() {
        if(TextUtils.isEmpty(nameedttxt.getText().toString()))
        {
            Toast.makeText(ConfirmCartActivity.this,"Please enter your name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phnedttxt.getText().toString()))
        {
            Toast.makeText(ConfirmCartActivity.this,"Please enter your Phone number",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(addressedttxt.getText().toString()))
        {
            Toast.makeText(ConfirmCartActivity.this,"Please enter your full address",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(cityedttxt.getText().toString()))
        {
            Toast.makeText(ConfirmCartActivity.this,"Please enter your City",Toast.LENGTH_SHORT).show();

        }
        else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
       final String savecurtime,savcurdate;
        Calendar calfordate = Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MM/dd/yyyy");
        savcurdate=currentdate.format(calfordate.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss");
        savecurtime=currenttime.format(calfordate.getTime());


        final DatabaseReference orderef= FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(prevalent.currentOnlineUser.getPhone().toString());
         HashMap<String,Object> ordermap=new HashMap<>();
        ordermap.put("totalamount",totalamount);
        ordermap.put("name",nameedttxt.getText().toString());
        ordermap.put("phonenumber",phnedttxt.getText().toString());
        ordermap.put("address",addressedttxt.getText().toString());
        ordermap.put("city",cityedttxt.getText().toString());
        ordermap.put("date",savcurdate);
        ordermap.put("time",savecurtime);
        ordermap.put("state", "Not shipped");
        orderef.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cartlist").child("Userview")
                            .child(prevalent.currentOnlineUser.getPhone().toString())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ConfirmCartActivity.this,"Your Order has been placed successfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ConfirmCartActivity.this,HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }
}
