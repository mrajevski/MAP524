package com.map524.mattrajevski.shiftoptimizer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.enums.*;
import com.github.pires.obd.commands.control.*;
import com.github.pires.obd.commands.engine.*;
import com.github.pires.obd.commands.fuel.*;
import com.github.pires.obd.commands.pressure.*;
import com.github.pires.obd.commands.protocol.*;
import com.github.pires.obd.commands.temperature.*;
import com.github.pires.obd.exceptions.*;
import com.github.pires.obd.utils.*;

public class MainActivity extends AppCompatActivity {

    int REQUEST_ENABLE_BT = 1;
    boolean connected;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    ArrayList<String> devList;
    BluetoothDevice btDevice;
    BluetoothSocket socket;
    ProgressDialog progress;

    short GEAR = 0;
    int lastSpeed, currentSpeed, currentRPM;
    float currentThrottle, currentAccl;
    TextView RPM, speed, throttle, temp, accl;
    ImageView status;
    Button connect;
    RPMCommand engineRpmCommand;
    SpeedCommand speedCommand;
    ThrottlePositionCommand throttleCommand;
    EngineCoolantTemperatureCommand coolantCommand;
    MediaPlayer up = null, down = null, more = null;


    public void init() {
        devList = new ArrayList<>();
        btDevice = null;
        socket = null;
        connected = false;
        RPM = (TextView)findViewById(R.id.RPM);
        speed = (TextView)findViewById(R.id.speed);
        throttle = (TextView)findViewById(R.id.throttle);
        temp = (TextView)findViewById(R.id.temp);
        accl = (TextView)findViewById(R.id.acceleration);
        connect = (Button)findViewById(R.id.connect);
        up = MediaPlayer.create(this, R.raw.up_sound);
        down = MediaPlayer.create(this, R.raw.down_sound);
        more = MediaPlayer.create(this, R.raw.up_sound);
    }

    public void getDevices() {
        Set pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (int i = 0; i < pairedDevices.size(); i++) {
                BluetoothDevice dev = (BluetoothDevice) pairedDevices.toArray()[i];
                devList.add(dev.getName() + "//" + dev.getAddress());
            }
        }
    }

    public void selectDevice() {
        if (devList.size() > 0) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_singlechoice,
                    devList.toArray(new String[devList.size()]));

            alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    btDevice = btAdapter.getRemoteDevice(devList.get(position).substring(devList.get(position).length() - 17));
                    new connectBT().execute();
                }
            });

            alertDialog.setTitle("Choose Bluetooth device");
            alertDialog.show();
        }
        else {
            Toast.makeText(this, "No paired devices", Toast.LENGTH_SHORT).show();
        }
    }

    private class connectBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(MainActivity.this, "Connecting to " + btDevice.getAddress(), "progress:");

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = btDevice.createInsecureRfcommSocketToServiceRecord(uuid);
                socket.connect();
                connected = true;
                try {
                    new EchoOffCommand().run(socket.getInputStream(), socket.getOutputStream());
                    new LineFeedOffCommand().run(socket.getInputStream(), socket.getOutputStream());
                    //new TimeoutCommand(10).run(socket.getInputStream(), socket.getOutputStream());
                    new SelectProtocolCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
                }
                catch (IOException | InterruptedException e) {
                    Log.i("OBD INIT COMMAND: ", e.toString());
                }

            }
            catch (IOException e) {
                Log.i("BLUETOOTH CONNECT:", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }

    public void disconnect() {
        try {
            socket.close();
            connected = false;
            connect.setText("Connect");
        }
        catch (IOException e) {
            Log.i("BLUETOOTH DISCONNECT:", e.toString());
        }
    }

    public void onClickHandler(int id) {
        switch (id) {
            case R.id.connect:
                if (connected) {
                    disconnect();
                }
                else {
                    selectDevice();
                }
                break;
            case R.id.help:
                startActivity(new Intent(this, help.class));
                break;
            default:
                Log.i("UNHANDLED CLICK: ", connect.toString());
                break;
        }
    }
    public short shiftStatus() {
        if ((currentAccl < 0.05 || currentRPM > 3000) && currentThrottle > 0.9) {
            return 1;
        }
        if ((currentAccl < 0.00 || currentRPM < 1600) && currentThrottle < 0.2) {
            return 2;
        }
        if (currentAccl < 0.00 && currentThrottle > 0.8) {
            return 3;
        }
        return 0;
    }

    public void stop() {
        if (up.isPlaying()) {
            up.pause();
            //upSound.reset();
        }

        if (down.isPlaying()) {
            down.pause();
            //downSound.reset();
        }

        if (more.isPlaying()) {
            more.pause();
            //moreSound.reset();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if (btAdapter != null) {
            getDevices();
            findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickHandler(R.id.connect);
                }
            });

            if (!btAdapter.isEnabled())
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);

            final Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    handler.postDelayed(this, 1000);

                    engineRpmCommand = new RPMCommand();
                    speedCommand = new SpeedCommand();
                    throttleCommand = new ThrottlePositionCommand();
                    coolantCommand = new EngineCoolantTemperatureCommand();
                    //fuelCommand = new ConsumptionRateCommand();

                    if (connected) {
                        try {
                            engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                            speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                            throttleCommand.run(socket.getInputStream(), socket.getOutputStream());
                            coolantCommand.run(socket.getInputStream(), socket.getOutputStream());
                        } catch (IOException | InterruptedException e) {
                            Log.i("OBD COMMAND: ", e.toString());
                        }

                        connect.setText("" + "Disconnect from\n" + btDevice.getName());
                        RPM.setText(engineRpmCommand.getFormattedResult());
                        currentRPM = engineRpmCommand.getRPM();
                        speed.setText(speedCommand.getFormattedResult());
                        currentSpeed = speedCommand.getMetricSpeed();
                        throttle.setText(throttleCommand.getFormattedResult());
                        currentThrottle = throttleCommand.getPercentage();
                        if (currentSpeed > 0) {
                            currentAccl = ((currentSpeed - lastSpeed)*1000)/3600;
                        }
                        else {
                            currentAccl = 0;
                        }
                        accl.setText("(" + Float.toString(currentAccl) + "m/s)/s");
                        /*switch (shiftStatus()) {
                            case 0:
                                stop();
                                break;
                            case 1:
                                status.setImageResource(R.color.shiftUp);
                                up.start();
                                break;
                            case 2:
                                status.setImageResource(R.color.shiftDown);
                                down.start();
                                break;
                            case 3:
                                status.setImageResource(R.color.moreThrottle);
                                more.start();
                                break;

                        }*/
                        temp.setText(coolantCommand.getFormattedResult());
                        lastSpeed = currentSpeed;
                    }
                }

            };
            handler.postDelayed(r, 0000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                onClickHandler(R.id.help);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
