package com.example.emedicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PaymentInfo extends AppCompatActivity {

    DrawerLayout drawerLayout;

    DatabaseReference DataRef;
    TextView appNo, day, startTime, endTime, amount, method;
    EditText cardNo, cvc, exp;
    Button btnEdit, btnDelete, btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);
        drawerLayout = findViewById(R.id.drawer_layout);

        appNo = findViewById(R.id.appointmentNoContent);
        day = findViewById(R.id.appointmentDayContent);
        startTime = findViewById(R.id.yourTimeContent1);
        endTime = findViewById(R.id.yourTimeContent2);
        cardNo = findViewById(R.id.cardNo);
        cvc = findViewById(R.id.cvc);
        exp = findViewById(R.id.exp);
        amount = findViewById(R.id.AmountContent);
        btnEdit = findViewById(R.id.buttonEditPayment);
        btnDelete = findViewById(R.id.buttonDeletePayment);
        method = findViewById(R.id.method);
        btnConfirm = findViewById(R.id.buttonConfirmPayment);

        String paymentKey = getIntent().getStringExtra("paymentKey");

        DataRef = FirebaseDatabase.getInstance().getReference().child("payment").child(paymentKey);

        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    appNo.setText(snapshot.child("AppointmentNo").getValue().toString());
                    day.setText(snapshot.child("Day").getValue().toString());
                    startTime.setText(snapshot.child("StartTime").getValue().toString());
                    endTime.setText(snapshot.child("EndTime").getValue().toString());
                    amount.setText(snapshot.child("Amount").getValue().toString());
                    method.setText(snapshot.child("Method").getValue().toString());
                    cardNo.setText(snapshot.child("CardNo").getValue().toString());
                    cvc.setText(snapshot.child("CVCNo").getValue().toString());
                    exp.setText(snapshot.child("ExpDate").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String CardNo = cardNo.getText().toString();
                final String CVC = cvc.getText().toString();
                final String EXP = exp.getText().toString();

                if( CardNo!=null && CVC!=null && EXP!=null ){
                    Intent i = new Intent(PaymentInfo.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(PaymentInfo.this, "Please Fill out All Fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String CardNo = cardNo.getText().toString();
                final String CVC = cvc.getText().toString();
                final String EXP = exp.getText().toString();

                if( CardNo!=null && CVC!=null && EXP!=null ){
                    updatePayment( CardNo, CVC, EXP );
                }
                else{
                    Toast.makeText(PaymentInfo.this, "Please Fill Out All Fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRef = FirebaseDatabase.getInstance().getReference().child("payment");
                DataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(paymentKey)) {
                            DataRef = FirebaseDatabase.getInstance().getReference().child("payment").child(paymentKey);
                            DataRef.removeValue();
                            //clearControls();

                            Toast.makeText(getApplicationContext(), "Payment Deleted Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source To Display!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void updatePayment(final String cardNo, final String cvc, final String exp) {

        DataRef = FirebaseDatabase.getInstance().getReference().child("payment");

        String testKey = getIntent().getStringExtra("paymentKey");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("CardNo",cardNo);
        hashMap.put("CVCNo",cvc);
        hashMap.put("ExpDate",exp);

        DataRef.child(testKey).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(PaymentInfo.this, "Payment Is successfully Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome (View view){ recreate(); }

    public void ClickMyProfile (View view){
        redirectActivity(this, Profile.class);
    }

    public void ClickPayment (View view){
        redirectActivity(this, Test.class);
    }

    public void ClickBMI (View view){
        redirectActivity(this, BMI.class);
    }

    public void ClickHealthCal (View view){
        redirectActivity(this, HealthCalculator.class);
    }

    public void ClickCalorieCal (View view){
        redirectActivity(this, CalorieCal.class);
    }

    public void ClickStepCounter (View view){
        redirectActivity(this, StepCounter.class);
    }

    public void ClickLogOut(View view){
        NavDrawer.logout(this);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }
}