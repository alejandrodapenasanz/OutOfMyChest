package com.androidpprog2.outofmychest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_register,btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register = (Button) findViewById(R.id.main_btn_Register);
        btn_login = (Button)findViewById(R.id.main_btb_Login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    private void openRegister(){
        Intent intent = new Intent(this, userRegister.class);
        MainActivity.this.startActivity(intent);
    }

    private void openLogin(){
        Intent intent = new Intent(this, userLogin.class);
        MainActivity.this.startActivity(intent);
    }
}