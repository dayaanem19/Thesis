package com.example.motorcycleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button button_login;
    private Button button_signup;
    private CheckBox ShowPassword;
    private TextView Attempts;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login = (Button) findViewById(R.id.loginButton);
        Name = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.password);
        Attempts = (TextView) findViewById(R.id.textView2);
        ShowPassword = (CheckBox)findViewById(R.id.checkPass);
        button_signup = (Button)findViewById(R.id.signUpbutton);


        Name.addTextChangedListener(loginTextWatcher);
        Password.addTextChangedListener(loginTextWatcher);

        Attempts.setText("No of attempts remaining: 5");

        ShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScreen4(Name.getText().toString(),Password.getText().toString());
            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScreen2();

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
            String passwordInput = Password.getText().toString().trim();

            button_login.setEnabled(!nameInput.isEmpty() && !passwordInput.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //open 2nd Screen
    public void openScreen2(){

        Intent intent = new Intent(this, secondScreen.class);
        startActivity(intent);

    }

    //open 4th Screen
    public void openScreen4(String userName, String userPassword) {

        if(userName.equalsIgnoreCase("nikka") && userPassword.equalsIgnoreCase("1234")){
            Intent intent = new Intent(this, fourthScreen.class);
            startActivity(intent);
        }
        else{
            counter --;

            Attempts.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter ==0){
                button_login.setEnabled(false);
            }
        }

    }
}