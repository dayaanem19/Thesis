package com.example.motorcycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySecondV2 extends AppCompatActivity {

    private Button verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On Screen load
        setContentView(R.layout.activity_second_screenv2);

        verifyBtn = findViewById(R.id.verifyBtn);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySecond21();
            }
        });
    }

    public void openActivitySecond21() {
        Intent intent = new Intent(this, ActivitySecond21.class);
        startActivity(intent);

        // NOTE: To success fully connect to screen, declare class name in AndroidManifest.xml
    }
}
