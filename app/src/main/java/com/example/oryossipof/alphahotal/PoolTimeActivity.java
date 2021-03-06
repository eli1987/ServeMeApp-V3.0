package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class PoolTimeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pool_time);
        TextView poolHours = (TextView) findViewById(R.id.firstlinepooltextview);

        Paint paint = new Paint();
        float width = paint.measureText(InformationUtils.PoolHours);
        int maxLength = 17;
        if (width > maxLength) {
            List<String> arrayList = null;
            String[] array = (InformationUtils.PoolHours.split("\\s"));
            arrayList = Arrays.asList(array);
            int seventyPercent = (int) (Math.round(arrayList.size() * 0.36));
            String linebreak = arrayList.get(seventyPercent) + "\n";
            arrayList.set(seventyPercent, linebreak);
            InformationUtils.PoolHours = TextUtils.join(" ", arrayList);
            InformationUtils.PoolHours.replace(",", " ");
            poolHours.setText(InformationUtils.PoolHours);


        }
    }
}
