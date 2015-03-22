package com.example.albert.bluetoothclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

//servidor es el mobil
//client es el aparell
public class MainActivity extends ActionBarActivity {
    private final static int REQUEST_ENABLE_BT = 1;
    private EditText inputtext;
    private Button sendButton;
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectedThread sender;
    private int i = 0;
    private SeekBar mSeekBarAixeta1;
    private SeekBar mSeekBarAixeta2;
    private SeekBar mSeekBarDutxa;
    private SeekBar mSeekBarGeneral;


    private float aixeta1 = 0;
    private float aixeta2 = 0;
    private float dutxa = 0;
    private float general = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputtext = (EditText) findViewById(R.id.send_text);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("connectat", "cop:" + i);
                if (sender != null) {
                    String text = inputtext.getText().toString();
                    sender.write(text.getBytes());
                } else {
                    Log.e("connectat", "th es null");
                    show("ERROR");
                }
                i++;
            }
        });
        initBluetooth();
        mSeekBarAixeta1 = (SeekBar) findViewById(R.id.seekBarAixeta1);
        mSeekBarAixeta2 = (SeekBar) findViewById(R.id.seekBarAixeta2);
        mSeekBarDutxa = (SeekBar) findViewById(R.id.seekBarDutxa);
        mSeekBarGeneral = (SeekBar) findViewById(R.id.seekBarGeneral);


        mSeekBarAixeta1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                aixeta1 = progress / 10f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarAixeta2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                aixeta2 = progress / 10f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarDutxa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dutxa = progress / 10f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarGeneral.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                general = progress / 10f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Timer timer = new Timer();
        timer.schedule(send(), 0, 1000);


    }

    public TimerTask send() {
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (sender != null) {
                    final String text = "/"+aixeta1+","+aixeta2+","+dutxa+","+general;
                    Log.e("connectat","envio:"+text);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }
                    });

                    sender.write(text.getBytes());
                }
            }
        };
        return tt;

    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        switch (this.enable()) {
            case Constants.ENABLED:
                show("bluetooth is enabled");
                BluetoothDevice device = this.getPairedDevices();

                if (device != null) {
                    //show("TU: "+mBluetoothAdapter.getName() + "\n" + mBluetoothAdapter.getAddress());
                    ConnectThread th = new ConnectThread(device, mBluetoothAdapter);

                    th.start();

                    synchronized (this) {
                        try {
                            this.wait(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sender = th.getSenderThread();
                    }


                } else {
                    show("mobil no trobat");
                }
                /*// Register the BroadcastReceiver
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy*/


                break;

            case Constants.DISABLED:
                show("bluetooth is disabled");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                onDestroy();
                break;
            case Constants.NOT_SUPORTED:
                Toast.makeText(getApplicationContext(), "bluetooth not supported", Toast.LENGTH_LONG).show();
                onDestroy();
                break;
        }

    }


    public int enable() {

        if (mBluetoothAdapter != null) {
            // Device does support Bluetooth
            if (mBluetoothAdapter.isEnabled()) {
                // Enabled. Work with Bluetooth.
                return Constants.ENABLED;
            } else {
                // Disabled.

                return Constants.DISABLED;

            }
        } else {
            return Constants.NOT_SUPORTED;
        }
    }

    public BluetoothDevice getPairedDevices() {
        //ArrayList<String> mArrayAdapter = null;
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
// If there are paired devices
        if (pairedDevices.size() > 0) {
            //mArrayAdapter =  new ArrayList<>(pairedDevices.size());
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                //  mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                show(device.getName() + "\n" + device.getAddress());
                //show(device.getAddress());

                if (device.getAddress().equals(Constants.MAC_MOBIL_SERVIDOR_FOLCH)) {
                    show("mobil "+device.getName()+" trobat");
                    return device;
                }

            }
        }
        return null;
    }

/*
    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            final ArrayList<String> list = new ArrayList<String>();


            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                list.add(device.getName() + "\n" + device.getAddress());
            }
            final ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);
        }
    };*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(mReceiver);
    }

    private void show(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
