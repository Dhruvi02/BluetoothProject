package com.example.bluetoothproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT =1;

    TextView txt_statusBT,txt_paired;
    ImageView img_BT;
    Button btn_on,btn_off,btn_discoverable,btn_paired,btn_chat_window;


    BluetoothAdapter myBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_statusBT = findViewById(R.id.txt_statusBT);
        txt_paired = findViewById(R.id.txt_paired);
        btn_on = findViewById(R.id.btn_on);
        btn_off = findViewById(R.id.btn_off);
        btn_discoverable = findViewById(R.id.btn_discoverable);
        btn_paired = findViewById(R.id.btn_paired);
        img_BT = findViewById(R.id.img_BT);
        btn_chat_window = findViewById(R.id.btn_chat_window);


        //adapter
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //check if bluettoth is available or not
        if (myBluetoothAdapter == null){
            txt_statusBT.setText("BT is not availale");

        }else{
            txt_statusBT.setText("Bluetooth is available");
        }

        //set image according to BT status(on/off)
        if (myBluetoothAdapter.isEnabled()){
            img_BT.setImageResource(R.drawable.ic_action_on);
        }else{
            img_BT.setImageResource(R.drawable.ic_action_off);
        }

        //on btn click
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myBluetoothAdapter.isEnabled()){
                    showToast("Turning on Bluetooth..!!");
                    //intent to turn on Bluetooth
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(i, REQUEST_ENABLE_BT);
                }else{
                    showToast("Bluetooth is already enabled..!!");

                }
            }
        });

        //on discover btn click
        btn_discoverable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myBluetoothAdapter.isDiscovering()){
                    showToast("Making your device discoverable");
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(i,REQUEST_DISCOVER_BT);
                }
            }
        });

        //on off btn click
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBluetoothAdapter.isEnabled()){
                    myBluetoothAdapter.disable();
                    showToast("Turning bluetooth off");
                }else {
                    showToast("Bluetooth is already off");
                }

            }
        });


        //get paired devices on btn click
        btn_paired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBluetoothAdapter.isEnabled()){
                    txt_paired.setText("Paired Devices");
                    Set<BluetoothDevice> devices = myBluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device : devices){
                        txt_paired.append("\nDevice: "+ device.getName() + ","+device);
                    }
                }else{
                    //bluetooth is off, can't pair devices
                    showToast("Turn on Bluetooth to get paired devices");
                }
            }
        });


        //open chat window
        btn_chat_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ChatWindow.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    //Bluetooth is on
                    img_BT.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                }else{
                    //user denied to turn on bluetooth on
                    showToast("couldn't turn on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //toast msg fun
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}