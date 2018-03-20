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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BookTripActivity extends AppCompatActivity {

    EditText edittext;
    Calendar myCalendar ;
    private Button bookTripBT;
    private DatePickerDialog picker;
    private  String roomNum;
    private ProgressDialog progress ;
    private String department = "Reception";
    private BroadcastReceiver receiver;
    private String des="";
    private String time="";
    private String dest = "";
    private String pass = "";
    private Spinner spinner;
    private int valid =0 ;
    private int index=0;
    private String[] destList = {"Dead sea", "Jerusalem and old city" , "Tel-aviv and Jafa", "Tiberias and Sea of Galilee", "Caesarea", "Mitzpe Ramon and the south"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_trip);

        bookTripBT = (Button) findViewById(R.id.ordertrip_button1);
        progress= new ProgressDialog(BookTripActivity.this);

        roomNum = getIntent().getStringExtra("roomNum");
        myCalendar = Calendar.getInstance();
        spinner = (Spinner) findViewById(R.id.spinner_ip);



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
                picker = new DatePickerDialog(BookTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
               // valid++;
                picker.show();

            }
        });

        /*
        final EditText Edit_Time = (EditText) findViewById(R.id.time_view_edit);

        Edit_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        Edit_Time.setText(selectedHour + ":" + selectedMinute);
                        time = Edit_Time.getText().toString();
                        valid++;

                    }
                    Calendar mcurrentTime = Calendar.getInstance();
                }, hour, minute, true);
                mTimePicker.setTitle(getResources().getString(R.string.select_time_str)); // add to strings  for all lang
                mTimePicker.show();

            }
        });
*/
        //set spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_ip);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // dest = spinner.getSelectedItem().toString();
                index = spinner.getSelectedItemPosition();
                dest = destList[index];

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // dest = spinner.getSelectedItem().toString();
                index = spinner.getSelectedItemPosition();
                dest = destList[index];


            }
        });

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Number_of_pass);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pass = spinner2.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pass = spinner2.getSelectedItem().toString();;
            }
        });

        bookTripBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (edittext.getText().length() == 0 || valid <2)
                if (edittext.getText().length() == 0)
                {
                    Toast.makeText(BookTripActivity.this, getResources().getString(R.string.empty_date_str), Toast.LENGTH_SHORT).show();
                    return;
                }
                //time = Edit_Time.getText().toString();
                BackgroundWorker bg = new BackgroundWorker(BookTripActivity.this);
             //   des = edittext.getText().toString() + " "+ time + " " + destList[spinner.getSelectedItemPosition()] + ", " + pass + " Passengers";
                des = edittext.getText().toString() + " " + destList[spinner.getSelectedItemPosition()] + ", " + pass + " Passengers";
                bg.execute("insertNewRequest",roomNum,department,"Order Trip",des);
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
                            Toast.makeText(BookTripActivity.this, getResources().getString(R.string.New_request_accepted_successfully_str), Toast.LENGTH_SHORT).show();

                            unregisterReceiver(receiver);

                        }
                        else if (result.equals("no one in the room"))
                        {
                            Toast.makeText(BookTripActivity.this, getResources().getString(R.string.not_occupied_str), Toast.LENGTH_SHORT).show();
                            unregisterReceiver(receiver);
                        }
                        else if (result.equals("same request"))
                        {
                            Toast.makeText(BookTripActivity.this,getResources().getString(R.string.tooManyRequests_str), Toast.LENGTH_SHORT).show();
                            unregisterReceiver(receiver);

                        }
                        else
                        {
                            Toast.makeText(BookTripActivity.this, getResources().getString(R.string.Connection_error_try_again_later_str), Toast.LENGTH_SHORT).show();
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
}
