package com.example.user.notificationexample;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    EditText editTextTimePicker;
    TextView textViewSetAlarm;
    TextView textViewCancelAlarm;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextTimePicker = (EditText) findViewById(R.id.edit_text_time_picker);
        textViewSetAlarm = (TextView) findViewById(R.id.text_view_set_alarm);
        textViewCancelAlarm = (TextView) findViewById(R.id.text_view_cancel_alarm);
        editTextTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        timePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                Log.e("testlog", "selectedHour=" + selectedHour);
                                calendar.set(Calendar.HOUR, selectedHour);
                                calendar.set(Calendar.MINUTE, selectedMinute);
                                calendar.set(Calendar.SECOND, 0);
                                Log.e("testlog", "calendar=" + calendar.getTime());
                                editTextTimePicker.setText("" + calendar.getTime());
                            }
                        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);//Yes 24 hour time
                        timePicker.setTitle("Select Time");
                        timePicker.show();

                    }

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.setTitle("Select Date");
                datePicker.show();
            }
        });


        textViewSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
                intent.putExtra("settedTime", String.valueOf(calendar.getTime()));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 122, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(MainActivity.this, "Alarm setted", Toast.LENGTH_LONG).show();

            }
        });

        textViewCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                int rc=new Random().nextInt()+(int)calendar.getTimeInMillis();
                Log.e("testlog","RequestId="+rc);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),  rc, myIntent, 0);
                alarmManager.cancel(pendingIntent);
                Toast.makeText(MainActivity.this, "Alarm cancelled", Toast.LENGTH_LONG).show();
            }
        });

    }
}
