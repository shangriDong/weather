package com.example.administrator.myapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shangri on 2016/3/14.
 */
public class GetCityServer {
    private final static String TAG = "SHANGRI_GetCityServer";
    private final String BAIDU_API_KEY = "DCukfNvLWMOkdFNjdx0HIfKq";
    private String localityName = "";
    private String cityName = "";
    private HttpURLConnection connection = null;
    private URL serverAddress = null;
    private InputStream is = null;
    private MyLocation myLocation = null;

    public GetCityServer(){
        myLocation = new MyLocation();
    }

    public String reverseGeocode(double latitude, double longitude) {
        try {
            // build the URL using the latitude & longitude you want to lookup
            // NOTE: I chose XML return format here but you can choose something
            // else
            serverAddress = new URL("http://api.map.baidu.com/geocoder/v2/?ak="
                                    + BAIDU_API_KEY
                                    + "&location="
                                    + latitude
                                    + ","+ longitude
                                    +"&output=json&pois=1");
            //利用HttpURLConnection对象从网络中获取网页数据
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("GET");
            //设置容许输出
            //connection.setDoOutput(true);
            //设置连接超时
            connection.setReadTimeout(10000);

            is = connection.getInputStream();   //获取输入流，此时才真正建立链接

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine  = "";
            while((inputLine = bufferReader.readLine()) != null){
                localityName += inputLine + "\n";
            }
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
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        return myLocation.getAddressComponent().getCity();
    }
}
