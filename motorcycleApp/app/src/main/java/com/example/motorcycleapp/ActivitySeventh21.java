package com.example.motorcycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySeventh21 extends AppCompatActivity {
    private TextView signIn;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On Screen load
        setContentView(R.layout.activity_seventh_screenv21);

        signIn = findViewById(R.id.textView20);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMainV2();
            }
        });

        submitBtn = findViewById(R.id.resetBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
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
