package com.example.emedicare;

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

public class DocAppointment extends AppCompatActivity {


    DrawerLayout drawerLayout;

    TextView Test_Name, Doc_Name;
    String hospital;


    private RadioGroup radioGroupHospital;
    private RadioButton hosButton;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appointment);


        drawerLayout = findViewById(R.id.drawer_layout);

        Intent i = getIntent();

        String test_name = i.getStringExtra(HomeActivity.EXTRA_Message1);
        String doc_name = i.getStringExtra(HomeActivity.EXTRA_Message2);

        Test_Name = findViewById(R.id.appoTitle);
        Doc_Name = findViewById(R.id.docTitle);

        Test_Name.setText(test_name);
        Doc_Name.setText(doc_name);

        radioGroupHospital = (RadioGroup) findViewById(R.id.radioGroupHospital);

        radioGroupHospital.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                hosButton = findViewById(radioGroupHospital.getCheckedRadioButtonId());
                hospital = hosButton.getText().toString().trim();
            }
        });

        btnSubmit = findViewById(R.id.buttonOkDoc);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DocAppointment.this, DocAppointment_2.class);
                i.putExtra("Test_name", test_name);
                i.putExtra("Doc_name", doc_name);
                if(radioGroupHospital.getCheckedRadioButtonId() == -1){
                    Toast.makeText(DocAppointment.this, "Select a Hospital", Toast.LENGTH_SHORT).show();
                    return;
                }

                i.putExtra("Hospital_name", hospital);
                startActivity(i);
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