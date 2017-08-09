package com.goyo.traveltracker.forms;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.model.map_model;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

public class TodayVisitsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private Button Btn_Date;
    String SelectedDate;
    ArrayList<map_model> data = new ArrayList<map_model>();
    PieView pieView,pieStops,pieTasks;

//    private ArrayList<LatLng> Task = new ArrayList<>();
//    private MarkerOptions TaskMarker = new MarkerOptions();
//    private List<MapData> MapData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_visits_maps);


        pieView = (PieView) findViewById(R.id.pieView);
        pieStops = (PieView) findViewById(R.id.pieStops);
        pieTasks = (PieView) findViewById(R.id.pieTasks);



        //date
        Btn_Date=(Button) findViewById(R.id.Date);
        Btn_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SingleDateAndTimePickerDialog.Builder(TodayVisitsMapsActivity.this)
                        .titleTextColor(Color.WHITE)
                        .backgroundColor(Color.BLACK)
                        .mainColor(Color.parseColor("#ff33b5e5"))
                        .bottomSheet()
                        .curved()
                        .displayHours(false)
                        .displayMinutes(false)
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                SelectedDate = new SimpleDateFormat("dd-MMM-yyyy").format(date);
                                if(mMap != null) {
                                    mMap.clear();
                                    DrawPath(mMap);
                                }
                                }
                        }).display();
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DrawPath(mMap);
    }


    private void DrawPath(GoogleMap googleMap){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        //storing latlong of tasks to draw a path
        if(SelectedDate==null) {
            SelectedDate = formattedDate;
        }

        Btn_Date.setText(SelectedDate);
        SQLBase db = new SQLBase(this);
        List<HashMap<String, String>> d = db.Get_CombinedVisit(SelectedDate);
        data.clear();
        if (d.size() > 0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new map_model(d.get(i).get("title"), d.get(i).get("time"), d.get(i).get("lat"), d.get(i).get("lon"), d.get(i).get("type")));
            }
        }



        latlngs.clear();
//        latlngs.add(new LatLng(19.075984, 72.877656));
//        latlngs.add(new LatLng(19.085819, 72.894877));
//        latlngs.add(new LatLng(19.084907, 72.907323));
//        latlngs.add(new LatLng(19.070610, 72.894748));
//        latlngs.add(new LatLng(19.0997, 72.9164));
//        latlngs.add(new LatLng(18.835374, 73.945370));
//        latlngs.add(new LatLng(18.864616, 74.018841));
//        latlngs.add(new LatLng(18.848046, 74.119778));
//        latlngs.add(new LatLng(18.818476, 74.255733));
//        latlngs.add(new LatLng(18.742416, 74.349117));
//        latlngs.add(new LatLng(18.742416, 74.349117));

        View icon = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custommapicon_1, null);

        if(data.size()>0) {
            //for stops
            int j=1;
            for (int i = 0; i < data.size(); i++) {
                ImageView MapIcon=(ImageView)icon.findViewById(R.id.MapIco);
                TextView numTxt = (TextView) icon.findViewById(R.id.num_txt);

                if((data.get(i).get_Type().equals("task"))) {
                    GradientDrawable backgroundGradient = (GradientDrawable) MapIcon.getBackground();
                    backgroundGradient.setColor(Color.parseColor("#ff33b5e5"));
                }else {
                    GradientDrawable backgroundGradient = (GradientDrawable) MapIcon.getBackground();
                    backgroundGradient.setColor(Color.parseColor("#ffffbb33"));
                }


                numTxt.setText("0"+j);
                latlngs.add(new LatLng(Double.parseDouble(data.get(i).get_lat()), Double.parseDouble(data.get(i).get_lon())));
                options.title(data.get(i).get_body());
                options.snippet(data.get(i).get_title());

                options.position(latlngs.get(i));
                options.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, icon)));
                googleMap.addMarker(options);
                j++;
            }
        }else {
            Toast.makeText(this, "There is no record on selected date! Please choose another date", Toast.LENGTH_SHORT).show();
        }



        //draw path
        if (latlngs.size() >= 2) {
            LatLng origin = latlngs.get(0);
            LatLng dest = latlngs.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngs.get(0), 13));

        }else {

            //km
            pieView.setPercentage(1);
            pieView.setPercentageBackgroundColor(Color.parseColor("#a854d4"));
            pieView.setInnerText("0km");
            pieView.setInnerTextVisibility(View.VISIBLE);
            PieAngleAnimation animation = new PieAngleAnimation(pieView);
            animation.setDuration(1000); //This is the duration of the animation in millis
            pieView.startAnimation(animation);


            //stops
            pieStops.setPercentage(1);
            pieStops.setInnerText("0");
            pieStops.setPercentageBackgroundColor(Color.parseColor("#ffffbb33"));
// Change the color fill of the background of the widget, by default is transparent
//            pieStops.setMainBackgroundColor(getResources().getColor(R.color.customColor5));
            pieStops.setInnerTextVisibility(View.VISIBLE);
            PieAngleAnimation animation2 = new PieAngleAnimation(pieStops);
            animation2.setDuration(1000); //This is the duration of the animation in millis
            pieStops.startAnimation(animation2);


            //tasks
            pieTasks.setPercentage(1);
            pieTasks.setInnerText("0");
            pieTasks.setPercentageBackgroundColor(Color.parseColor("#ff33b5e5"));
            pieTasks.setInnerTextVisibility(View.VISIBLE);
            PieAngleAnimation animation3 = new PieAngleAnimation(pieTasks);
            animation3.setDuration(1000); //This is the duration of the animation in millis
            pieTasks.startAnimation(animation3);
        }
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
        private String getDirectionsUrl(LatLng origin,LatLng dest){

            // Origin of route
            String str_origin = "origin="+origin.latitude+","+origin.longitude;

            // Destination of route
            String str_dest = "destination="+dest.latitude+","+dest.longitude;

            // Sensor enabled
            String sensor = "sensor=false";

            // Waypoints
            String waypoints = "";
            for(int i=2;i<latlngs.size();i++){
                LatLng point  = (LatLng) latlngs.get(i);
                if(i==2)
                    waypoints = "waypoints=";
                waypoints += point.latitude + "," + point.longitude + "|";
            }

            // Building the parameters to the web service
            String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+waypoints;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

            return url;
        }


    /** A method to download json data from url */
        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try{
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            }catch(Exception e){
                Log.d("Exception while", e.toString());
            }finally{
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }

        // Fetches data from url passed
        private class DownloadTask extends AsyncTask<String, Void, String> {

            // Downloading data in non-ui thread
            @Override
            protected String doInBackground(String... url) {

                // For storing data from web service

                String data = "";

                try{
                    // Fetching the data from web service
                    data = downloadUrl(url[0]);
                }catch(Exception e){
                    Log.d("Background Task",e.toString());
                }
                return data;
            }

            // Executes in UI thread, after the execution of
            // doInBackground()
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                ParserTask parserTask = new ParserTask();

                // Invokes the thread for parsing the JSON data
                parserTask.execute(result);
            }
        }

        /** A class to parse the Google Places in JSON format */
        private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {

            // Parsing the data in non-ui thread
            @Override
            protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

                JSONObject jObject;
                List<List<HashMap<String, String>>> routes = null;

                try {
                    jObject = new JSONObject(jsonData[0]);
                    DirectionsJSONParser parser = new DirectionsJSONParser();

                    // Starts parsing data
                    routes = parser.parse(jObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return routes;
            }

            // Executes in UI thread, after the parsing process
            @Override
            protected void onPostExecute(List<List<HashMap<String, String>>> result) {

                ArrayList<LatLng> points = null;
                PolylineOptions lineOptions = null;
                Double distance =0.0;

                // Traversing through all the routes
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        if(point.get("distance")!=null) {
                            distance = distance + Double.parseDouble(point.get("distance"));
                        }
                        if(point.get("lat")!=null) {
                            double lat = Double.parseDouble(point.get("lat"));
                            double lng = Double.parseDouble(point.get("lng"));
                            LatLng position = new LatLng(lat, lng);
                            points.add(position);
                        }




                    }

//                    List<PatternItem> pattern = Arrays.<PatternItem>asList(
//                            new Dot(), new Gap(20), new Dash(30), new Gap(20));
//                    mPolyline.setPattern(pattern);

//                    if(points.get(0)==latlngs.get(0)){
                        // Adding all the points in the route to LineOptions
                        lineOptions.addAll(points);
                        lineOptions.width(7);
                        lineOptions.color(Color.BLACK);
//                    }else {
//                        // Adding all the points in the route to LineOptions
//                        lineOptions.addAll(points);
//                        lineOptions.width(7);
//                        lineOptions.color(Color.GREEN);
//                    }

                }
                Double Km;
                Km= distance/1000;

                if(Km<=10.0) {
                    pieView.setPercentage(10);
                } else if (Km>20.0) {
                    pieView.setPercentage(30);
                } else if (Km>30.0) {
                    pieView.setPercentage(50);
                }else if (Km>50.0){
                    pieView.setPercentage(70);
                }else if (Km>70.0){
                    pieView.setPercentage(88);
                }

                pieView.setInnerText(Km+"km");
                pieView.setPercentageBackgroundColor(Color.parseColor("#a854d4"));
                pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation = new PieAngleAnimation(pieView);
                animation.setDuration(900); //This is the duration of the animation in millis
                pieView.startAnimation(animation);


                int stops=0,tasks=0;
                if(data.size()>0) {
                    for (int i = 0; i < data.size(); i++) {

                        if ((data.get(i).get_Type().equals("task"))) {
                            tasks++;
                        } else {
                            stops++;
                        }
                    }
                }


                //stops
                        if(stops<=2) {
                            pieStops.setPercentage(10);
                        } else if (stops>3) {
                            pieStops.setPercentage(30);
                        } else if (stops>4) {
                            pieStops.setPercentage(50);
                        }else if (stops>6){
                            pieStops.setPercentage(70);
                        }else if (stops>7){
                            pieStops.setPercentage(88);
                        }
                pieStops.setInnerText(stops+"");
                pieStops.setPercentageBackgroundColor(Color.parseColor("#ffffbb33"));
// Change the color fill of the background of the widget, by default is transparent
//            pieStops.setMainBackgroundColor(getResources().getColor(R.color.customColor5));
                pieStops.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation2 = new PieAngleAnimation(pieStops);
                animation2.setDuration(900); //This is the duration of the animation in millis
                pieStops.startAnimation(animation2);




                //tasks
                        if(tasks<=2) {
                            pieTasks.setPercentage(10);
                        } else if (tasks>3) {
                            pieTasks.setPercentage(30);
                        } else if (tasks>4) {
                            pieTasks.setPercentage(50);
                        }else if (tasks>6){
                            pieTasks.setPercentage(70);
                        }else if (tasks>7){
                            pieTasks.setPercentage(88);
                        }
                pieTasks.setInnerText(tasks+"");
                pieTasks.setPercentageBackgroundColor(Color.parseColor("#ff33b5e5"));
                pieTasks.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation3 = new PieAngleAnimation(pieTasks);
                animation3.setDuration(900); //This is the duration of the animation in millis
                pieTasks.startAnimation(animation3);

            // Change the text of the widget



                // Drawing polyline in the Google Map for the i-th route
                if(data.size()>0) {
                    if (lineOptions != null) {
                        mMap.addPolyline(lineOptions);
                    }
                }
            }
        }

}
