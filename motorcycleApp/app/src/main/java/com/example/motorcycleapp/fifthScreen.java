package com.example.motorcycleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class fifthScreen extends AppCompatActivity {

    private Button changeKey_button;
    private Button reset_button;
    private Button logout_button;
    private TextView keycode;
    private int keycode_counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_screen);

        changeKey_button = (Button) findViewById(R.id.changeButton);
        reset_button = (Button) findViewById(R.id.resetButton);
        logout_button = (Button) findViewById(R.id.logoutButton);
        keycode = (TextView) findViewById(R.id.keycode);

        keycode.setText("       KEY CODE 1");


        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScreen1();
            }
        });

        changeKey_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keycode_counter ++;

                keycode.setText("       KEY CODE " + String.valueOf(keycode_counter));
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keycode_counter = 1;

                keycode.setText("       KEY CODE " + String.valueOf(keycode_counter));
            }
        });

    }

    //Logout to Main Screen
    public void openScreen1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
