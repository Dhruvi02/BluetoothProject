package com.example.bluetoothproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatWindow extends AppCompatActivity {

    EditText edt_chat_msg;
    Button btn_send;
    ListView lst_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        edt_chat_msg = findViewById(R.id.edt_chat_msg);
        btn_send = findViewById(R.id.btn_send);
        lst_view = findViewById(R.id.lst_view);
    }
}