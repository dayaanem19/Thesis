package com.example.motorcycleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMainV2 extends AppCompatActivity {

    private Button signUpBtn;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On Screen load
        setContentView(R.layout.activity_mainv2);

        signUpBtn = findViewById(R.id.signUpbutton);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityThirdV2();
            }
        });

        forgotPassword = findViewById(R.id.textView2);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySeventh2();
            }
        });

    }

    public void openActivityThirdV2() {
        Intent intent = new Intent(this, ActivityThirdV2.class);
        startActivity(intent);

        // NOTE: To success fully connect to screen, declare class name in AndroidManifest.xml
    }

    public void openActivitySeventh2() {
        Intent intent = new Intent(this, ActivitySeventh2.class);
        startActivity(intent);

        // NOTE: To success fully connect to screen, declare class name in AndroidManifest.xml
    }

}
