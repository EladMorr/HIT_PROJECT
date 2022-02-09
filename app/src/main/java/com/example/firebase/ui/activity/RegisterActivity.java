package com.example.firebase.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.R;
import com.example.firebase.interfaces.IUserLoginCallback;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.User;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText mFullName, mMail, mPassword;
    private Button mSendToFirebase;
    private FirebaseAuth mAuth;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private User mManagerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        mAuth = FirebaseAuth.getInstance();

        mManagerUser = UsersManager.getInstance().getCurrentUser();

        mFullName = findViewById(R.id.addFullName);
        mMail = findViewById(R.id.addEmail);
        mPassword = findViewById(R.id.addPassword);
        mSendToFirebase = findViewById(R.id.send_data);
        mErrorMessage = findViewById(R.id.addEmployeeErrorMessage);
        mProgressBar = findViewById(R.id.addEmployeeProgressBar);

        mSendToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = mFullName.getText().toString();
                String Email = mMail.getText().toString();
                String password = mPassword.getText().toString();
                if (fullName.isEmpty() || Email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "please fill all information above", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mErrorMessage.setVisibility(View.GONE);

                    mAuth.createUserWithEmailAndPassword(Email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser fbUesr = authResult.getUser();
                            updateUserProfile(fbUesr, fullName);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressBar.setVisibility(View.GONE);
                            mErrorMessage.setVisibility(View.VISIBLE);
                            mErrorMessage.setText(e.getMessage());
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {

                        }
                    });
                }
            }
        });
    }

    private void updateUserProfile(FirebaseUser fbUser, String fullName) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(fullName);
        fbUser.updateProfile(builder.build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                UsersManager.getInstance().login(mManagerUser.getEmail(), mManagerUser.getPassword(), new IUserLoginCallback() {
                    @Override
                    public void onLoginSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Employee create successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onLoginFail() {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressBar.setVisibility(View.GONE);
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText("Something went wrong. please try again");
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

            }
        });
    }
}