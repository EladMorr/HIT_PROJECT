package com.example.firebase.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.R;
import com.example.firebase.interfaces.IUserLoginCallback;
import com.example.firebase.manager.DatabaseManager;
import com.example.firebase.manager.UsersManager;

public class LoginActivity extends AppCompatActivity {

    EditText mMailInput, mPasswordInput;
    Button mLoginBtn;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mMailInput = findViewById(R.id.login_mail);
        mPasswordInput = findViewById(R.id.login_password);
        mLoginBtn = findViewById(R.id.login_connectBTN);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = mMailInput.getText().toString();
                String password = mPasswordInput.getText().toString();

                if (Email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "please fill your Email.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "please fill your password.", Toast.LENGTH_SHORT).show();
                } else {
                    UsersManager.getInstance().login(Email, password, new IUserLoginCallback() {
                        @Override
                        public void onLoginSuccess() {
                            mLoadingBar = new ProgressDialog(getApplicationContext());
                            Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }

                        @Override
                        public void onLoginFail() {
                            Toast.makeText(LoginActivity.this, "login Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}