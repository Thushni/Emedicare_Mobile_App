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

public class Payment extends AppCompatActivity {

    DrawerLayout drawerLayout;

    DatabaseReference DataRef, ref;
    TextView appNo, day, startTime, endTime, amount;
    Button btnPay;
    EditText cardNo, cvc, exp;
    private RadioGroup methodRadio;
    private RadioButton methodButton;
    String method, AppNo, Day, StartTime, EndTime;
    String Amount = "4326";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        appNo = findViewById(R.id.appointmentNoContent);
        day = findViewById(R.id.appointmentDayContent);
        startTime = findViewById(R.id.yourTimeContent1);
        endTime = findViewById(R.id.yourTimeContent2);
        cardNo = findViewById(R.id.cardNo);
        cvc = findViewById(R.id.cvc);
        exp = findViewById(R.id.exp);
        amount = findViewById(R.id.AmountContent);
        btnPay = findViewById(R.id.registerPayment);

        drawerLayout = findViewById(R.id.drawer_layout);


        String testKey = getIntent().getStringExtra("testKey");
        String doc_test = getIntent().getStringExtra("doc_test");

        methodRadio = (RadioGroup) findViewById(R.id.radioMethodGroup);

        ref = FirebaseDatabase.getInstance().getReference().child("payment");

        methodRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                methodButton = findViewById(methodRadio.getCheckedRadioButtonId());
                method = methodButton.getText().toString().trim();
            }
        });

        DataRef = FirebaseDatabase.getInstance().getReference().child("test").child(testKey);

        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    appNo.setText(snapshot.child("AppointmentNo").getValue().toString());
                    day.setText(snapshot.child("Day").getValue().toString());
                    startTime.setText(snapshot.child("StartTime").getValue().toString());
                    endTime.setText(snapshot.child("EndTime").getValue().toString());
                    amount.setText(Amount);

                    AppNo = snapshot.child("AppointmentNo").getValue().toString();
                    Day = snapshot.child("Day").getValue().toString();
                    StartTime = snapshot.child("StartTime").getValue().toString();
                    EndTime = snapshot.child("EndTime").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String CardNo = cardNo.getText().toString();
                String CVC = cvc.getText().toString();
                String EXP = exp.getText().toString();

                if(methodRadio.getCheckedRadioButtonId() == -1){
                    Toast.makeText(Payment.this, "Select your payment method", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(CardNo.isEmpty()){
                    cardNo.setError("Card Number is Required");
                    cardNo.requestFocus();
                    return;
                }

                if(CVC.isEmpty()){
                    cvc.setError("CVC is Required");
                    cvc.requestFocus();
                    return;
                }

                if(EXP.isEmpty()){
                    exp.setError("Expire Date is Required");
                    exp.requestFocus();
                    return;
                }

                uploadPayment(AppNo, Day, StartTime, EndTime, Amount, method, CardNo, CVC, EXP);
            }
        });
    }


    private void uploadPayment( final String appNo, final String day, final String startTime, final String endTime,final String amount,final String method, final String cardNo,final String CVCNo,final String expDate) {

        final String key = ref.push().getKey();

        HashMap hashMap = new HashMap();
        hashMap.put("AppointmentNo", appNo);
        hashMap.put("Day", day);
        hashMap.put("StartTime",startTime);
        hashMap.put("EndTime",endTime);
        hashMap.put("Amount",amount);
        hashMap.put("Method",method);
        hashMap.put("CardNo",cardNo);
        hashMap.put("CVCNo",CVCNo);
        hashMap.put("ExpDate",expDate);


        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent i = new Intent(Payment.this, PaymentInfo.class);
                i.putExtra("paymentKey", key);
                startActivity(i);
                Toast.makeText(Payment.this, "Payment Added Successfully", Toast.LENGTH_SHORT).show();
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