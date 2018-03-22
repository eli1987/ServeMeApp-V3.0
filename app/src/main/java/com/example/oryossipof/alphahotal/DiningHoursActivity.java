package com.example.oryossipof.alphahotal;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class DiningHoursActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dining_hours);

        TextView dininghours = (TextView) findViewById(R.id.diningtextview);

        Paint paint = new Paint();
        float width = paint.measureText(InformationUtils.DINING_HOURS);  // maybe to do this in other class and than we can prevent duplicate code
        int maxLength = 17;
        if (width > maxLength) {
            List<String> arrayList = null;
            String[] array = (InformationUtils.DINING_HOURS.split("\\s"));
            arrayList = Arrays.asList(array);
            int seventyPercent = (int) (Math.round(arrayList.size() * 0.36));
            String linebreak = arrayList.get(seventyPercent) + "\n";
            arrayList.set(seventyPercent, linebreak);
            InformationUtils.DINING_HOURS = TextUtils.join(" ", arrayList);
            InformationUtils.DINING_HOURS.replace(",", " ");
            dininghours.setText(InformationUtils.DINING_HOURS);


        }
    }
}
