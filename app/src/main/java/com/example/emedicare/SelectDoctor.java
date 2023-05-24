package com.example.emedicare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }

    public void ClickDoc1(View view){
        redirectActivity(this, Profile.class);
    }

    public void ClickDoc2(View view){
        redirectActivity(this, Profile.class);
    }

    public void ClickDoc3(View view){
        redirectActivity(this, Profile.class);
    }


}