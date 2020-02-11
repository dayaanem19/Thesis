package com.example.motorcycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySeventh2 extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On Screen load
        setContentView(R.layout.activity_seventh_screenv2);

        nextBtn = findViewById(R.id.button4);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySeventh21();
            }
        });
    }

    public void openActivitySeventh21() {
        Intent intent = new Intent(this, ActivitySeventh21.class);
        startActivity(intent);
    }
}
