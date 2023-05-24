package com.example.emedicare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final RadioGroup radiogroup = (RadioGroup) findViewById(R.id.testRadioGroup);

        final Button changeButton = (Button) findViewById(R.id.enableButton);
        changeButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                changeOption(radiogroup);
            }
        });

        final Button changeButton2 = (Button) findViewById(R.id.backgroundColorButton);
        changeButton2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                changeButton2(radiogroup);
            }
        });
    }

    private void changeButton2(RadioGroup radiogroup) {
        if (radiogroup.isEnabled()) {
            radiogroup.setEnabled(false);
        } else {
            radiogroup.setEnabled(true);

        }
    }

    private void changeOption(RadioGroup radiogroup) {
        radiogroup.setBackgroundColor(Color.RED);
    }
}