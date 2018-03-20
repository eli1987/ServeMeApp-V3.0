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

import java.util.ArrayList;
import java.util.Locale;

public class InformationActivity extends Activity {

    public  String WEATHER_ADDRESS = "";
    public  String SHABBAT_ADDRESS = "";
   public  String FLIGHTS_ADDRESS = "";
    public  String MAPS_ADDRESS = "";
    private ListView mListView ;
    private Context context;
    private int [] drawableName= {R.drawable.weather,R.drawable.maps,R.drawable.flight,R.drawable.shabb2,R.drawable.din,R.drawable.pre,R.drawable.pool};
    private String infoDesc[] = {"Weather","Maps","Flight Times","Shabbat Hours ","Dinning Hours","Activities","Pool hours"};
    private String info[];
    private AASync AASync;
    private BroadcastReceiver serviceReceiver;
    ArrayList<HotelService> infoResult = new ArrayList<>();
    ArrayList<HotelService> newService =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_information);


        info = new String[]{getResources().getString(R.string.Weather_str),getResources().getString(R.string.Maps_str),getResources().getString(R.string.Flight_Times_str),getResources().getString(R.string.Shabbat_Hours_str),getResources().getString(R.string.Dinning_Hours_str),getResources().getString(R.string.Activities_str),getResources().getString(R.string.Pool_hours_str)};
        mListView = (ListView) findViewById(R.id.lv_housekeeping);
        final CutomAdapter2 adapter = new CutomAdapter2();
        mListView.setAdapter(adapter);
        context=this;

        String type = "getService";
        String CurrentLang = Locale.getDefault().getLanguage();
        // startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("035433333")));
        AASync = new AASync(InformationActivity.this);
        AASync.execute(type,CurrentLang);
        registerReceiver(serviceReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                infoResult = (ArrayList<HotelService>)intent.getSerializableExtra("result");
                Log.e("result",infoResult.size()+"");
                newService = new ArrayList<HotelService>();
                for(int i=0;i <infoResult.size();i++)
                {
                    WEATHER_ADDRESS = infoResult.get(i).weather_url;
                    MAPS_ADDRESS = infoResult.get(i).maps_url;
                    FLIGHTS_ADDRESS = infoResult.get(i).flightHours;
                    SHABBAT_ADDRESS = infoResult.get(i).shabbatHours;
                }
                //unregisterReceiver(receiver);

            }


        }, new IntentFilter("serviceIntent"));


    }

    class CutomAdapter2 extends BaseAdapter
    {


        @Override
        public int getCount() {
            return drawableName.length;
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
            iv.setImageResource(drawableName[i]);
            textview.setText(info[i]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    switch(index)
                    {
                        case 0:     //weather

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEATHER_ADDRESS));
                            startActivity(intent);
                            break;

                        case 1:   //MAPS
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MAPS_ADDRESS));
                            startActivity(intent);
                            break;

                        case 2:     //flights
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FLIGHTS_ADDRESS));
                            startActivity(intent);
                            break;

                        case 3:   //shabbat
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SHABBAT_ADDRESS));
                            startActivity(intent);
                            break;

                        case 4:  //Activities
                            intent = new Intent(InformationActivity.this, ActivitiesActivity.class);
                            startActivity(intent);
                            break;

                        case 5:
                            intent = new Intent(InformationActivity.this, ActivitiesActivity.class);
                            startActivity(intent);
                            break;

                        case 6:

                            break;

                    }
                }
            });
            return view;
        }
    }
}
