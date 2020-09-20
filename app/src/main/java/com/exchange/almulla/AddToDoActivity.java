package com.exchange.almulla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddToDoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button btn_save;
    private Button btn_cancel;
    private EditText edit_tast;
    private EditText edit_dateTime;
    private int myYear;
    private int myday;
    private int day;
    private int month;
    private int myMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        initControlls();

    }

    private void initControlls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_save =(Button)findViewById(R.id.btn_save);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        edit_tast=(EditText)findViewById(R.id.edit_tast);
        edit_dateTime=(EditText)findViewById(R.id.edit_dateTime);

        edit_dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateTime();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSendData();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closewithNodata();
            }
        });
    }

    private void closewithNodata() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public boolean onNavigateUp() {
        validateAndSendData();
        return super.onNavigateUp();
    }

    private void validateAndSendData() {

        String title= edit_tast.getText().toString().trim();
        String datetime= edit_dateTime.getText().toString().trim();
        if(title.isEmpty()){
            Toast.makeText(this, "Please enter title.", Toast.LENGTH_SHORT).show();
        }else if(datetime.isEmpty()){
            Toast.makeText(this, "Please enter date.", Toast.LENGTH_SHORT).show();
        }else {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("title",title);
            returnIntent.putExtra("dateTime",datetime);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();

        }
    }

    private void pickDateTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddToDoActivity.this, AddToDoActivity.this,year, month,day);
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddToDoActivity.this, AddToDoActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int myHour = hourOfDay;
        int myMinute = minute;
        edit_dateTime.setText(myday+"/"+myMonth+"/"+myYear+" "+myHour+":"+myMinute);
    }
}
