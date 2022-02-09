package com.example.firebase.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firebase.R;
import com.example.firebase.manager.DatabaseManager;
import com.example.firebase.manager.RolesManager;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.User;
import com.example.firebase.ui.activity.AddShiftActivity;
import com.example.firebase.ui.activity.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.Fragment;

public class ManagerFragment extends Fragment {

    private ProgressDialog mLoadingBar;
    private User mCurrentUser;
    private FloatingActionButton mMainBtn;
    private FloatingActionButton mAddPersonBtn;
    private FloatingActionButton mAddShiftBtn;
    private TextView mTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manager, container, false);
        mTitle = v.findViewById(R.id.managerFragmentTitle);
        mMainBtn = v.findViewById(R.id.managerAddBtn);
        mAddPersonBtn = v.findViewById(R.id.managerAddEmployee);
        mAddShiftBtn = v.findViewById(R.id.managerAddShift);

        mCurrentUser = UsersManager.getInstance().getCurrentUser();
        mTitle.setVisibility(View.GONE);

        if(mCurrentUser.isUserManager()){
            loadManagerView();
        }else{
            mTitle.setVisibility(View.VISIBLE);
            mMainBtn.setVisibility(View.GONE);
        }

        return v;
    }

    private void loadManagerView(){
        mMainBtn.setVisibility(View.VISIBLE);
        mMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleAddButtons(!(mAddPersonBtn.getVisibility() == View.VISIBLE));
            }
        });
    }

    private void toggleAddButtons(boolean visible){
        mAddPersonBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
        mAddShiftBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
        if(visible){
            mAddPersonBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity() , RegisterActivity.class));
                }
            });
            mAddShiftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity() , AddShiftActivity.class));
                }
            });
        }else{
            mAddPersonBtn.setOnClickListener(null);
            mAddShiftBtn.setOnClickListener(null);
        }
    }
}