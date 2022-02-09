package com.example.firebase.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.firebase.R;
import com.example.firebase.manager.DatabaseManager;
import com.example.firebase.manager.RolesManager;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            DatabaseManager.getInstance().getUserRoleType(fbUser.getEmail(), new RolesManager.IOnRoleResult() {
                @Override
                public void role(RolesManager.RoleType type) {
                    User user = new User(fbUser);
                    user.setRoleType(type);
                    UsersManager.getInstance().setCurrentUser(user);
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    finish();
                }

                @Override
                public void fail() {
                    Toast.makeText(SplashScreenActivity.this, "Failed to auto login.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            });
        } else {

            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }
    }
}
