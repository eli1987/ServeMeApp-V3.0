package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class FeedbackMenuActivity extends Activity {
    public  String BOOKING_ADDRESS ="";
    public  String TRIPADVISOR_ADDRESS = "";
    private int drawableNames[] = {R.drawable.workers,R.drawable.foodrat,R.drawable.booking,R.drawable.tripadv,R.drawable.feedback2};
    private String  feedback[];

    private Class[] connections = {MainActivity.class, MainActivity.class, MainActivity.class,MainActivity.class, FeedbackActivity.class};

    private ListView mListView ;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_feedback_menu);
        feedback =new String[]{getResources().getString(R.string.Employees_Rating_str),getResources().getString(R.string.Food_Rating_str),"Booking.com","Tripadvisor.com",getResources().getString(R.string.Hotel_Feedback_str)};


        mListView = (ListView) findViewById(R.id.lv_housekeeping);
        final CutomAdapter2 adapter = new CutomAdapter2();
        mListView.setAdapter(adapter);
        context=this;


                    BOOKING_ADDRESS = InformationUtils.BOOKING_ADDRESS;
                    TRIPADVISOR_ADDRESS = InformationUtils.TRIPADVISOR_ADDRESS;


    }

    class CutomAdapter2 extends BaseAdapter
    {


        @Override
        public int getCount() {
            return drawableNames.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int index = i;
            view = getLayoutInflater().inflate(R.layout.listview_layout,null);
            ImageView iv= (ImageView) view.findViewById(R.id.imageviewlayout);
            TextView textview = (TextView) view.findViewById(R.id.textviewLayout);
            iv.setImageResource(drawableNames[i]);
            textview.setText(feedback[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent  intent;
                    switch(index)
                    {

                        case 0:
                            intent = new Intent(FeedbackMenuActivity.this, EmployeeRateingActivity.class);
                            startActivity(intent);
                             break;

                        case 1:
                            Toast.makeText(FeedbackMenuActivity.this, "Food", Toast.LENGTH_SHORT).show();
                            break;

                        case 2:
                             intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BOOKING_ADDRESS));
                             startActivity(intent);
                             break;

                        case 3:
                             intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TRIPADVISOR_ADDRESS));
                             startActivity(intent);
                             break;

                        case 4:
                            intent = new Intent(FeedbackMenuActivity.this, connections[index]);
                            startActivity(intent);
                            break;


                    }

                }
            });

            return view;
        }
    }
}
