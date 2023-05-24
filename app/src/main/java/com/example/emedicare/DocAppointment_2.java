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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DocAppointment_2 extends AppCompatActivity {

    DrawerLayout drawerLayout;

    TextView appoTitle, docTitle, availableHospital;
    RadioButton time1,time2,timeButton;
    private RadioGroup timeRadio;
    String time, appointmentNo;

    Button buttonOkTest;

    DatabaseReference ref,refAuth;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private String userID;

    String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appointment2);

        drawerLayout = findViewById(R.id.drawer_layout);

        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        refAuth = FirebaseDatabase.getInstance().getReference().child("Users");
        userID = user.getUid();

        refAuth.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    fullName = userProfile.fullName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DocAppointment_2.this, "Something Wrong Happend!" ,Toast.LENGTH_LONG).show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("docAppointment");

        String Test_name = getIntent().getStringExtra("Test_name");
        String Doc_name = getIntent().getStringExtra("Doc_name");
        String Hospital_name = getIntent().getStringExtra("Hospital_name");

        appoTitle = findViewById(R.id.appoTitle);
        docTitle = findViewById(R.id.docTitle);
        availableHospital = findViewById(R.id.availableHospital);
        timeRadio = findViewById(R.id.radioGroupDayandTime);

        buttonOkTest = findViewById(R.id.buttonOkTest);

        time1 = findViewById(R.id.Time1);
        time2 = findViewById(R.id.Time2);

        appoTitle.setText(Test_name);
        docTitle.setText(Doc_name);
        availableHospital.setText(Hospital_name);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int) snapshot.getChildrenCount();
                appointmentNo = String.valueOf(count+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(DocAppointment_2.this, "appointmentNo Error", Toast.LENGTH_SHORT).show();
            }
        });

        if (Hospital_name.equals("Asiri Medical Hospital")){
            time1.setText("Sunday 9.00AM - 11.00AM");
            time2.setText("Saturday 5.00PM - 7.00PM");

        }else{
            time1.setText("Sunday 7.00AM - 9.00AM");
            time2.setText("Saturday 1.00PM - 3.00PM");
        }

        timeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                timeButton = findViewById(timeRadio.getCheckedRadioButtonId());
                time = timeButton.getText().toString().trim();

            }
        });

        buttonOkTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timeRadio.getCheckedRadioButtonId() == -1){
                    Toast.makeText(DocAppointment_2.this, "Select day and time", Toast.LENGTH_SHORT).show();
                    return;
                }

                uploadDocAppointment(appointmentNo, Test_name, Doc_name, Hospital_name, time, fullName);
            }
        });
    }


    private void uploadDocAppointment( final String AppointmentNo, final String test_name, final String doc_name, final String availableHospital, final String time, final String fName) {

        final String key = ref.push().getKey();

        HashMap hashMap = new HashMap();
        hashMap.put("AppointmentNo", AppointmentNo);
        hashMap.put("FullName", fName);
        hashMap.put("Test_name", test_name);
        hashMap.put("Doc_name", doc_name);
        hashMap.put("Available_Hospital",availableHospital);
        hashMap.put("Date_Time",time);


        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent i = new Intent(DocAppointment_2.this, DocAppointmentInfo.class);
                i.putExtra("docKey", key);
                i.putExtra("appNo", appointmentNo);
                i.putExtra("fName", fullName);
                startActivity(i);
                Toast.makeText(DocAppointment_2.this, "Doctor Appointment Added Successfully", Toast.LENGTH_SHORT).show();
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