package com.example.firebase.manager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.firebase.interfaces.IUserLoginCallback;
import com.example.firebase.model.Post;
import com.example.firebase.model.Shift;
import com.example.firebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseManager {

    private static DatabaseManager sINSTANCE;
    private FirebaseDatabase database;
    private DatabaseReference mPostRef;
    private DatabaseReference mUserRef;
    private DatabaseReference mShiftRef;


    public static DatabaseManager getInstance(){
        if(sINSTANCE == null) {
            sINSTANCE = new DatabaseManager();
        }
        return sINSTANCE;
    }

    private DatabaseManager(){
        database = FirebaseDatabase.getInstance();
        mPostRef = database.getReference("Post");
        mUserRef = database.getReference("users");
        mShiftRef = database.getReference("Shift");
    }

    public void addShift(Shift shift){
        mShiftRef.setValue(shift);
    }

    public void removeShift(Shift shift){
        mShiftRef.child(shift.getId()).removeValue();
    }

    public void addPost(Post post){
        mPostRef.setValue(post);
    }

    public void removePost(Post post){
        mPostRef.child(post.getID()).removeValue();
    }

    public void addUser(User user){
        mUserRef.setValue(user);
    }

    public void removeUser(User user){
        mUserRef.child(user.getEmail()).removeValue();
    }

    public RolesManager.RoleType getUserRoleType(String mail, RolesManager.IOnRoleResult result) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-b115e-default-rtdb.firebaseio.com/");
        reference.child("role").child("manager").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    boolean match = false;
                    String roles = (String) task.getResult().getValue();
                    RolesManager.RoleType role = RolesManager.RoleType.Employee;
                    try {
                        JSONObject jo = new JSONObject(roles);

                        JSONArray ja = jo.getJSONArray("admin");
                        for (int i = 0; i < ja.length(); i++) {
                            if (mail.equals(ja.getString(i))) {
                                match = true;
                                role = RolesManager.RoleType.Admin;
                            }
                        }

                        if (!match) {
                            JSONArray managers = jo.getJSONArray("manager");
                            for (int i = 0; i < managers.length(); i++) {
                                if (mail.equals(managers.getString(i))) {
                                    role = RolesManager.RoleType.Manager;
                                }
                            }
                        }

                        result.role(role);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return RolesManager.RoleType.Employee;
    }


}
