package com.example.firebase.manager;

import com.example.firebase.model.Shift;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShiftsManager {

    private static ShiftsManager sINSTANCE;
    private Map<String, Shift> mShifts;

    private ShiftsManager() {
        mShifts = new HashMap<>();
    }

    public interface IOnShiftsLoaded {
        void onShiftsLoaded(ArrayList<Shift> shifts);
    }

    public static ShiftsManager getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new ShiftsManager();
        }
        return sINSTANCE;
    }

    public void addShift(Shift s) {
        DatabaseManager.getInstance().getShiftsDatabaseRef().child(s.getId()).setValue(s, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                ref.get();
                mShifts.put(s.getId(), s);
            }
        });
    }

    public void getAllShifts(IOnShiftsLoaded listener) {
        Task<DataSnapshot> snap = DatabaseManager.getInstance().getShiftsDatabaseRef().get();
        snap.addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Map<String, Map<String , Map<String , Object>>> shiftMap = (Map<String, Map<String, Map<String, Object>>>) dataSnapshot.getValue();
//                for (Map<String , Map<String , Map<String  , >>>:
//                     ) {
//
//                }

                String d = "";
//                for (Shift s: shiftMap.values()) {
//                    s.getEndTime();
//                }

//                ArrayList<Shift> arr = new ArrayList<>(shiftMap.values());
//                listener.onShiftsLoaded(arr);
            }
        });
    }

    public void removeShift(String shiftId) {
        mShifts.remove(shiftId);
    }

    public void removeShift(Shift shift) {
        removeShift((shift.getId()));
    }

    public Shift getShift(String id) {
        return mShifts.get(id);
    }

    public void updateShift(Shift shift) {
        mShifts.put(shift.getId(), shift);
    }
}
