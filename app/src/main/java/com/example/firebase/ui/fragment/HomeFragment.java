package com.example.firebase.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.firebase.R;
import com.example.firebase.interfaces.IPostCallback;
import com.example.firebase.manager.DatabaseManager;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.User;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private LinearLayout mPostsContainer;
    private TextView mHelloUser;
    private User mUser;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        mHelloUser = v.findViewById(R.id.helloUser);
        mPostsContainer = v.findViewById(R.id.postsContainer);
        mUser = UsersManager.getInstance().getCurrentUser();
        mProgressBar = v.findViewById(R.id.postsLoadingProgressBar);

        mHelloUser.setText("Hi\n"+mUser.getFullName());

        DatabaseManager.getInstance().getPostsByRole(mUser.getRoleType(), new IPostCallback() {
            @Override
            public void onPostsLoaded(String posts) {
                TextView post = new TextView(getActivity());
                post.setText(posts);
                mPostsContainer.addView(post);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}