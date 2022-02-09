package com.example.firebase.manager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RolesManager {

    private static RolesManager sINSTANCE;

    public enum RoleType {
        Admin,
        Manager,
        Employee
    }

    public interface IOnRoleResult {
        void role(RoleType type);
        void fail();
    }

    private RolesManager() {

    }

    public static RolesManager getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new RolesManager();
        }
        return sINSTANCE;
    }

}
