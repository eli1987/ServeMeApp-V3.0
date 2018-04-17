package com.example.oryossipof.alphahotal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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

public class GenricAASync <E> extends AsyncTask<String, Void,  ArrayList<E>> {


    private static String typeToCheck = "";
    Context context;
    AlertDialog alertDialog;
    String result = null;
    ArrayList<E> service = new ArrayList<E>();
    ArrayList<String> copyservice = new ArrayList<String>();

    GenricAASync(Context ctx) {
        context = ctx;
    }

    @Override
    protected ArrayList<E> doInBackground(String... params) {

        String type = params[0];
        String ServerUrl =  InformationUtils.url;


        typeToCheck = type;
        try {
            URL url = new URL(ServerUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            /*********/
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            /************/


            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_date = "";
            for (int i = 1; i <= params.length; i++) {
                if (i == 1) {
                    post_date += URLEncoder.encode("params" + i, "UTF-8") + "=" + URLEncoder.encode(params[i], "UTF-8");
                } else if (i == params.length) {
                    post_date += "&" + URLEncoder.encode("todo", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                } else
                    post_date += "&" + URLEncoder.encode("params" + i, "UTF-8") + "=" + URLEncoder.encode(params[i], "UTF-8");
            }

            bufferedWriter.write(post_date);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");

            }
            result = sb.toString();

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
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                if (typeToCheck == "getEmployeeData")
                    service.add((E) new Employee(jo.getString("id"), jo.getString("fname"), jo.getString("lname"), jo.getString("department"), jo.getString("imgstr")));

                else if (typeToCheck == "getService")
                    copyservice.add(jo.getString("description"));

                else if (typeToCheck == "getActivityData")
                    service.add((E) new MyActivity(jo.getString("activity"), jo.getString("description")));

                else if (typeToCheck == "getDishData")
                    service.add((E) new Dish(jo.getInt("id"), jo.getString("dishName"), jo.getString("picStr")));
                else if (typeToCheck == "getTripData")
                    service.add((E) new Trip(jo.getInt("id"), jo.getString("tripName"), jo.getString("picStr")));

                else if (typeToCheck == "getRestaurants")
                    service.add((E) new Dish(jo.getInt("id"), jo.getString("dishName"), jo.getString("picStr")));

                else if (typeToCheck == "getRecommendedDish"){
                    if (jo.has("dishId")) {
                        service.add((E) new Dish(0, "0", ""));
                    } else
                        service.add((E) new Dish(jo.getInt("id"), jo.getString("dishName"), jo.getString("picStr")));
                }
                else if (typeToCheck  == "getRecommendedTrip")
                {
                    if (jo.has("tripId")) {
                        service.add((E) new Trip(0, "0", ""));
                    } else
                        service.add((E) new Trip(jo.getInt("id"), jo.getString("tripName"), jo.getString("picStr")));
                }
            }

            if (typeToCheck == "getService")
                service.add((E) new HotelService(copyservice.get(0), copyservice.get(1), copyservice.get(2), copyservice.get(3), copyservice.get(4), copyservice.get(5), copyservice.get(6), copyservice.get(7), copyservice.get(8)
                        , copyservice.get(9), copyservice.get(10), copyservice.get(11)));

            return service;
        } catch (Exception e) {

        }



        return null;

    }



    @Override
    protected void onPostExecute(ArrayList<E> strings) {
        super.onPostExecute(strings);
        service = strings;
        if(typeToCheck == "getService")
        {
            Intent intent1 = new Intent("serviceIntent");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }
       else if (typeToCheck == "getDishData") {
            Intent intent1 = new Intent("dishIntent");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }
        else if (typeToCheck == "getTripData") {
            Intent intent1 = new Intent("TripIntent");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }
       else if (typeToCheck == "getRecommendedDish") {
            Intent intent1 = new Intent("getRecommendedDish");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }

        else if (typeToCheck == "getRecommendedTrip") {
            Intent intent1 = new Intent("getRecommendedTrip");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }
      else  if(typeToCheck == "getEmployeeData")
        {
            Intent intent1 = new Intent("empIntent");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }
        else if(typeToCheck == "getActivityData")
        {
            Intent intent1 = new Intent("ActivityIntent");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }
        else if(typeToCheck == "getRestaurants")
        {
            Intent intent1 = new Intent("getRestaurants");
            intent1.putExtra("result", service);
            context.sendBroadcast(intent1);

        }

    }
}