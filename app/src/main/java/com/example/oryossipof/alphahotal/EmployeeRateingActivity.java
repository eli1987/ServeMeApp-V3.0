package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class EmployeeRateingActivity extends Activity {


    private EmpAASync EmpAASync;
    private BroadcastReceiver receiver;
    private String roomNum;
    ArrayList<Employee>  result = new ArrayList<>();
    ArrayList<Employee> newUsers =new ArrayList<>();
    String workerNum;
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee_rateing);

        roomNum = getIntent().getStringExtra("roomNum");

        ArrayList<Employee> employess = new ArrayList<Employee>();
        // Create the adapter to convert the array to views
        adapter = new EmployeeAdapter(this, employess);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.employeeListView);

        String type = "getEmployeeData";
        // Add item to adapter
        listView.setAdapter(adapter);

        EmpAASync = new EmpAASync(EmployeeRateingActivity.this);
        EmpAASync.execute(type, Locale.getDefault().getLanguage());
        registerReceiver(receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (ArrayList<Employee>)intent.getSerializableExtra("result");
                Log.e("result",result.size()+"");
                newUsers = new ArrayList<Employee>();
                for(int i=0;i <result.size();i++)
                {
                    newUsers.add(result.get(i));
                }

                adapter.addAll(newUsers);
                adapter.newUsers = newUsers;
                adapter.context =EmployeeRateingActivity.this;
                adapter.roomNum = roomNum;
                listView.setAdapter(adapter);
                unregisterReceiver(receiver);
            }


        }, new IntentFilter("empIntent"));

    }


}
