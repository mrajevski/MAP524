package com.map524.mattrajevski.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView l = (TextView)findViewById(R.id.btList);

        if (!btAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        }

        final IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        final Button scButton = (Button)findViewById(R.id.scan);
        scButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                l.setText(l.getText() + "help me pls");
                registerReceiver(btReceiver, filter);
            }
        });

        final Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        final Button dcButton = (Button)findViewById(R.id.discover);
        dcButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(discoverable);
            }
        });
    }

    private final BroadcastReceiver btReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName(), deviceHardwareAddress = device.getAddress();
                TextView l = (TextView)findViewById(R.id.btList);
                l.setText("\n" + deviceName + " - " + deviceHardwareAddress);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(btReceiver);
    }

}
