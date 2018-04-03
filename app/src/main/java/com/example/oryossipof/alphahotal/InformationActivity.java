package com.example.oryossipof.alphahotal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class InformationActivity extends Activity {

    public  String WEATHER_ADDRESS = "";
    public  String SHABBAT_ADDRESS = "";
   public  String FLIGHTS_ADDRESS = "";
    public  String MAPS_ADDRESS = "";
    private ListView mListView ;
    private Context context;
    private int [] drawableName= {R.drawable.weather,R.drawable.maps,R.drawable.flight,R.drawable.shabb2,R.drawable.din,R.drawable.pre,R.drawable.pool,R.drawable.recommendedicon};
    private String infoDesc[] = {"Weather","Maps","Flight Times","Shabbat Hours ","Dinning Hours","Activities","Pool hours","Recommended dish for you"};
    private String info[];

    private String roomNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_information);


        info = new String[]{getResources().getString(R.string.Weather_str),getResources().getString(R.string.Maps_str),getResources().getString(R.string.Flight_Times_str),getResources().getString(R.string.Shabbat_Hours_str),getResources().getString(R.string.Dinning_Hours_str),getResources().getString(R.string.Activities_str),getResources().getString(R.string.Pool_hours_str),getResources().getString(R.string.RecommandeDish_str)};
        mListView = (ListView) findViewById(R.id.lv_housekeeping);
        final CutomAdapter2 adapter = new CutomAdapter2();
        mListView.setAdapter(adapter);
        context=this;
        roomNum = getIntent().getStringExtra("roomNum");



        WEATHER_ADDRESS = InformationUtils.WEATHER_ADDRESS;
        MAPS_ADDRESS = InformationUtils.MAPS_ADDRESS;
        FLIGHTS_ADDRESS = InformationUtils.FLIGHTS_ADDRESS;
        SHABBAT_ADDRESS = InformationUtils.SHABBAT_ADDRESS;

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
                            intent = new Intent(InformationActivity.this, DiningHoursActivity.class);
                            startActivity(intent);
                            break;

                        case 5:
                            intent = new Intent(InformationActivity.this, ActivitiesActivity.class);
                            startActivity(intent);
                            break;

                        case 6:
                            intent = new Intent(InformationActivity.this, PoolTimeActivity.class);
                            startActivity(intent);

                            break;

                        case 7:
                            intent = new Intent(InformationActivity.this, RecommendedForYouActivity.class);
                            intent.putExtra("roomNum",roomNum);
                            startActivity(intent);

                            break;

                    }
                }
            });
            return view;
        }
    }
}
