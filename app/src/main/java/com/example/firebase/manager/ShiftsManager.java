package com.example.firebase.manager;

import com.example.firebase.model.Shift;

import java.util.HashMap;
import java.util.Map;

public class ShiftsManager {

    private static ShiftsManager sINSTANCE;
    private Map<String, Shift> mShifts;

    private ShiftsManager() {
        mShifts = new HashMap<>();
    }

    public ShiftsManager getInstance() {
        if (sINSTANCE == null) {
            sINSTANCE = new ShiftsManager();
        }
        return sINSTANCE;
    }

    public Shift createShift() {
        Shift s = new Shift();
        mShifts.put(s.getId(), s);
        return s;
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

    public void updateShift(Shift shift){
        mShifts.put(shift.getId() , shift);
    }
}
