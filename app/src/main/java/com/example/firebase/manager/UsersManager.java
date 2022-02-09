package com.example.firebase.manager;

import android.util.Log;

import com.example.firebase.interfaces.IUserLoginCallback;
import com.example.firebase.model.User;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

public class UsersManager {


    private static UsersManager sINSTANCE;
    private FirebaseAuth mAuth;
    private final String TAG = getClass().getSimpleName();
    private User mCurrentUser;


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

    public void setCurrentUser(User currentUser) {
        this.mCurrentUser = currentUser;
    }

    public void login(String email, String password, IUserLoginCallback callback) {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                mCurrentUser = new User(user);
                mCurrentUser.setPassword(password);
                callback.onLoginSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", e);
                callback.onLoginFail();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                // If sign in fails, display a message to the user.
                callback.onLoginFail();
            }
        });
    }
}
