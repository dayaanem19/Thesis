package com.example.motorcycleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class fourthScreen extends AppCompatActivity {


    private Button Enter_Button;
    private EditText Pinnumber;
    private TextView Pin_Attempts;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_screen);

       Enter_Button = (Button) findViewById(R.id.enterButton);
       Pinnumber = (EditText) findViewById(R.id.pinNumber);
       Pin_Attempts = (TextView) findViewById(R.id.textView6);

       Pin_Attempts.setText("No of attempts remaining: 3");

       Enter_Button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openScreen5(Pinnumber.getText().toString());
           }
       });
    }

    //open 5th screen
    public void openScreen5(String pinNumber){
        if(pinNumber.equalsIgnoreCase("1234")){
            Intent intent = new Intent(this, fifthScreen.class);
            startActivity(intent);
        }
        else{
            counter --; //decrement if pin number is incorrect

            Pin_Attempts.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter==0){ //login button will be disabled if all attempts are used
                Enter_Button.setEnabled(false);
            }
        }
    }
}
