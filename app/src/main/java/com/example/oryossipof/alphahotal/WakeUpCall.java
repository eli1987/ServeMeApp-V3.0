package com.example.oryossipof.alphahotal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WakeUpCall extends AppCompatActivity {

    private EditText edittext;
    private Calendar myCalendar ;
    private Button wakeupBT;
    Calendar mcurrentTime;
    private DatePickerDialog picker;
    private  String roomNum;
    private ProgressDialog progress ;
    private String department = "Reception";
    private BroadcastReceiver receiver;
    private String time="";
    private String des="";
    private int valid =0 ;
    private int _selectedHour ;
    private int _min ;
    private EditText Edit_Time ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up_call);

        wakeupBT = (Button) findViewById(R.id.wakeupbt);
        progress= new ProgressDialog(WakeUpCall.this);

        roomNum = getIntent().getStringExtra("roomNum");
        myCalendar = Calendar.getInstance();


        edittext= (EditText) findViewById(R.id.DatePick);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                //view.setMinDate(System.currentTimeMillis() - 1000);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };



        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stu
                valid = 0 ;
                picker = new DatePickerDialog(WakeUpCall.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                valid++;
                picker.show();

            }
        });

        Edit_Time = (EditText) findViewById(R.id.time_view_edit);

        Edit_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(WakeUpCall.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        _selectedHour = selectedHour;
                        _min = selectedMinute;

                        if (myCalendar.get(Calendar.DATE) == mcurrentTime.get(Calendar.DATE)) // is today?
                        {
                            if((Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) >  selectedHour)
                            {}
                            else if((Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) == selectedHour )
                            {
                                if((Calendar.getInstance().get(Calendar.MINUTE) > selectedMinute))
                                {}
                                else
                                    setTextTimeAndValid();
                            }
                            else
                                setTextTimeAndValid();
                        }
                        else
                            setTextTimeAndValid();
                    }


                }, hour, minute, true);
                mTimePicker.setTitle(getResources().getString(R.string.select_time_str)); // add to strings  for all lang
                mTimePicker.show();

            }

        });


        wakeupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edittext.getText().length() == 0 || valid <2)
                {
                    Toast.makeText(WakeUpCall.this, getResources().getString(R.string.empty_date_str), Toast.LENGTH_SHORT).show();
                    return;
                }
                time = Edit_Time.getText().toString();
                BackgroundWorker bg = new BackgroundWorker(WakeUpCall.this);
                des = edittext.getText().toString() + " "+ time ;
                bg.execute("insertNewRequest",roomNum,department,"Wake up call",des);
                progress.setMessage(getResources().getString(R.string.Delivring_request_str));
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(false);
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.setProgress(0);
                progress.show();

                registerReceiver(receiver =new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String result = (String)intent.getExtras().getString("result");

                        progress.setProgress(100);
                        progress.dismiss();
                        //alertDialog.show();
                        if(result.equals("New requests accepted successfully")) {
                            Toast.makeText(WakeUpCall.this, getResources().getString(R.string.New_request_accepted_successfully_str), Toast.LENGTH_SHORT).show();

                            unregisterReceiver(receiver);

                        }
                        else if (result.equals("no one in the room"))
                        {
                            Toast.makeText(WakeUpCall.this, getResources().getString(R.string.not_occupied_str), Toast.LENGTH_SHORT).show();
                            unregisterReceiver(receiver);
                        }
                        else if (result.equals("same request"))
                        {
                            Toast.makeText(WakeUpCall.this,getResources().getString(R.string.tooManyRequests_str), Toast.LENGTH_SHORT).show();
                            unregisterReceiver(receiver);

                        }
                        else
                        {
                            Toast.makeText(WakeUpCall.this, getResources().getString(R.string.Connection_error_try_again_later_str), Toast.LENGTH_SHORT).show();
                            unregisterReceiver(receiver);
                        }

                    }

                }, new IntentFilter("resultIntent4"));
            }

        });

    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    private void setTextTimeAndValid() {

        Edit_Time.setText(_selectedHour + ":" + _min);
        time = Edit_Time.getText().toString();
        valid++;
    }

}
