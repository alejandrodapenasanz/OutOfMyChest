package com.androidpprog2.outofmychest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class userLogin extends AppCompatActivity {
    TextView loginEmail,loginPasword, textRegister;
    Button btn_login;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        loginEmail = (TextView)findViewById(R.id.loginUsername_Email);
        loginPasword = (TextView)findViewById(R.id.loginPassword);
        textRegister = (TextView)findViewById(R.id.loginRegisterText);

        btn_login = (Button)findViewById(R.id.login_btn_login);

        progressBar =(ProgressBar)findViewById(R.id.loginProgressBar);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loginUser();
                progressBar.setVisibility(View.GONE);
            }
        });

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
    }

    private void openRegister(){
        Intent intent = new Intent(this, userRegister.class);
        userLogin.this.startActivity(intent);
    }

    private void loginUser(){

        String username = loginEmail.getText().toString().trim();
        String password = loginPasword.getText().toString().trim();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(username);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    loginEmail.setError(null);
                    loginPasword.setError(null);
                    String passwordFromDb = snapshot.child(username).child("password").getValue(String.class);
                    String usernameFromDb = snapshot.child(username).child("username").getValue(String.class);
                    String emailFromDb = snapshot.child(username).child("email").getValue(String.class);
                    if(passwordFromDb.equals(password)){

                        //Login
                        Intent intent = new Intent (getApplicationContext(), userProfile.class);
                        intent.putExtra("username",usernameFromDb);
                        intent.putExtra("email",emailFromDb);
                        intent.putExtra("password",passwordFromDb);


                        startActivity(intent);
                    }else{
                        //Error
                        loginPasword.setError("Wrong password!");
                        loginPasword.requestFocus();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}