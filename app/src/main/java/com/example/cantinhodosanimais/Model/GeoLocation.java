package com.example.cantinhodosanimais.Model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocation {
    private  double latitude;
    private  double longitude;

    /**
     *  Classe's Constructor
     *  To create this classe's objects
     * @param latitude
     * @param longitude
     */
    public GeoLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * To allow access of latitude of the indicated address in other classes
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * To allow access of longitude of the indicated address in other classes
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }


    /**
     * After receiving an address, this method converts it into latitude and longitude
     * @param locationAddress
     * @param context
     */
    public void getAddress(String locationAddress, final Context context){

        Thread thread = new Thread() {
            @Override
            public void run() {

                Geocoder geocoder = new Geocoder (context, Locale.getDefault());

                try {

                    List addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList !=null && addressList.size() > 0) {

                        Address address = (Address) addressList.get(0);
                        latitude = address.getLatitude();
                        longitude =address.getLongitude();
                    }

                }   catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}

