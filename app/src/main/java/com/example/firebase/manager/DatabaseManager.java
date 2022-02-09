package com.example.firebase.manager;

import com.example.firebase.interfaces.IPostCallback;
import com.example.firebase.model.Post;
import com.example.firebase.model.Shift;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

public class DatabaseManager {

    private static DatabaseManager sINSTANCE;
    private DatabaseReference database;
    private DatabaseReference mPostRef;
    private DatabaseReference mRoles;
    private DatabaseReference mShiftRef;


    public static DatabaseManager getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new DatabaseManager();
        }
        return sINSTANCE;
    }

    private DatabaseManager() {
        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-b115e-default-rtdb.firebaseio.com/");
        mPostRef = database.child("posts");
        mRoles = database.child("role");
//        mUserRef = database.child("users");
        mShiftRef = database.child("shift");
    }

    public void addShift(Shift shift) {
        mShiftRef.setValue(shift);
    }

    public void removeShift(Shift shift) {
        mShiftRef.child(shift.getId()).removeValue();
    }

    public void addPost(Post post) {
        mPostRef.setValue(post);
    }

    public void removePost(Post post) {
        mPostRef.child(post.getID()).removeValue();
    }

//    public void addUser(User user) {
//        mUserRef.setValue(user);
//    }
//
//    public void removeUser(User user) {
//        mUserRef.child(user.getEmail()).removeValue();
//    }

    public void getPostsByRole(RolesManager.RoleType role, IPostCallback callback) {
        String postType = role == RolesManager.RoleType.Manager ? "managerPosts" : "employeePosts";
        mPostRef.child(postType).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot ds = task.getResult();
                    String posts = (String) ds.getValue();
                    callback.onPostsLoaded(posts);
                }
            }
        });
    }

    public void getUserRoleType(String mail, RolesManager.IOnRoleResult result) {
        mRoles.child("manager").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot ds = task.getResult();
                    String admin1Mail = (String) ds.child("admin1").child("mail").getValue();
                    String admin2Mail = (String) ds.child("admin2").child("mail").getValue();

                    RolesManager.RoleType role = RolesManager.RoleType.Employee;
                    if (mail.equals(admin1Mail) || mail.equals(admin2Mail)) {
                        role = RolesManager.RoleType.Manager;
                    }
                    result.role(role);
                } else {
                    result.fail();
                }
            }
        });
    }

    public void addShift(){
//        mShiftRef.getsetValue("shifts");
    }
}
