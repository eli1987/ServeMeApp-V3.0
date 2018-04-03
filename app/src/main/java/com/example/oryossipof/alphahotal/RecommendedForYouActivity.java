package com.example.oryossipof.alphahotal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class RecommendedForYouActivity extends AppCompatActivity {

    private DishAASync DishAASync;
    private BroadcastReceiver receiver;
    private String roomNum;
    ArrayList<Dish> result = new ArrayList<>();
    ArrayList<Dish> newUsers =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recommended_for_you);
        final  TextView dishtName = (TextView) findViewById(R.id.RecommendedAnsTv);
        final ImageView ImageStr = (ImageView) findViewById(R.id.Recommendeddishiv);

        roomNum = getIntent().getStringExtra("roomNum");

        String type = "getRecommendedDish";

        DishAASync = new DishAASync(RecommendedForYouActivity.this);
        DishAASync.execute(type,roomNum ,Locale.getDefault().getLanguage());

        registerReceiver(receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (ArrayList<Dish>)intent.getSerializableExtra("result");
                Log.e("result",result.size()+"");
                newUsers = new ArrayList<Dish>();
                for(int i=0;i <result.size();i++)
                {
                    newUsers.add(result.get(i));
                }

                if(result.size() == 0)
                {
                    dishtName.setText(getResources().getString(R.string.Sorry_str));
                }
                else
                {
                    dishtName.setText(newUsers.get(0).dishName);
                    Picasso.with(context).load("http://servemeapp.000webhostapp.com/"+newUsers.get(0).getDishImg()).fit().into(ImageStr);

                }

                unregisterReceiver(receiver);

            }
        }, new IntentFilter("getRecommendedDish"));



    }
}
