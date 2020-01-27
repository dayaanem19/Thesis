package com.example.motorcycleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class thirdScreen extends AppCompatActivity {


    private EditText Name;
    private EditText Pinnumber;
    private EditText Password;
    private EditText ConfirmPassword;
    private Button SubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_screen);

        Name = (EditText) findViewById(R.id.name);
        Pinnumber = (EditText) findViewById(R.id.pinNumber);
        Password = (EditText) findViewById(R.id.password);
        ConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        SubmitButton = (Button) findViewById(R.id.submitButton);

        Name.addTextChangedListener(loginTextWatcher);
        Pinnumber.addTextChangedListener(loginTextWatcher);
        Password.addTextChangedListener(loginTextWatcher);
        ConfirmPassword.addTextChangedListener(loginTextWatcher);

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScreen1();
            }
        });
    }

    //disable if Input boxes are empty
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = Name.getText().toString().trim();
            String pinnumberInput = Pinnumber.getText().toString().trim();
            String passwordInput = Password.getText().toString().trim();
            String confirmPasswordInput = ConfirmPassword.getText().toString().trim();

            SubmitButton.setEnabled(!nameInput.isEmpty() && !pinnumberInput.isEmpty() && !passwordInput.isEmpty() && !confirmPasswordInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //open Main screen
    public void openScreen1(){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
