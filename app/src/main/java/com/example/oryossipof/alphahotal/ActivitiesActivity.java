package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class  ActivitiesActivity extends Activity {

    private ActivityAASync ActivityAASync;
    private BroadcastReceiver receiver;

    ArrayList<MyActivity>  result = new ArrayList<>();
    ArrayList<MyActivity> newUsers =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activities);

        ArrayList<MyActivity> arrayOfUsers = new ArrayList<MyActivity>();
        // Create the adapter to convert the array to views
        final ActivityAdapter adapter = new ActivityAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.activityListView);

        String type = "getActivityData";
        // Add item to adapter
        listView.setAdapter(adapter);

        ActivityAASync = new ActivityAASync(ActivitiesActivity.this);
        ActivityAASync.execute(type, Locale.getDefault().getLanguage());
        registerReceiver(receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (ArrayList<MyActivity>)intent.getSerializableExtra("result");
                Log.e("result",result.size()+"");
                newUsers = new ArrayList<MyActivity>();
                for(int i=0;i <result.size();i++)
                {
                    newUsers.add(result.get(i));
                }

                adapter.addAll(newUsers);
                listView.setAdapter(adapter);
                unregisterReceiver(receiver);
            }


        }, new IntentFilter("ActivityIntent"));

    }


}
