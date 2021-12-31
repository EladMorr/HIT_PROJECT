package com.example.firebase.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebase.R;
import com.example.firebase.interfaces.IUserLoginCallback;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.ui.activity.HomeActivity;
import com.example.firebase.ui.activity.LoginActivity;
import com.example.firebase.ui.activity.ManagerPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerFragment extends Fragment {

    Button mConnectBTN;
    EditText mMailInput, mPasswordInput;
    String mailInput, passwordInput;
    private ProgressDialog mLoadingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manager, container, false);

        mMailInput = v.findViewById(R.id.manager_login_mail);
        mPasswordInput = v.findViewById(R.id.manager_login_password);



        mConnectBTN = v.findViewById(R.id.manager_login_connectBTN);
        mConnectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailInput = mMailInput.getText().toString();
                passwordInput = mPasswordInput.getText().toString();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference referenceManager = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-b115e-default-rtdb.firebaseio.com/")
                        .child("role")
                        .child("manager")
                        .child("admin2")
                        .child("mail");
                referenceManager.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String EmailFromDB = snapshot.getValue().toString();
                            if(EmailFromDB.equals(mailInput)){
                                UsersManager.getInstance().login(EmailFromDB, passwordInput, new IUserLoginCallback() {
                                    @Override
                                    public void onLoginSuccess() {
                                        mLoadingBar = new ProgressDialog(getActivity());
                                        Toast.makeText(getActivity(), "Welcome to manager page" , Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(),ManagerPage.class));
                                    }

                                    @Override
                                    public void onLoginFail() {
                                        Toast.makeText(getContext(), "login Failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getContext(), "login Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return v;
    }
}