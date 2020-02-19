package com.example.motorcycleapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class ActivitySecond21 extends AppCompatActivity {

    private Button nextBtn;
    private Button getPairedBtn;
    private Switch bluetoothSwitch;
    private TextView text;
    private BluetoothAdapter myBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    private ListView myListView;

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
            nextBtn.setEnabled(false);
            getPairedBtn.setEnabled(false);
            bluetoothSwitch.setEnabled(false);
            text.setText("Status: not supported");

            //pop up message
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",
                    Toast.LENGTH_LONG).show();
        } else {
            bluetoothSwitch = findViewById(R.id.bluetoothSwitch);
            bluetoothSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("SAMPLE", "ON CLICK SWITCH!");
                    searchDevices(v);

                    if (bluetoothSwitch.isChecked()) {
                        Log.d("SAMPLE", "Setting Switch to on!");

                        if (!myBluetoothAdapter.isEnabled()) {

                            // Get paired devices button
                            getPairedBtn = findViewById(R.id.getPairedBtn);
                            getPairedBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pairDevices(v);
                                }
                            });

                            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

                            Toast.makeText(getApplicationContext(),"Bluetooth turning on..." ,
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(),"Bluetooth is already on",
                                    Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Log.d("SAMPLE", "Setting Switch to off!");

                            myBluetoothAdapter.disable();
                            text.setText("Status: Disconnected");

                            Toast.makeText(getApplicationContext(),"Bluetooth turned off",
                                    Toast.LENGTH_LONG).show();
                    }
                }
            });

            myListView = findViewById(R.id.myListView);

            // create the arrayAdapter that contains the BTDevices, and set it to the ListView
            BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            myListView.setAdapter(BTArrayAdapter);
        }

    }

    public void openActivityThirdV2() {
        Intent intent = new Intent(this, ActivityThirdV2.class);
        startActivity(intent);

        // NOTE: To success fully connect to screen, declare class name in AndroidManifest.xml
    }

    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name and the MAC address of the object to the arrayAdapter
                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    public void searchDevices(View v) {
        Log.v("SEARCH DEVICES", Boolean.toString(!myBluetoothAdapter.isEnabled()));
        if (myBluetoothAdapter.isDiscovering()) {
            Log.d("SAMPLE", "INSIDE IS DISCOVERING!");
            // the button is pressed when it discovers, so cancel the discovery
            myBluetoothAdapter.cancelDiscovery();
        }
        else {
            Log.d("SAMPLE", "INSIDE ELSE!");
            BTArrayAdapter.clear();
            myBluetoothAdapter.startDiscovery();

            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    public void pairDevices(View v) {
        // get paired devices
        pairedDevices = myBluetoothAdapter.getBondedDevices();

        // put it's one to the adapter
        for(BluetoothDevice device : pairedDevices)
            BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());

        Toast.makeText(getApplicationContext(),"Show Paired Devices",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(bReceiver);
    }
}
