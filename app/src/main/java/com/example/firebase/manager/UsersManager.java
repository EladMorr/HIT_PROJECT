package com.example.firebase.manager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.firebase.interfaces.IUserLoginCallback;
import com.example.firebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersManager {

    private FirebaseAuth mAuth;
    private static UsersManager sINSTANCE;
    private User mCurrentUser;
    private String TAG = getClass().getSimpleName();

    private UsersManager() {
    }

    public static UsersManager getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new UsersManager();
        }
        return sINSTANCE;
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void login(String email, String password, IUserLoginCallback callback) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();
        if (fbUser == null) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                RolesManager.getInstance().getUserRoleType(user.getEmail(), new RolesManager.IOnRoleResult() {
                                    @Override
                                    public void role(RolesManager.RoleType type) {
                                        mCurrentUser = new User(user, type);
                                        callback.onLoginSuccess();
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                callback.onLoginFail();
                            }
                        }
                    });
        } else {
            callback.onLoginSuccess();
        }
    }
}
