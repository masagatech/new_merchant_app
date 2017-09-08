package com.goyo.parent.forms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.goyo.parent.R;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

public class Task_Add extends AppCompatActivity implements LocationListener {

    private ImageView map;
    private double lat=0.0, lon=0.0;
    private LocationManager locationManager2;
    public Criteria criteria;
    public String bestProvider;
    private Button Btn_Add_Task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__add);

        //action bar
        setTitle(getString(R.string.add_tasks));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //map
        map = (ImageView) findViewById(R.id.map);

        //getting current location
        GetLocation();

        //setting map based on location
        setMap();



        //save button
        Btn_Add_Task=(Button)findViewById(R.id.send_form);
        Btn_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    private void setMap(){
        final String STATIC_MAP_API_ENDPOINT = "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=13&size=640x400&markers=color:red%7C" + lat + "," + lon;

        AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bmp = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet(STATIC_MAP_API_ENDPOINT);

                InputStream in = null;
                try {
                    in = httpclient.execute(request).getEntity().getContent();
                    bmp = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            protected void onPostExecute(Bitmap bmp) {
                if (bmp != null) {
                    map.setImageBitmap(bmp);
                }
            }
        };
        setImageFromUrl.execute();
    }

    private void GetLocation() {

        locationManager2 = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager2.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        criteria = new Criteria();
        bestProvider = String.valueOf(locationManager2.getBestProvider(criteria, true)).toString();
        if ((location == null)) {
            locationManager2.requestLocationUpdates(bestProvider, 1000, 0, this);
        }

        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();

            locationManager2.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lon = location.getLongitude();

        locationManager2.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager2.removeUpdates(this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
