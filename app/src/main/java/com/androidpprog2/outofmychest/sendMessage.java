package com.androidpprog2.outofmychest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendMessage extends AppCompatActivity {

    TextInputLayout messageLayout;
    String intentUsername;
    Button btn_send_msg;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        messageLayout = (TextInputLayout)findViewById(R.id.textInputLayoutMsg);
        btn_send_msg = (Button)findViewById(R.id.sendMessageButton);

        Intent intent = getIntent();
        intentUsername = intent.getStringExtra("username");

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmsg();
            }
        });


    }

    private void getmsg(){
        String strMessage = messageLayout.getEditText().getText().toString();
        message msg = new message(strMessage,intentUsername);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        //mDatabase.child("messages").child(intentUsername).setValue(strMessage);
        mDatabase.child("messages").push().setValue(msg);

    }


}