package com.example.emedicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Objects;

public class DocAppointmentInfo extends AppCompatActivity {

    DatabaseReference DataRef;
    TextView appointmentNo, test_name, doc_name, availableHospital,fullName;
    private RadioGroup timeRadio;
    RadioButton time1,time2, timeButton;
    Button buttonEditTest, buttonOkTest, buttonDelete;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appointment_info);

        String docKey = getIntent().getStringExtra("docKey");
        String appNo = getIntent().getStringExtra("appNo");
        String fName = getIntent().getStringExtra("fName");

        appointmentNo = findViewById(R.id.appointmentNo);
        fullName = findViewById(R.id.fullName);
        test_name = findViewById(R.id.appoTitle);
        doc_name = findViewById(R.id.docTitle);
        availableHospital = findViewById(R.id.availableHospital);
        timeRadio = findViewById(R.id.radioGroupDayandTime);
        time1 = findViewById(R.id.Time1);
        time2 = findViewById(R.id.Time2);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonEditTest = findViewById(R.id.buttonEditTest);
        buttonOkTest = findViewById(R.id.buttonOkTest);

        DataRef = FirebaseDatabase.getInstance().getReference().child("docAppointment").child(docKey);

        DataRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    appointmentNo.setText(appNo);
                    fullName.setText(fName);
                    test_name.setText(snapshot.child("Test_name").getValue().toString());
                    doc_name.setText(snapshot.child("Doc_name").getValue().toString());
                    availableHospital.setText(snapshot.child("Available_Hospital").getValue().toString());

                    if (snapshot.child("Available_Hospital").getValue().toString().equals("Asiri Medical Hospital")){
                        time1.setText("Sunday 9.00AM - 11.00AM");
                        time2.setText("Saturday 5.00PM - 7.00PM");

                        if(snapshot.child("Date_Time").getValue().toString().equals("Sunday 9.00AM - 11.00AM")){
                            timeRadio.check(R.id.Time1);
                        }else{
                            timeRadio.check(R.id.Time2);
                        }

                    }else{
                        time1.setText("Sunday 7.00AM - 9.00AM");
                        time2.setText("Saturday 1.00PM - 3.00PM");

                        if(snapshot.child("Date_Time").getValue().toString().equals("Sunday 7.00AM - 9.00AM")){
                            timeRadio.check(R.id.Time1);
                        }else{
                            timeRadio.check(R.id.Time2);
                        }
                    }
                }
                else{
                    Toast.makeText(DocAppointmentInfo.this, "Error with fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRef = FirebaseDatabase.getInstance().getReference().child("docAppointment");
                DataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(docKey)) {
                            DataRef = FirebaseDatabase.getInstance().getReference().child("docAppointment").child(docKey);
                            DataRef.removeValue();

                            Toast.makeText(getApplicationContext(), "Doctor Appointment Deleted Successfully!", Toast.LENGTH_SHORT).show();
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

        timeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                timeButton = findViewById(timeRadio.getCheckedRadioButtonId());
                time = timeButton.getText().toString().trim();
            }
        });

        buttonOkTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(DocAppointmentInfo.this, HomeActivity.class);
                i.putExtra("doc_test", "doctor");
                startActivity(i);
            }
        });

        buttonEditTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if( time!=null ){
                    updateDocAppointment( time );
                }
                else{
                    Toast.makeText(DocAppointmentInfo.this, "Please Fill All Edit Text Fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateDocAppointment(final String time) {

        DataRef = FirebaseDatabase.getInstance().getReference().child("docAppointment");

        String testKey = getIntent().getStringExtra("docKey");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Date_Time", time);

        DataRef.child(testKey).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DocAppointmentInfo.this, "Doctor appointment successfully updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}