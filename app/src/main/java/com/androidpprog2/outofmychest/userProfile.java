package com.androidpprog2.outofmychest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class userProfile extends AppCompatActivity {
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = (TextView)findViewById(R.id.userprofileUsername);

        showUserData();
    }

    private void showUserData(){
        Intent intent = getIntent();
        String intentUsername = intent.getStringExtra("username");
        username.setText(intentUsername);
    }
}