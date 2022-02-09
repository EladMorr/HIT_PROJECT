package com.example.firebase.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.R;
import com.example.firebase.interfaces.IUserLoginCallback;
import com.example.firebase.manager.DatabaseManager;
import com.example.firebase.manager.RolesManager;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.User;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText mMailInput, mPasswordInput;
    TextView mErrorMessage;
    Button mLoginBtn;
    private ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mMailInput = findViewById(R.id.login_mail);
        mPasswordInput = findViewById(R.id.login_password);
        mLoginBtn = findViewById(R.id.login_connectBTN);
        mErrorMessage = findViewById(R.id.errorMessage);
        mLoadingBar = findViewById(R.id.loginProgressBar);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mMailInput.getText().toString();
                String password = mPasswordInput.getText().toString();
                if (TextUtils.isEmpty(mail)) {
                    mMailInput.setError("please fill your Email");
                    mMailInput.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    mPasswordInput.setError("please fill your Password");
                    mPasswordInput.requestFocus();
                } else {
                    mErrorMessage.setVisibility(View.GONE);
                    mLoadingBar.setVisibility(View.VISIBLE);
                    mLoginBtn.setEnabled(false);
                    mMailInput.setError(null);
                    mPasswordInput.setError(null);
                    UsersManager.getInstance().login(mail, password, new IUserLoginCallback() {
                        @Override
                        public void onLoginSuccess() {
                            User fbUser = UsersManager.getInstance().getCurrentUser();
                            if (fbUser != null) {
                                DatabaseManager.getInstance().getUserRoleType(fbUser.getEmail(), new RolesManager.IOnRoleResult() {
                                    @Override
                                    public void role(RolesManager.RoleType type) {
                                        fbUser.setRoleType(type);
                                        Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();
                                    }

                                    @Override
                                    public void fail() {
                                        mLoadingBar.setVisibility(View.GONE);
                                        mLoginBtn.setEnabled(true);
                                        mErrorMessage.setVisibility(View.VISIBLE);
                                        mErrorMessage.setText("Something went wrong. please try again");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onLoginFail() {
                            mLoadingBar.setVisibility(View.GONE);
                            mLoginBtn.setEnabled(true);
                            mErrorMessage.setVisibility(View.VISIBLE);
                            mErrorMessage.setText("Login failed. Mail or password don't match");
                        }
                    });
                }
            }
        });
    }
}