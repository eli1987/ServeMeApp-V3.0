package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class RecommendedForYouActivity extends Activity {

    //private DishAASync DishAASync;

    private GenricAASync<Dish> genricAASync;
    private BroadcastReceiver receiver;
    private String roomNum;
    ArrayList<Dish> result = new ArrayList<>();
    ArrayList<Dish> newUsers =new ArrayList<>();
    private ProgressDialog progress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recommended_for_you);
        final  TextView dishtName = (TextView) findViewById(R.id.RecommendedAnsTv);
        final ImageView ImageStr = (ImageView) findViewById(R.id.Recommendeddishiv);
        roomNum = getIntent().getStringExtra("roomNum");
        progress= new ProgressDialog(RecommendedForYouActivity.this);

        String type = "getRecommendedDish";

        genricAASync = new GenricAASync<Dish>(RecommendedForYouActivity.this);
        genricAASync.execute(type,roomNum ,Locale.getDefault().getLanguage());
        progress.setMessage(getResources().getString(R.string.Delivring_request_str));
        progress.setProgressStyle(R.style.AppTheme_Dark_Dialog);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setProgress(0);
        progress.show();

        registerReceiver(receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result = (ArrayList<Dish>)intent.getSerializableExtra("result");
                //Log.e("result",result.size()+"");
                if(result == null)
                {
                    dishtName.setText(getResources().getString(R.string.Sorry_str));
                    progress.dismiss();
                }



                else
                {
                    newUsers = new ArrayList<Dish>();
                    for(int i=0;i <result.size();i++)
                    {
                        newUsers.add(result.get(i));
                    }
                    dishtName.setText(newUsers.get(0).dishName);
                    Picasso.with(context).load("http://servemeapp.000webhostapp.com/"+newUsers.get(0).getDishImg()).fit().into(ImageStr);
                    progress.dismiss();
                }

                unregisterReceiver(receiver);

            }
        }, new IntentFilter("getRecommendedDish"));



    }
}
