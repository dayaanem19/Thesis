package com.example.motorcycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityThird21 extends AppCompatActivity {
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On Screen load
        setContentView(R.layout.activity_third_screenv21);

        submitButton = findViewById(R.id.resetBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMainV2();
            }
        });

    }

    public void openActivityMainV2() {
        Intent intent = new Intent(this, ActivityMainV2.class);
        startActivity(intent);
    }
}
