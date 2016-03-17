package com.example.administrator.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //获取位置
    private double latitude = 0.0;
    private double longitude = 0.0;
    private static final String TAG = "SHANGRI";
    private static final int UPDATELOCATION = 101;
    private LocationManager locationManager = null;
    private TextView text = null;
    private TextView cityNameText = null;
    private Location location = null;
    private GetCityServer getCityServer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text = (TextView) findViewById(R.id.text2);
        text.setText(TAG);

        cityNameText= (TextView) findViewById(R.id.CityName);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Toast.makeText(getApplicationContext(), "默认Toast样式",
                        Toast.LENGTH_SHORT).show();
            }
        });

        getLocationManager();

        getCityServer = new GetCityServer();

        //new Thread(networkTask).start();
        //reverseGeocode(location);
    }

    private LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "provider status = " + status);
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "LM onProviderEnabled = " + provider);
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "LM onProviderDisabled = " + provider);
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.d(TAG, "Location changed : Lat: "
                        + location.getLatitude() + " Lng: "
                        + location.getLongitude());
                text.setText("经度：" + location.getLatitude() + " 纬度" + location.getLongitude());
                Log.i(TAG, "经度：" + location.getLatitude() + " 纬度" + location.getLongitude());

                new Thread(networkTask).start();
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);

            cityNameText.setText(val);
        }
    };

    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value",
                           getCityServer.reverseGeocode(location.getLatitude(),
                           location.getLongitude()));
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getLocationManager() {
        //locationManager
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        if (null == locationManager) {
            Log.e(TAG, "locationManager = NULL");
            return;
        }

        final int REQUEST_CODE_ASK_PERMISSIONS = 123;

        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                               REQUEST_CODE_ASK_PERMISSIONS);
            Log.e(TAG, "NO PERMISSION");
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.i(TAG, "Provider is GPS_PROVIDER");

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,
                    0,
                    locationListener);

             location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            Log.i(TAG, "Provider is NETWORK_PROVIDER");

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                                   5000,
                                                   0,
                                                   locationListener);

            if(location != null){
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
            }
        } else {
            Log.e(TAG, "无法获取位置！");
            Toast.makeText(getApplicationContext(), "无法获取位置！",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
