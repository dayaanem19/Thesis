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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class ActivitySecond21 extends AppCompatActivity {

    private Button searchBtn;
    private Button nextBtn;
    private ImageView backBtn;
    private Switch bluetoothSwitch;
    private BluetoothAdapter myBluetoothAdapter;
    private ListView myListView;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
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

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMainV2();
            }
        });

        myListView = findViewById(R.id.myListView);

        // create the arrayAdapter that contains the BTDevices, and set it to the ListView
        BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        myListView.setAdapter(BTArrayAdapter);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(myBluetoothAdapter == null) {
            nextBtn.setEnabled(false);
            searchBtn.setEnabled(false);

            //pop up message
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",
                    Toast.LENGTH_LONG).show();
        } else {

            bluetoothSwitch = findViewById(R.id.bluetoothSwitch);

            if (myBluetoothAdapter.isEnabled()) {
                bluetoothSwitch.setChecked(true);
            }

            Log.d("SAMPLE", String.valueOf(bluetoothSwitch.isChecked()));

            // If switch is already turned on or bluetooth is already enabled
            if (bluetoothSwitch.isChecked()) {
                // Search devices button
                searchBtn = findViewById(R.id.searchBtn);
                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchDevices(v);
                    }
                });

                // On discovered item click
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        pairedDevices = myBluetoothAdapter.getBondedDevices();
                        final ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
                        bluetoothDevices.addAll(bluetoothDevices);
                        Log.v("SAMPLE", "Item selected: " + selectedItem);
                        if (bluetoothDevices.size() > 0) {
                            BluetoothDevice device = bluetoothDevices.get(position);
//                        if (device.getBondState()!=BluetoothDevice.BOND_BONDED){
                            pairDevice(device);


//                                pairDevices(view);
                        }
                        else {
                            Toast.makeText(ActivitySecond21.this, "unable to connect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            // If Bluetooth is already on.
            if (myBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(),"Bluetooth is already on",
                        Toast.LENGTH_LONG).show();
            }

            // On switch click
            bluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("SAMPLE", "ON CLICK SWITCH!");

                    // If switch is turned on
                    if (isChecked) {

                        Log.d("SAMPLE", "Setting Switch to on!");
                        on(buttonView);

                        // Search devices button
                        searchBtn = findViewById(R.id.searchBtn);
                        searchBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searchDevices(v);
//                                pairDevices(v);
                            }
                        });

                        // On discovered item click
                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedItem = (String) parent.getItemAtPosition(position);
                                Log.v("SAMPLE", "Item selected: " + selectedItem);
//                                pairDevices(view);
                            }
                        });

                    } else {
                        Log.d("SAMPLE", "Setting Switch to off!");
                        off(buttonView);
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

    public void openActivityMainV2() {
       Intent intent = new Intent(this, ActivityMainV2.class);
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
        Log.d("SAMPLE", "Inside search devices!");
        Log.d("SAMPLE", String.valueOf(myBluetoothAdapter.isDiscovering()));

//        if (myBluetoothAdapter.isDiscovering()) {
//            // the button is pressed when it discovers, so cancel the discovery
//            myBluetoothAdapter.cancelDiscovery();
//        }
//        else {
            BTArrayAdapter.clear();
            myBluetoothAdapter.startDiscovery();

            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
//        }
    }

    public void on(View view){
        if (!myBluetoothAdapter.isEnabled()) {
            myBluetoothAdapter.enable();
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

            Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already on",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void off(View view){
        myBluetoothAdapter.disable();

        Toast.makeText(getApplicationContext(),"Bluetooth turned off",
                Toast.LENGTH_LONG).show();
    }
    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
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
