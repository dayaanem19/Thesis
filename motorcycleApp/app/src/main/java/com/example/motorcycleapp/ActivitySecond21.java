package com.example.motorcycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySecond21 extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On Screen load
        setContentView(R.layout.activity_second_screenv21);

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityThirdV2();
            }
        });


    }

    public void openActivityThirdV2() {
        Intent intent = new Intent(this, ActivityThirdV2.class);
        startActivity(intent);

        // NOTE: To success fully connect to screen, declare class name in AndroidManifest.xml
    }
}
