package com.example.administrator.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ShangriDong on 2016/3/17.
 */
public class GetHttpService {
    private final static String TAG = "GetHttp";
    public String mResult = null;
    private URL mServerAddress = null;
    private HttpURLConnection mConnection = null;
    private InputStream mIs = null;

    public String getHttpResult(URL serverAddress) {
        mServerAddress = serverAddress;

        try {
            // build the URL using the latitude & longitude you want to lookup
            // NOTE: I chose XML return format here but you can choose something
            // else
        /*mServerAddress = new URL("http://api.map.baidu.com/geocoder/v2/?ak="
                + BAIDU_API_KEY
                + "&location="
                + latitude
                + ","+ longitude
                +"&output=json&ponIs=1");*/
            //利用HttpURLConnection对象从网络中获取网页数据
            mConnection = (HttpURLConnection) mServerAddress.openConnection();
            mConnection.setRequestMethod("GET");

            //设置连接超时
            mConnection.setReadTimeout(10000);

            mIs = mConnection.getInputStream();   //获取输入流，此时才真正建立链接

            InputStreamReader isr = new InputStreamReader(mIs);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                mResult += inputLine + "\n";
            }
            Log.i(TAG, mResult);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        } finally {
            if (mIs != null) {
                try {
                    mIs.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (mConnection != null) {
                mConnection.disconnect();
            }
        }
        return mResult;
    }
}
