package com.androidpprog2.outofmychest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class userRegister extends AppCompatActivity {
    TextView registerEmail, registerPassword, registerUsername;
    Button btn_register;

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

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                //Get values from view
                String email = registerEmail.getText().toString();
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();

                User user = new User(email,username,password);

                reference.child(email).setValue(user);
            }
        });

    }
}