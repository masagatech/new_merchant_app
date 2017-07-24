package com.goyo.traveltracker.forms;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_tag_db;
import com.goyo.traveltracker.model.model_task;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.pchmn.materialchips.ChipsInput;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static com.goyo.traveltracker.Service.NetworkStateReceiver.IsMobailConnected;
import static com.goyo.traveltracker.forms.dashboard.REQUEST_CHECK_SETTINGS;
import static com.goyo.traveltracker.forms.dashboard.mGoogleApiClient;
import static com.goyo.traveltracker.forms.pending_order.TripId;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewStops extends AAH_FabulousFragment implements LocationListener{

    private ImageView map;
    private EditText remark,remark_title;
    private double lat=0.0, lon=0.0;
    private LocationManager locationManager2;
    public Criteria criteria;
    private  Location location;
    public String bestProvider, currentDateTimeString,Empl_Id;
    String Body,Title,Lat,Lon;
    private Button Btn_Add_Task;
    private ImageView imageView1,imageView2,imageView3;
    private TextView textView1;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private ChipsInput chipsInput;


    public static  NewStops newInstance() {
        NewStops f = new NewStops();
        return f;
        // Required empty public constructor
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {

        settingsrequest();
        View contentView = View.inflate(getContext(), R.layout.activity_task__add, null);
        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);


        SQLBase db = new SQLBase(getActivity());

        List<model_tag_db> data = new ArrayList<model_tag_db>();
        List<HashMap<String,String>> d = db.Get_Tags();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_tag_db( d.get(i).get(Tables.tbltags.Tag_Id), d.get(i).get(Tables.tbltags.Tag_Title),d.get(i).get(Tables.tbltags.Tag_remark_1),d.get(i).get(Tables.tbltags.Tag_remark_2),d.get(i).get(Tables.tbltags.Tag_remark_3),d.get(i).get(Tables.tbltags.Tag_Creat_On),d.get(i).get(Tables.tbltags.Is_Server_Send)));
            }
        }

        chipsInput = (ChipsInput) contentView.findViewById(R.id.chip);
        chipsInput.setFilterableList(data);





//        KMPAutoComplTextView complTextView = (KMPAutoComplTextView) contentView.findViewById(R.id.tvAutoCompl);
//        complTextView.setDatas(data);
//        complTextView.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener() {
//            @Override
//            public void onPopupItemClick(CharSequence charSequence) {
//                Toast.makeText(getActivity(), charSequence.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //map
        map = (ImageView) contentView.findViewById(R.id.map);

        //getting current location
        GetLocation();

        //setting map based on location
        setMap();

        remark = (EditText) contentView.findViewById(R.id.Task_Body);
        remark_title = (EditText) contentView.findViewById(R.id.Task_Title);

//        final SQLBase db = new SQLBase(getActivity());
        //final List<model_task> task = db.getAllContacts();
//        for (model_task cn : task) {
//            String log = "Id: "+cn.get_id()+" ,Title: " + cn.get_title() + " ,Body: " + cn.get_body()+ " ,Lat: " + cn.get_lat()+ " ,Lon: " + cn.get_lon()+ " ,EMP ID: " + cn.get_empl_id()+ " ,Created On: " + cn.get_creat_on()+ " ,Is Server Send: " + cn.get_is_server_send();
//            // Writing Contacts to log
//            Log.e("Name: ", log);
//            Toast.makeText(getActivity(), log, Toast.LENGTH_SHORT).show();
//        }


        //image
        imageView1=(ImageView)contentView.findViewById(R.id.imageView);
        imageView2=(ImageView)contentView.findViewById(R.id.imageView2);
        imageView3=(ImageView)contentView.findViewById(R.id.imageView3);
        textView1=(TextView) contentView.findViewById(R.id.textView11);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


        Btn_Add_Task = (Button) contentView.findViewById(R.id.send_form);
        Btn_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!IsConnected){
                    currentDateTimeString = DateFormat.getDateInstance().format(new Date());
                    Empl_Id= String.valueOf(Global.loginusr.getDriverid());
                    Title=remark_title.getText().toString();
                    Body=remark.getText().toString();
                    Lat=String.valueOf(lat);
                    Lon=String.valueOf(lon);
                if(Title.equals("")){
                    Toast.makeText(getActivity(), "Please Enter Info!", Toast.LENGTH_SHORT).show();
                }else {
                    List<model_tag_db> contactsSelected = (List<model_tag_db>) chipsInput.getSelectedChipList();
                    List<String> Tags = new ArrayList<String>();
                    if(contactsSelected.size()>0) {
                        for (int i = 0; i <= contactsSelected.size() - 1; i++) {
                            Tags.add(contactsSelected.get(i).getLabel());
                        }
                    }
                    SQLBase db = new SQLBase(getActivity());
                SendToServer(Empl_Id,Title,Body,Lat,Lon,currentDateTimeString,Tags);

                    Gson gson = new Gson();
                    String TagString= gson.toJson(Tags);
                db.OFFLINE_TASK_ADDTASK(new model_task(Title,Body,Lat,Lon,TagString,currentDateTimeString,"0"));

                }


            }
        });

        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
//        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setViewgroupStatic(Btn_Add_Task); // optional; layout to stick at bottom on slide
//        setViewPager(vp_types); //optional; if you use viewpager that has scrollview
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }
private void SendToServer(String Empl_Id,String Title,String Body,String Lat,String Lon,String currentDateTimeString,List<String> Tags) {
    String tag= Joiner.on("','").join(Tags);
    JsonObject json = new JsonObject();
    json.addProperty("stpnm", Title);
    json.addProperty("stpdesc", Body);
    json.addProperty("lat", Lat);
    json.addProperty("lng", Lon);
    json.addProperty("trpid", TripId);
    json.addProperty("uid", Empl_Id);
    json.addProperty("cuid", currentDateTimeString);
    json.addProperty("enttid", Global.loginusr.getEnttid()+"");
    json.addProperty("tag","{'" + tag + "'}");

    Ion.with(this)
            .load(Global.urls.saveTripStops.value)
            .setJsonObjectBody(json)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    // do stuff with the result or error
                    try {
                        JsonObject o= result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("funsave_tripstops").getAsJsonObject();
                        Toast.makeText(getActivity(), o.get("msg").toString(), Toast.LENGTH_SHORT).show();
                        closeFilter("closed");

                    } catch (Exception ea) {
                        ea.printStackTrace();
                    }


                }
            });
}

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
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

        locationManager2 = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if(IsMobailConnected) {
          location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }else {
           location = locationManager2.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
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

    public void settingsrequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
                        settingsrequest();//keep asking if imp or do whatever
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    textView1.setVisibility(View.GONE);
                    imageView1.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            textView1.setVisibility(View.GONE);
            imageView1.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
