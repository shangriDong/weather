package com.example.administrator.myapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Shangri on 2016/3/14.
 */
public class GetCityServer {
    private final static String TAG = "SHANGRI_GetCityServer";
    private final static String BAIDU_API_KEY = "DCukfNvLWMOkdFNjdx0HIfKq";
    private String localityName = "";
    private URL serverAddress = null;
    private MyLocation myLocation = null;
    private GetHttpService mGetHttpSrvice = null;

    public GetCityServer(){
        myLocation = new MyLocation();
        mGetHttpSrvice = new GetHttpService();
    }

    public String reverseGeocode(double latitude, double longitude) {
        try {
            serverAddress = new URL("http://api.map.baidu.com/geocoder/v2/?ak="
                    + BAIDU_API_KEY
                    + "&location="
                    + latitude
                    + "," + longitude
                    + "&output=json&ponIs=1");
        } catch (MalformedURLException ex) {
            Log.e(TAG, ex.toString());
        }

        localityName = mGetHttpSrvice.getHttpResult(serverAddress);
        Log.i(TAG, localityName);
        try {
            JSONTokener jsonParser = new JSONTokener(localityName);
            // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
            // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
            JSONObject person = (JSONObject) jsonParser.nextValue();
            // 接下来的就是JSON对象的操作了
            myLocation.getRoot().setStatus(person.getInt("status"));
            Log.i(TAG, "status: " + String.valueOf(myLocation.getRoot().getStatus()));

            JSONObject result = person.getJSONObject("result");
            {
                JSONObject location = result.getJSONObject("location");
                Log.i(TAG, "location: " + location);
                myLocation.getResult().setFormatted_address(result.getString("formatted_address"));
                Log.i(TAG, "formatted_address: " + myLocation.getResult().getFormatted_address());

                myLocation.getResult().setBusiness(result.getString("business"));
                Log.i(TAG, "business: " + myLocation.getResult().getBusiness());

                JSONObject addressComponent = result.getJSONObject("addressComponent");
                Log.i(TAG, "addressComponent: " + addressComponent);
                {
                    myLocation.getAddressComponent().setAdcode(addressComponent.getString("adcode"));
                    Log.i(TAG, "adcode: " + myLocation.getAddressComponent().getAdcode());
                    myLocation.getAddressComponent().setCity(addressComponent.getString("city"));
                    Log.i(TAG, "city: " + myLocation.getAddressComponent().getCity());
                    //cityName = city;
                }
            }

        } catch (JSONException ex) {
            Log.e(TAG, ex.toString());
        }
        return myLocation.getAddressComponent().getCity();
    }
}
