package com.androidpprog2.outofmychest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userRegister extends AppCompatActivity {
    TextView registerEmail, registerPassword, registerUsername;
    Button btn_register;
    ProgressBar progressBar;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        registerEmail = (TextView)findViewById(R.id.registerEmail);
        registerPassword = (TextView)findViewById(R.id.registerPassword);
        registerUsername = (TextView)findViewById(R.id.registerUsername);

        btn_register = (Button)findViewById(R.id.register_btn_Register);

        progressBar = (ProgressBar)findViewById(R.id.registerProgressBar);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(validateUser(registerUsername)){
                    registerUser();
                }
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    private boolean validateUser(TextView username){
        String user = username.getText().toString();
        if(user.isEmpty() || user.contains("[")|| user.contains("]")|| user.contains(",")|| user.contains(".")|| user.contains("$")){
            registerUsername.setError("Username can not contain [ ] , . $");
            return false;
        }
        return true;
    }
    private void registerUser(){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        //Get values from view
        String email = registerEmail.getText().toString();
        String username = registerUsername.getText().toString();
        String password = registerPassword.getText().toString();

        //Verify the parameters
        if(email.isEmpty()){
            registerEmail.setError("E-mail is required!");
            registerEmail.requestFocus();
            return;
        }
        if(username.isEmpty()){
            registerUsername.setError("Username is required!");
            registerUsername.requestFocus();
            return;
        }
        if(password.isEmpty()){
            registerUsername.setError("password is required!");
            registerUsername.requestFocus();
            return;
        }
        //Create the object we are going to save
        User user = new User(email,username,password);
        //Save the user
        reference.child(username).setValue(user);
        //Redirect to login page
        Intent intent = new Intent(this, userLogin.class);
        userRegister.this.startActivity(intent);
    }
}