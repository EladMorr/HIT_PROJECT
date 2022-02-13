package com.example.firebase.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebase.R;
import com.example.firebase.manager.ShiftsManager;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.Shift;
import com.example.firebase.model.User;
import com.example.firebase.ui.activity.AddShiftActivity;
import com.example.firebase.ui.activity.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ManagerFragment extends Fragment {

    private ProgressDialog mLoadingBar;
    private User mCurrentUser;
    private FloatingActionButton mMainBtn;
    private FloatingActionButton mAddPersonBtn;
    private FloatingActionButton mAddShiftBtn;
    private TextView mTitle;
    private RecyclerView mAllShiftsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manager, container, false);
        mTitle = v.findViewById(R.id.managerFragmentTitle);
        mMainBtn = v.findViewById(R.id.managerAddBtn);
        mAddPersonBtn = v.findViewById(R.id.managerAddEmployee);
        mAddShiftBtn = v.findViewById(R.id.managerAddShift);
        mAllShiftsRecyclerView = v.findViewById(R.id.managerAllShiftsRecyclerView);

        mCurrentUser = UsersManager.getInstance().getCurrentUser();
        mTitle.setVisibility(View.GONE);

        if (mCurrentUser.isUserManager()) {
            loadManagerView();
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mMainBtn.setVisibility(View.GONE);
        }

        return v;
    }

    private void loadManagerView() {
        mMainBtn.setVisibility(View.VISIBLE);
        mMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleAddButtons(!(mAddPersonBtn.getVisibility() == View.VISIBLE));
            }
        });

        mAllShiftsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ShiftsManager.getInstance().getAllShifts(new ShiftsManager.IOnShiftsLoaded() {
            @Override
            public void onShiftsLoaded(ArrayList<Shift> shifts) {
                mAllShiftsRecyclerView.setAdapter(new AllShiftsRecyclerView(shifts));
            }
        });
    }

    private void toggleAddButtons(boolean visible) {
        mAddPersonBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
        mAddShiftBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (visible) {
            mAddPersonBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), RegisterActivity.class));
                }
            });
            mAddShiftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), AddShiftActivity.class));
                }
            });
        } else {
            mAddPersonBtn.setOnClickListener(null);
            mAddShiftBtn.setOnClickListener(null);
        }
    }

    public static class AllShiftsRecyclerView extends RecyclerView.Adapter<AllShiftsViewHolder> {

        private ArrayList<Shift> mShifts;

        public AllShiftsRecyclerView(ArrayList<Shift> shifts) {
            mShifts = shifts;
        }

        @NonNull
        @Override
        public AllShiftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AllShiftsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_shifts_view_holder_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AllShiftsViewHolder holder, int position) {
            holder.onBind(mShifts.get(position));
        }

        @Override
        public int getItemCount() {
            return mShifts.size();
        }
    }

    public static class AllShiftsViewHolder extends RecyclerView.ViewHolder {

        private View mLayout;
        private TextView mStartTime;
        private TextView mEndTime;
        private TextView mNumOfEmployees;

        public AllShiftsViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = itemView;
            mStartTime = mLayout.findViewById(R.id.shitItemStartTime);
            mEndTime = mLayout.findViewById(R.id.shitItemEndTime);
            mNumOfEmployees = mLayout.findViewById(R.id.shitItemNumOfEmployees);
        }

        public void onBind(Shift shift) {
            mStartTime.setText(String.valueOf(shift.getStartTime()));
            mEndTime.setText(String.valueOf(shift.getEndTime()));
            mNumOfEmployees.setText(String.valueOf(shift.getNumOFEmployees()));

        }

    }
}