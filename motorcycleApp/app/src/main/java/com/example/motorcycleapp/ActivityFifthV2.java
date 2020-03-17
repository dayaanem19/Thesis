package com.example.motorcycleapp;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityFifthV2 extends AppCompatActivity {

    private LottieAnimationView animation_view;
    private Button changeKeyBtn;
    private TextView description;
    private Button ResetBtn;
    private TextView logoutBtn;

    private final static String TAG = "SLAUGHTER";
    int i,v,f,h;
    String Alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz" + "0123456789";

    int flag =0;
    int atmtc =0;

    volatile boolean isResume=true;
    volatile boolean isResume2=true;
    private SharedPreferences database;

    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceName="IMB0001";
    private String mDeviceAddress="90:E2:02:04:84:7C";
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothGattCharacteristic characteristicTX;
    private BluetoothGattCharacteristic characteristicRX;

    @Override
    public void onBackPressed(){
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_screenv2);

        logoutBtn = findViewById(R.id.logOutButton);
        changeKeyBtn=findViewById(R.id.changeKeyBtn);
        description=findViewById(R.id.Desc);
        ResetBtn=findViewById(R.id.ResetBtn);
        animation_view=findViewById(R.id.animation_view);

        new Thread(new Task()).start();

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        description.setEnabled(false);
        database = getSharedPreferences("UserInfo", MODE_PRIVATE);
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothLeService.close();
                openActivityMainV2();
            }
        });

        changeKeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder keycode = new StringBuilder(8);

                for (i = 0; i <8; i++) {
                    int index = (int)(Alphanumeric.length() * Math.random());
                    keycode.append(Alphanumeric.charAt(index));
                }

                System.out.println(keycode);
                String message = keycode.toString();

                Log.d(TAG, "Sending: " + message);
                animation_view.setAnimation("manual_check.json");
                animation_view.playAnimation();
                final byte[] tx = message.getBytes();
                if (mConnected) {
                    characteristicTX.setValue(tx);
                    mBluetoothLeService.writeCharacteristic(characteristicTX);
                    animation_view.setAnimation("manual_check.json");
                    animation_view.playAnimation();
                    description.setVisibility(View.VISIBLE);
                    description.setText("You have manually changed a keycode");
                }

                changeKeyBtn.setEnabled(false);
                ResetBtn.setEnabled(false);
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                changeKeyBtn.setEnabled(true);
                                ResetBtn.setEnabled(true);
                            }
                        });
                    }
                }, 60000);

            }
        });

        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "keyc1234";

                Log.d(TAG, "Sending: " + message);
                final byte[] tx = message.getBytes();
                if (mConnected) {
                    characteristicTX.setValue(tx);
                    mBluetoothLeService.writeCharacteristic(characteristicTX);
                    animation_view.setAnimation("reset_check.json");
                    animation_view.playAnimation();
                    description.setEnabled(true);
                    description.setVisibility(View.VISIBLE);
                    description.setText("You have manually reset a keycode");
                }

            }
        });

    }

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName,
                                       IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
                    .getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up
            // initialization.
            mDeviceName = "IMB0001";
            mDeviceAddress = "90:E2:02:04:84:7C";
            mBluetoothLeService.connect(mDeviceAddress);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device. This can be a
    // result of read
    // or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                //      updateConnectionState(R.string.connected);
                invalidateOptionsMenu();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
                    .equals(action)) {
                mConnected = false;
//                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                // clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
                    .equals(action)) {

                setupSerial();

            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                if(isResume == true) {
                    receiveData(intent
                            .getStringExtra(BluetoothLeService.EXTRA_DATA));
                }


            }
        }
    };

    // If a given GATT characteristic is selected, check for supported features.
    // This sample
    // demonstrates 'Read' and 'Notify' features. See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for
    // the complete
    // list of supported characteristic features.
    private final ExpandableListView.OnChildClickListener servicesListClickListner = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {
            if (mGattCharacteristics != null) {
                final BluetoothGattCharacteristic characteristic = mGattCharacteristics
                        .get(groupPosition).get(childPosition);
                final int charaProp = characteristic.getProperties();
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    // If there is an active notification on a characteristic,
                    // clear
                    // it first so it doesn't update the data field on the user
                    // interface.
                    if (mNotifyCharacteristic != null) {
                        mBluetoothLeService.setCharacteristicNotification(
                                mNotifyCharacteristic, false);
                        mNotifyCharacteristic = null;
                    }
                    mBluetoothLeService.readCharacteristic(characteristic);
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    mNotifyCharacteristic = characteristic;
                    mBluetoothLeService.setCharacteristicNotification(
                            characteristic, true);
                }

                return true;
            }
            return false;
        }
    };


    private void setupSerial() {

        // blechat - set serial characteristics
        String uuid;
        String unknownServiceString = getResources().getString(
                R.string.unknown_service);

        for (BluetoothGattService gattService : mBluetoothLeService
                .getSupportedGattServices()) {


            uuid = gattService.getUuid().toString();
            System.out.println("UUID:"+ uuid);
            // If the service exists for HM 10 Serial, say so.
            if (SampleGattAttributes.lookup(uuid, unknownServiceString) == "HM 10 Serial") {

                // get characteristic when UUID matches RX/TX UUID
                characteristicTX = gattService
                        .getCharacteristic(BluetoothLeService.UUID_HM_RX_TX);
                characteristicRX = gattService
                        .getCharacteristic(BluetoothLeService.UUID_HM_RX_TX);

                mBluetoothLeService.setCharacteristicNotification(
                        characteristicRX, true);
                break;
            } // if
        } // for
    }

    private void receiveData(String data) {

        System.out.println("DATA RECEIVED:" + data);

        try {
            v = Integer.parseInt(String.valueOf(data.charAt(0)));
            f = Integer.parseInt(String.valueOf(data.charAt(1)));
            h = Integer.parseInt(String.valueOf(data.charAt(2)));
        } catch (NumberFormatException nfe) {
            mBluetoothLeService.close();
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            for (BluetoothDevice bt : pairedDevices) {
                if (bt.getName().contains("IMB0001")) {
                    try {
                        Method method = bt.getClass().getMethod("removeBond", (Class[]) null);
                        method.invoke(bt, (Object[]) null);

                        SharedPreferences.Editor editor = database.edit();
                        editor.clear();
                        editor.apply();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            openActivityMainV2();
        }

        System.out.println("V:" + v);
        System.out.println("F:" + f);
        System.out.println("H:" + h);

        if (h ==1) {
            mBluetoothLeService.close();
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            for (BluetoothDevice bt : pairedDevices) {
                if (bt.getName().contains("IMB0001")) {
                    try {
                        Method method = bt.getClass().getMethod("removeBond", (Class[]) null);
                        method.invoke(bt, (Object[]) null);

                        SharedPreferences.Editor editor = database.edit();
                        editor.clear();
                        editor.apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            openActivityMainV2();
        }
        if(v== 1 && f == 1){
            flag = 1;
            atmtc = 1;
        }

        if(v == 0 && f == 1) {
            flag =0;
            if(flag==0 && atmtc ==1) {

                //generate random keycode
                StringBuilder keycode = new StringBuilder(8);

                for (i = 0; i <8; i++) {
                    int index = (int)(Alphanumeric.length() * Math.random());
                    keycode.append(Alphanumeric.charAt(index));
                }

                System.out.println(keycode);
                String message = keycode.toString();

                Log.d(TAG, "Sending: " + message);
                final byte[] tx = message.getBytes();
                if (mConnected) {
                    characteristicTX.setValue(tx);
                    mBluetoothLeService.writeCharacteristic(characteristicTX);
                }
                // lottieAnimationView.playAnimation();
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        //  detailsTextView.setText("You have automatically changed a keycode");
                    }
                });
                flag = 0;
                atmtc = 0;
            }
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter
                .addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    class Task implements Runnable {

        @Override
        public void run() {
            BluetoothAdapter myBluetooth;
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            final SharedPreferences database;
            database = getSharedPreferences("UserInfo", MODE_PRIVATE);

            while (isResume2 == true) {

                if (!myBluetooth.isEnabled()) {

                    SharedPreferences.Editor editor = database.edit();
                    editor.putString("bluetoothStat","off");
                    editor.apply();

                    openActivityMainV2();

                } else {

                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                    for (BluetoothDevice bt : pairedDevices) {
                        if (bt.getName().contains("IMB0001")) {
                            flag=1;
                        }
                    }

                    if(flag==0) {

                        SharedPreferences.Editor editor = database.edit();
                        editor.clear();
                        editor.apply();

                        editor.putString("bluetoothImmoNotPaired","off");
                        editor.apply();
                        openActivityMainV2();
                    }
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isResume=false;
        new Thread(new Task()).start();
        isResume2=true;
        openScreen4();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("NAA KOS ON RESUME");
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isResume2=false;
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    public void openActivityMainV2() {
        isResume2=false;
        Intent intent = new Intent(this, ActivityMainV2.class);
        startActivity(intent);
    }

    public void openScreen4(){
        isResume2=false;
        Intent intent = new Intent(this, ActivityFourthV2.class);
        startActivity(intent);
    }

}
