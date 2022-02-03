package com.example.firebase.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.firebase.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-b115e-default-rtdb.firebaseio.com//");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        NavHostFragment nhf = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.managerfragmentContainerView);
        NavController navController = nhf.getNavController();
                bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.home:
                                navController.navigate(R.id.homePage);
                                break;
                            case R.id.personalPage:
                                navController.navigate(R.id.personalPage);
                                break;
                            case R.id.settings:
                                navController.navigate(R.id.settings);
                                break;
                            case R.id.managerFragment3:
                                navController.navigate(R.id.managerFragment3);
                                break;
                        }
                        return true;
                    }
                });

//        b_deleteAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String phoneNumber = UsersManager.getInstance().getCurrentUser().getPhoneNumber();
//                reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.hasChild(phoneNumber)){
//                            User user = snapshot.child(phoneNumber).getValue(User.class);
//                            if(user.getPhoneNumber().equals(phoneNumber)){
//                                reference.child("users").child(phoneNumber).removeValue();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//               // AlertDialog.Builder builder = new AlertDialog.Builder(holder)
//            }
//        });
    }
}