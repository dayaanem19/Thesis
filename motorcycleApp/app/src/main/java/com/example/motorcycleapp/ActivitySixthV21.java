package com.example.motorcycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySixthV21 extends AppCompatActivity {
    private Button submitButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On Screen load
        setContentView(R.layout.activity_sixth_screenv21);

        submitButton = findViewById(R.id.resetBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityFourthV2();
            }
        });

        textView = findViewById(R.id.textView20);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMainV2();
            }
        });

    }

    public void openActivityFourthV2() {
        Intent intent = new Intent(this, ActivityFourthV2.class);
        startActivity(intent);
    }

    public void openActivityMainV2() {
        Intent intent = new Intent(this, ActivityMainV2.class);
        startActivity(intent);
    }
}
