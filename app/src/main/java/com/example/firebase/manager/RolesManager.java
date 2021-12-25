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
    }

    private RolesManager() {

    }

    public static RolesManager getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new RolesManager();
        }
        return sINSTANCE;
    }

    public RoleType getUserRoleType(String mail, IOnRoleResult result) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-b115e-default-rtdb.firebaseio.com/");
        reference.child("roles").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    boolean match = false;
                    String roles = (String) task.getResult().getValue();
                    RoleType role = RoleType.Employee;
                    try {
                        JSONObject jo = new JSONObject(roles);

                        JSONArray ja = jo.getJSONArray("admin");
                        for (int i = 0; i < ja.length(); i++) {
                            if (mail.equals(ja.getString(i))) {
                                match = true;
                                role = RoleType.Admin;
                            }
                        }

                        if (!match) {
                            JSONArray managers = jo.getJSONArray("manager");
                            for (int i = 0; i < managers.length(); i++) {
                                if (mail.equals(managers.getString(i))) {
                                    role = RoleType.Manager;
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

        return RoleType.Employee;
    }
}
