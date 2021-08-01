package com.androidpprog2.outofmychest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class userProfile extends AppCompatActivity {
    TextView username;
    String intentUsername;

    Button btn_send;
    Button btn_request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = (TextView)findViewById(R.id.userprofileUsername);

        btn_request = (Button)findViewById(R.id.userProfileRequest);
        btn_send = (Button)findViewById(R.id.userProfileSend);


        showUserData();

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestmessage();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendmessage();
            }
        });
    }

    private void showUserData(){
        Intent intent = getIntent();
        intentUsername = intent.getStringExtra("username");
        username.setText(intentUsername);
    }

    private void  openRequestmessage(){
        Intent intent = new Intent(this, requestMessage.class);
        userProfile.this.startActivity(intent);
    }

    private void  openSendmessage(){

        Intent intent = new Intent(this, sendMessage.class);
        intent.putExtra("username",intentUsername);

        userProfile.this.startActivity(intent);
    }
}