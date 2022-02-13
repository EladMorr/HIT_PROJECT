package com.example.firebase.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.firebase.R;
import com.example.firebase.manager.ShiftsManager;
import com.example.firebase.manager.UsersManager;
import com.example.firebase.model.Shift;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddShiftActivity extends AppCompatActivity {

    private TextView mSelectedDateTextVIew;
    private View mChooseDateExpandBtn;

    private Button mAddShiftBtn;

    private View mChooseTimeExpandBtn;
    private TextView mSelectedStartTimeTextView;
    private TextView mSelectedEndTimeTextView;
    private LinearLayout mStartTimeBtn;
    private LinearLayout mEndTimeBtn;

    private Calendar mCalendar = Calendar.getInstance();
    private SimpleDateFormat mSdf = new SimpleDateFormat("dd/MM/yy");

    private NumberPicker mNumberOfEmployeePicker;

    private boolean mStartTimeSet = true;

    private long mSelectedStartTime;
    private long mSelectedEndTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        mAddShiftBtn = findViewById(R.id.addShiftBtn);
        mSelectedDateTextVIew = findViewById(R.id.addShiftSelectedDate);

        mSelectedStartTimeTextView = findViewById(R.id.addShiftSelectedStartTime);
        mSelectedEndTimeTextView = findViewById(R.id.addShiftSelectedEndTime);
        mStartTimeBtn = findViewById(R.id.addShiftStartTimeBtn);
        mEndTimeBtn = findViewById(R.id.addShiftEndTimeBtn);

        mChooseDateExpandBtn = findViewById(R.id.addShiftChooseDateExpandBtn);
        mChooseTimeExpandBtn = findViewById(R.id.addShiftChooseTimeExpandBtn);

        mNumberOfEmployeePicker = findViewById(R.id.addShiftNumOfEmployees);

        initViews();
    }

    private void initViews() {

        updateSelectedDate();

        updateSelectedTime(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
        mStartTimeSet = false;
        updateSelectedTime(mCalendar.get(Calendar.HOUR_OF_DAY) + 1, mCalendar.get(Calendar.MINUTE));

        mAddShiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shift shift = new Shift();
                shift.setShiftManager(UsersManager.getInstance().getCurrentUser());
                shift.setStartTime(mSelectedStartTime);
                shift.setEndTime(mSelectedEndTime);
                ShiftsManager.getInstance().addShift(shift);
            }
        });

        mChooseDateExpandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        mStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTimeSet = true;
                showTimePickerDialog();
            }
        });

        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTimeSet = false;
                showTimePickerDialog();
            }
        });
        mNumberOfEmployeePicker.setMinValue(1);
        mNumberOfEmployeePicker.setMaxValue(50);
        mNumberOfEmployeePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String text = "Changed from " + oldVal + " to " + newVal;
//                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSelectedDate() {
        mSelectedDateTextVIew.setText(mSdf.format(mCalendar.getTime()));
    }

    private void updateSelectedTime(int hour, int minutes) {
        String selected = String.format("%s:%s", hour, minutes);
        if (mStartTimeSet) {
            mSelectedStartTimeTextView.setText(selected);
        } else {
            mSelectedEndTimeTextView.setText(selected);
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog dpd = new DatePickerDialog(AddShiftActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.YEAR, year);
                updateSelectedDate();
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog tpd = new TimePickerDialog(AddShiftActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                updateSelectedTime(hourOfDay, minute);
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
        tpd.show();
    }

    private void syncWithCalendar() {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, mCalendar.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, mCalendar.getTimeInMillis());
        values.put(CalendarContract.Events.TITLE, "idan test");
        values.put(CalendarContract.Events.DESCRIPTION, "comment");

        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        values.put(CalendarContract.Events.CALENDAR_ID, 1);

//                values.put(CalendarContract.Events.DURATION, "+P1H");

        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
    }
}
