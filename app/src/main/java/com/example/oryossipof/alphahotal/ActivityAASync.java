package com.example.oryossipof.alphahotal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by אלי on 23/03/2018.
 */

public class ActivityAASync extends AsyncTask<String, Void, ArrayList<MyActivity>> {

    private static String typeToCheck = "";
    Context context;
    AlertDialog alertDialog;
    String result = null;
    ArrayList<MyActivity> myActivities = new ArrayList<MyActivity>();
    ArrayList<String> copyservice = new ArrayList<String>();

    ActivityAASync(Context ctx) {
        context = ctx;
    }

    @Override
    protected ArrayList<MyActivity> doInBackground(String... params) {

        String type = params[0];
        String language = params[1];

        // String login_url = "http://10.0.2.2/security/fcm_insert.php";
        // String login_url = "http://192.168.14.157/ServerMeApp/login.php";
        String login_url = "http://servemeapp.000webhostapp.com//androidDataBaseQueries.php";
        // String notification_url = "http://securitymanagementapp.000webhostapp.com//send_notiofication.php";
        if (type.equals("getActivityData")) {
            typeToCheck = "getActivityData";
            try {
                // String language = params[1];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                /*********/
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                /************/

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_date = URLEncoder.encode("lang", "UTF-8") + "=" + URLEncoder.encode(language, "UTF-8")
                        + "&"+ URLEncoder.encode("todo", "UTF-8") + "=" + URLEncoder.encode("getActivityData", "UTF-8");

                bufferedWriter.write(post_date);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line+"\n");

                }
                result =sb.toString();

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo = null;
                for(int i = 0 ; i <ja.length();i++)
                {
                    jo= ja.getJSONObject(i);
                    //the names of the string spouse to be the same in the phpmyadmin table (for now there is no table on the server).
                    myActivities.add(new MyActivity(jo.getString("activity"),jo.getString("description")));
                }



            }
            catch (Exception e)
            {

            }
            return myActivities;
        }


        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MyActivity> strings) {
        super.onPostExecute(strings);
        myActivities = strings;
        if(typeToCheck == "getActivityData")
        {
            Intent intent1 = new Intent("ActivityIntent");
            intent1.putExtra("result", myActivities);
            context.sendBroadcast(intent1);

        }
    }


}

