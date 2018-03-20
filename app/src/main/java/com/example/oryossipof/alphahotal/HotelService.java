package com.example.oryossipof.alphahotal;


public class HotelService {

    public String securityNumber;
    public String recNumber;
    public String booking_url;
    public String tripAdvisor_url;
    public String weather_url;
    public String maps_url;

    public HotelService(String securityNumber,String recNumber, String booking_url,String tripAdvisor_url,String weather_url,String maps_url) {
        this.securityNumber = securityNumber;
        this.recNumber = recNumber;
        this.booking_url = booking_url;
        this.tripAdvisor_url = tripAdvisor_url;
        this.weather_url = weather_url;
        this.maps_url = maps_url;


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


}


