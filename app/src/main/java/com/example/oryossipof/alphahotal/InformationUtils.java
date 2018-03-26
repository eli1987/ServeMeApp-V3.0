package com.example.oryossipof.alphahotal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;


public class InformationUtils {
    static String PoolHours;
    static  String WEATHER_ADDRESS = "";
    static  String SHABBAT_ADDRESS = "";
    static  String FLIGHTS_ADDRESS = "";
    static  String BOOKING_ADDRESS ="";
    static  String TRIPADVISOR_ADDRESS ="";
    static String CallSecrity = "";
    static  String MAPS_ADDRESS = "";
    static String ROOM_SERVICE_CALL;
    static String MAINTENANCE_CALL;
    static String DINING_HOURS;


    static BroadcastReceiver serviceReceiver;
    static ArrayList<HotelService> infoResult = new ArrayList<>();
    static ArrayList<HotelService> newService =new ArrayList<>();
    public static String CallReception;
    static Context context;



    public static void initalizeInformation(Context ctx)
    {
        String type = "getService";
        String CurrentLang = Locale.getDefault().getLanguage();
        AASync AASync = new AASync(ctx);
        AASync.execute(type,CurrentLang);
        context = ctx;
        context.registerReceiver(serviceReceiver =new BroadcastReceiver() {
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
                    CallSecrity = infoResult.get(i).securityNumber;
                    CallReception = infoResult.get(i).recNumber;
                    PoolHours = infoResult.get(i).pool;
                    BOOKING_ADDRESS= infoResult.get(i).booking_url;
                    TRIPADVISOR_ADDRESS = infoResult.get(i).tripAdvisor_url;
                    ROOM_SERVICE_CALL = infoResult.get(i).roomServiceCall;
                    MAINTENANCE_CALL= infoResult.get(i).mantenanceCall;
                    DINING_HOURS = infoResult.get(i).diningHour;
                }

                unRegister();
            }


        }, new IntentFilter("serviceIntent"));

    }

    private static void unRegister() {
    context.unregisterReceiver(serviceReceiver);
    }
}
