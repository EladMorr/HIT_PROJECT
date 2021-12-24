package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText e_phoneNumber, e_password;
    Button b_create;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-b115e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e_phoneNumber = findViewById(R.id.login_phoneNumber);
        e_password = findViewById(R.id.login_password);
        b_create = findViewById(R.id.login_connectBTN);

        b_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = e_phoneNumber.getText().toString();
                String password = e_password.getText().toString();

                if(phoneNumber.isEmpty()){
                    Toast.makeText(LoginActivity.this, "please fill your phone number.", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "please fill your password.", Toast.LENGTH_SHORT).show();
                }
                else{
                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneNumber)){
                                String getPassword = snapshot.child(phoneNumber).child("password").getValue(String.class);
                                if(getPassword.equals(password)){
                                    Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "wrong password.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "wrong phone number.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}