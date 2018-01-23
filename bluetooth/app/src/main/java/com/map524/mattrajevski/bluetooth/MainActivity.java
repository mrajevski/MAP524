package com.map524.mattrajevski.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.logging.LogRecord;

// REFERENCES //
// https://developer.android.com/guide/topics/connectivity/bluetooth.html
//  - Bluetooth setup
// http://blog.lemberg.co.uk/how-guide-obdii-reader-app-development
//  - Connecting to OBD-II reader and tx/rx data
////

public class MainActivity extends AppCompatActivity {
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    Intent connect = new Intent("bluetooth.connect");
    Set<String[]> p, d;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                d.add(new String[] {device.getName(), device.getAddress()});
            }
        }
    };

    private void search() {
        Set<BluetoothDevice> paired = btAdapter.getBondedDevices();
        if (paired.size() > 0) {
            for (BluetoothDevice device : paired) {
                p.add(new String[] {device.getName(), device.getAddress()});
            }
        }

        startDiscovery();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                connect.putExtra("paired", p.toArray()).putExtra("discovered", d.toArray());
            }
        }, 10000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!btAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        }

        registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search();
                startActivity(connect);
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
