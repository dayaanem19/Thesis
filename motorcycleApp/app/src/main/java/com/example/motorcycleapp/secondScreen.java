package com.example.motorcycleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class secondScreen extends AppCompatActivity {

    private EditText Serial_ID;
    private Button Next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        Serial_ID = (EditText) findViewById(R.id.serialid);
        Next = (Button) findViewById(R.id.nextButton);


        Next.setEnabled(false); // set button disable initially
        //disable button if input boxes are empty
        Serial_ID.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (s.toString().equals("")) {
                    Next.setEnabled(false);
                } else {
                    Next.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });



        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScreen3(Serial_ID.getText().toString());
            }
        });



    }

    //open 3rd Screen
    public void openScreen3(String serial){

        Intent intent = new Intent(this, thirdScreen.class);
        startActivity(intent);



    }
}
