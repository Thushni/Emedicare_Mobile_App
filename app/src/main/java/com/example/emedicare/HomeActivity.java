package com.example.emedicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private Button OTOLARYNGOLOGIES, ORTHOPEDIC, DENTIST, OBSTETRICIAN, full_blood, fast_blood, urea, ecg;



    public static final String EXTRA_Message1 = "com.example.data1";
    public static final String EXTRA_Message2 = "com.example.data2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void Oto_btn(View view){

        Intent intent = new Intent(this, DocAppointment.class);

        intent.putExtra(EXTRA_Message1, "OTOLARYNGOLOGIST");
        intent.putExtra(EXTRA_Message2, "Dr. Anoma Perera");
        startActivity(intent);
    }

    public void Oyo_btn(View view){

        Intent intent = new Intent(this, DocAppointment.class);

        intent.putExtra(EXTRA_Message1, "OYOPEDIC SERGEON");
        intent.putExtra(EXTRA_Message2, "Dr. Amila Pathmasiri");
        startActivity(intent);
    }

    public void dentist_btn(View view){

        Intent intent = new Intent(this, DocAppointment.class);

        intent.putExtra(EXTRA_Message1, "DENTIST");
        intent.putExtra(EXTRA_Message2, "Dr. Senarath paranavitharana");
        startActivity(intent);
    }

    public void Obs_btn(View view){

        Intent intent = new Intent(this, DocAppointment.class);

        intent.putExtra(EXTRA_Message1, "OBSTETRICIAN GYNECOLOGYST(VOG)");
        intent.putExtra(EXTRA_Message2, "Dr. Anura Disanayake");
        startActivity(intent);
    }

    public void FullBlood_btn(View view){

        Intent intent = new Intent(this, Appointment.class);

        intent.putExtra(EXTRA_Message1, "FULL BLOOD TEST");
        intent.putExtra(EXTRA_Message2, "Asiri Laboratory");
        startActivity(intent);
    }

    public void FastBlood_btn(View view){

        Intent intent = new Intent(this, Appointment.class);

        intent.putExtra(EXTRA_Message1, "FASTING BLOOD TEST");
        intent.putExtra(EXTRA_Message2, "Vasana Laboratory");
        startActivity(intent);
    }

    public void Urea_btn(View view){

        Intent intent = new Intent(this, Appointment.class);

        intent.putExtra(EXTRA_Message1, "UREA ELECTROLYTES TEST");
        intent.putExtra(EXTRA_Message2, "Lanka Laboratory");
        startActivity(intent);
    }

    public void Ecg_btn(View view){

        Intent intent = new Intent(this, Appointment.class);

        intent.putExtra(EXTRA_Message1, "ECG TEST");
        intent.putExtra(EXTRA_Message2, "Singha Laboratory");
        startActivity(intent);
    }

    //Nav Drawer

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

    public static void redirectActivity(Activity activity,Class aClass) {
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawer.closeDrawer(drawerLayout);
    }



    @Override
    protected void onStart() {
        super.onStart();
//        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mainAdapter.stopListening();
    }

}