package com.example.firebase.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.R;
import com.example.firebase.model.User;
import com.example.firebase.manager.UsersManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText m_fullName, m_Email, m_phoneNumber, m_password;
    Button b_sendToFirebase, b_haveAccount;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_fullName = findViewById(R.id.fullName);
        m_Email = findViewById(R.id.email);
        m_phoneNumber = findViewById(R.id.phoneNumber);
        m_password = findViewById(R.id.password);
        b_sendToFirebase = findViewById(R.id.send_data);
        b_haveAccount = findViewById(R.id.haveAccount);

        b_sendToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName = m_fullName.getText().toString();
                String Email = m_Email.getText().toString();
                String phoneNumber = m_phoneNumber.getText().toString();
                String password = m_password.getText().toString();
                if(fullName.isEmpty() || Email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "please fill all information above", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(Email,password);
                    Toast.makeText(getApplicationContext() , "User created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

            }
        });
        b_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}