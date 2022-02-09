package com.example.firebase.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebase.R;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.User;
import com.example.firebase.ui.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private TextView mEmployeeName;
    private TextView mEmployeeMail;
    private TextView mEmployeePhone;
    private Button mLogoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        mEmployeeMail = v.findViewById(R.id.settingsEmployeeMail);
        mEmployeeName = v.findViewById(R.id.settingsEmployeeName);
        mEmployeePhone = v.findViewById(R.id.settingsEmployeePhone);
        mLogoutButton = v.findViewById(R.id.settingsLogOutBtn);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });

        setEmployeeDetails();

        return v;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Logout");
        dialog.setMessage("Are you sure you want to logout?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void setEmployeeDetails() {
        User user = UsersManager.getInstance().getCurrentUser();
        mEmployeeName.setText(user.getFullName());
        mEmployeeMail.setText(user.getEmail());
        mEmployeePhone.setText(user.getPhoneNumber());
    }
}