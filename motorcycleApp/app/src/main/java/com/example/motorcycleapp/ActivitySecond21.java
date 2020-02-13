package com.example.motorcycleapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySecond21 extends AppCompatActivity {

    private Button nextBtn;
    private Switch bluetoothSwitch;
    private TextView text;
    private BluetoothAdapter myBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;

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

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(myBluetoothAdapter == null) {
            bluetoothSwitch.setChecked(false);
            text.setText("Status: not supported");

            //pop up message
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",
                    Toast.LENGTH_LONG).show();
        } else {
            bluetoothSwitch = findViewById(R.id.bluetoothSwitch);
            bluetoothSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bluetoothSwitch.isChecked()) {
                        Log.d("SAMPLE", "Setting Switch to on!");

                        if (!myBluetoothAdapter.isEnabled()) {
                            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

                            Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Bluetooth is already on",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("SAMPLE", "Setting Switch to off!");
                    }
                }
            });
        }

    }

    public void openActivityThirdV2() {
        Intent intent = new Intent(this, ActivityThirdV2.class);
        startActivity(intent);

        // NOTE: To success fully connect to screen, declare class name in AndroidManifest.xml
    }
}
