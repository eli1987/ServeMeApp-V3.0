package com.example.oryossipof.alphahotal;


import java.io.Serializable;

public class HotelService implements Serializable {

    public String securityNumber;
    public String recNumber;
    public String booking_url;
    public String tripAdvisor_url;
    public String weather_url;
    public String shabbatHours;
    public String flightHours;
    public String maps_url;
    public String pool;
    public String roomServiceCall;
    public String mantenanceCall;
    public String diningHour;

    public HotelService(String securityNumber,String recNumber, String booking_url,String tripAdvisor_url,String weather_url,String maps_url ,String pool, String shabbatHours ,String flightHours
            ,String roomServiceCall, String mantenanceCall ,String diningHour
    ) {
        this.securityNumber = securityNumber;
        this.recNumber = recNumber;
        this.booking_url = booking_url;
        this.tripAdvisor_url = tripAdvisor_url;
        this.weather_url = weather_url;
        this.maps_url = maps_url;
        this.shabbatHours = shabbatHours;
        this.flightHours = flightHours;
        this.pool = pool;
        this.roomServiceCall = roomServiceCall;
        this.mantenanceCall = mantenanceCall;
        this.diningHour = diningHour;


    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }
    public String getBooking_url() {
        return booking_url;
    }

    public String getRecNumber() {
        return recNumber;
    }

    public String getMaps_url() {
        return maps_url;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public String getTripAdvisor_url() {
        return tripAdvisor_url;
    }

    public String getWeather_url() {
        return weather_url;
    }

    public void setBooking_url(String booking_url) {
        this.booking_url = booking_url;
    }

    public void setMaps_url(String maps_url) {
        this.maps_url = maps_url;
    }

    public void setRecNumber(String recNumber) {
        this.recNumber = recNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public void setTripAdvisor_url(String tripAdvisor_url) {
        this.tripAdvisor_url = tripAdvisor_url;
    }

    public void setWeather_url(String weather_url) {
        this.weather_url = weather_url;
    }



    public void setShabbatHours(String shabbatHours) {
        this.shabbatHours = shabbatHours;
    }

    public String getShabbatHours() {
        return shabbatHours;
    }

    public void setFlightHours(String flightHours) {
        this.flightHours = flightHours;
    }

    public String getFlightHours() {
        return flightHours;
    }
}


