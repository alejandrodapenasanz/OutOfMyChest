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
        canRegister(user);


    }
    private void canRegister(User user){
        int result = 0;
        String username = user.getUsername();
        String email = user.getEmail();
        DatabaseReference referenceDB = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = referenceDB.orderByChild("username").equalTo(username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String usernameFromDb = snapshot.child(username).child("username").getValue(String.class);
                    if(username.equals(usernameFromDb)){
                        registerUsername.setError("Username already exists");
                    }
                }else{//Username is not used
                    Query checkEmail = referenceDB.orderByChild("email").equalTo(email);
                    checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("users");
                            if(snapshot.exists()){
                                String emailFromDb = snapshot.child(email).child("email").getValue(String.class);
                                if(email.equals(emailFromDb)){
                                    registerEmail.setError("E-mail is already used!");
                                }
                            }else{
                                reference.child(username).setValue(user);
                                Intent intent = new Intent(getApplicationContext(), userLogin.class);
                                userRegister.this.startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}