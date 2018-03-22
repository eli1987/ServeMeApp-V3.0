package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmployeeRateingActivity extends Activity {

    ArrayList<String> idArr = new ArrayList<>();
    String login_url = "http://servemeapp.000webhostapp.com//androidDataBaseQueries.php";
    private EmpAASync EmpAASync;
    private BackgroundWorker backgroundWorkerForUpdate;
    private BroadcastReceiver receiver;
    private String updateResult;
    private BroadcastReceiver updateReceiver;
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

        ArrayList<Employee> employess = new ArrayList<Employee>();
        // Create the adapter to convert the array to views
        adapter = new EmployeeAdapter(this, employess);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.employeeListView);

        String type = "getEmployeeData";
        // Add item to adapter
        listView.setAdapter(adapter);

        EmpAASync = new EmpAASync(EmployeeRateingActivity.this);
        EmpAASync.execute(type);
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
                //unregisterReceiver(receiver);
                adapter.addAll(newUsers);
                listView.setAdapter(adapter);
            }


        }, new IntentFilter("empIntent"));


    }


}
