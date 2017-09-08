package com.goyo.parent.forms;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.gson.reflect.TypeToken;
import com.goyo.parent.R;
import com.goyo.parent.Service.RiderStatus;
import com.goyo.parent.common.Checker;
import com.goyo.parent.database.SQLBase;
import com.goyo.parent.database.Tables;
import com.goyo.parent.gloabls.Global;
import com.goyo.parent.initials.splash_screen;
import com.goyo.parent.model.model_loginusr;
import com.goyo.parent.utils.SHP;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.goyo.parent.Service.RiderStatus.handler;
import static com.goyo.parent.gloabls.Global.urls.getEmpStatus;
import static com.goyo.parent.gloabls.Global.urls.mobileupload;

public class dashboard extends AppCompatActivity {

    @BindView(R.id.Pending_Order)
    FrameLayout Pending_Order;
    @BindView(R.id.All_Order_details)
    FrameLayout OrderDetails;
    @BindView(R.id.pushOrder)
    FrameLayout Push_Order;
    @BindView(R.id.Complated_Orders)
    FrameLayout Complated_Orders;
    @BindView(R.id.Rejected_Orders)
    FrameLayout Rejected_Orders;
    @BindView(R.id.Cash_Collection)
    FrameLayout Cash_Collection;
    @BindView(R.id.Notifications)
    FrameLayout Notifications;
    @BindView(R.id.All_Order)
    FrameLayout All_Order;
    @BindView(R.id.Holyday)
    FrameLayout Holyday;

    private PopupWindow OrderPopup;
    private Button Btn_Accept, Btn_Reject;
    private FloatingActionButton Flot_add_Task,Map;
    private TextView Deliver_at_Text;
    private TextView PopUp_CountText;
    private TextView Online, RiderName;
    private ImageButton Logout;
    private SwitchCompat RiderStatusSwitch;
    Intent mServiceIntent;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static GoogleApiClient mGoogleApiClient;
    private String latitude, longitude;
    public Criteria criteria;
    public String bestProvider;
    LocationManager locationManager2;
   private int Pending_element;
    public static String TripId;
//    final Popup_Counter CountTimer = new Popup_Counter(180000, 1000);
    private NotificationManager notificationManager;
    TextView Count_Pending,Count_TodayVisits;
    private Boolean isCallLogout=false;
    private LinearLayout PushOrderTL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerCrashReport();

        setContentView(R.layout.activity_dashboard);

        //checking app version
        AppVerCheck();

        //getting Last TripId
        TripId=PreferenceManager.getDefaultSharedPreferences(dashboard.this).getString("tripid", "0");


        //counts
        Count_Pending=(TextView)findViewById(R.id.Count);
        Count_TodayVisits=(TextView)findViewById(R.id.Count_Todayvisit);
        ButterKnife.bind(this);

        //setting cutom actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.rider_online_switch);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);

        //Push Order visible only to TL
//        PushOrderTL=(LinearLayout)findViewById(R.id.PushOrderTL);
//        if(Global.loginusr.getType().equals("TL")){
//            PushOrderTL.setVisibility(View.VISIBLE);
//        }

        //sending info for first time when app  is opened
//        getStatus();


//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this).build();
//        mGoogleApiClient.connect();

        //Check if GPS on in user phone, If not prompt them on
//        settingsrequest();


        //custem image
//        Bitmap bm = BitmapFactory.decodeResource( getResources(), R.drawable.custum);
//        String extStorageDirectory = this.getApplicationInfo().dataDir.toString();
//        File file = new File(extStorageDirectory, "default_image.PNG");
//        OutputStream outStream = null;
//        try {
//            outStream = new FileOutputStream(file);
//            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//            outStream.flush();
//            outStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        //cgetting current location

//        locationManager2 = (LocationManager) this.getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Location location = locationManager2.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        location = locationManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        criteria = new Criteria();
//        bestProvider = String.valueOf(locationManager2.getBestProvider(criteria, true)).toString();
//        if ((location == null)) {
//            locationManager2.requestLocationUpdates(bestProvider, 1000, 0, this);
//        }
//
//        if (location != null) {
//            latitude = String.valueOf(location.getLatitude());
//            longitude = String.valueOf(location.getLongitude());
//        }



        RiderStatusSwitch = (SwitchCompat) findViewById(R.id.compatSwitch);
        Online = (TextView) findViewById(R.id.online);
        RiderName = (TextView) findViewById(R.id.nameRider);
//        Logout = (ImageButton) findViewById(R.id.Logout);


        //New tasks
//        Flot_add_Task=(FloatingActionButton)findViewById(R.id.add_task) ;
//        Flot_add_Task.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent=new Intent(dashboard.this,Task_Add.class);
////                startActivity(intent);
//
//                if(TripId.equals("0")){
//                    Toast.makeText(dashboard.this, "Please Time-in First", Toast.LENGTH_SHORT).show();
//                }else {
//                    NewStops dialogFrag = NewStops.newInstance();
//                    dialogFrag.setParentFab(Flot_add_Task);
//                    dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
//                }
//
//            }
//        });


        //map
//        Map=(FloatingActionButton) findViewById(R.id.Map);
//        Map.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View v) {
//                                       Intent i = new Intent(dashboard.this, TodayVisitsMapsActivity.class);
//                                       startActivity(i);
//                                   }
//                               });




        //showing rider name in actionbar
        RiderName.setText("     "+Global.loginusr.getFullname());

//
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String LogoutMessage = getString(R.string.logout_msg_with_online);
//                if (Global.isOnline) {
//                    isCallLogout = true;
//
//                }
//                else {
//                    LogoutMessage = getString(R.string.logout_msg_normal);
//                    isCallLogout = false;
//                }
//                new AlertDialog.Builder(dashboard.this)
//                        .setTitle(getResources().getString(R.string.logout))
//                        .setMessage(LogoutMessage)
//                        .setPositiveButton(R.string.alert_ok_text, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                if(isCallLogout==false)
//                                    //calling logout api
//                                    Logout();
//                                else
//                                    //turning switch off
//                                    SwitchTurnedOnOFF("false");
//                            }
//                        })
//                        .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                isCallLogout=false;
//                            }
//                        })
//                        .setCancelable(false)
//                        .setIcon(android.R.drawable.ic_lock_lock).show();
//            }
//        });

//
//        RiderStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Global.isOnline=isChecked;
//                if (isChecked) {
//                    if (isStatusDbCheck) {
//                        return;
//                    }
//
//                    //showing alert message
//                    new AlertDialog.Builder(dashboard.this)
//                            .setTitle(R.string.online_head)
//                            .setMessage(getResources().getString(R.string.online_body))
//                            .setPositiveButton(getResources().getString(R.string.online_yes), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //Getting JSON data from server
//                                    SwitchTurnedOnOFF("true");
//                                }
//                            })
//                            .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    isStatusDbCheck = true;
//                                    RiderStatusSwitch.setChecked(false);
//                                    isStatusDbCheck = false;
//
//                                }
//                            })
//                            .setCancelable(false)
//                            .setIcon(R.drawable.rider_del).show();
//
//                } else {
//                    if (isStatusDbCheck) {
//                        return;
//                    }
//                    //showing alert message
//                    new AlertDialog.Builder(dashboard.this)
//                            .setTitle(getResources().getString(R.string.offline_head))
//                            .setMessage(R.string.offline_body)
//                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //calling api
//                                    SwitchTurnedOnOFF("false");
//                                }
//                            })
//                            .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    isStatusDbCheck = true;
//                                    RiderStatusSwitch.setChecked(true);
//                                    isStatusDbCheck = false;
//                                }
//                            })
//                            .setCancelable(false)
//                            .setIcon(R.drawable.rider_del).show();
//
//                }
//            }
//        });
//

    }


    private void  Logout(){

        String sessionid = SHP.get(dashboard.this, SHP.ids.sessionid, "-1").toString();
        String uid = SHP.get(dashboard.this, SHP.ids.uid, "-1").toString();
        JsonObject json = new JsonObject();
        json.addProperty("sessionid", sessionid);
        json.addProperty("email", uid);
        json.addProperty("flag", "rider");
        Ion.with(dashboard.this)
                .load(Global.urls.getlogout.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null)
                                Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_loginusr>>() {
                            }.getType();
                            List<model_loginusr> login = (List<model_loginusr>) gson.fromJson(result.get("data"), listType);
                            if (login.size() > 0) {
                                model_loginusr m = login.get(0);
                                if (m.getStatus() == 1) {
                                    SHP.set(dashboard.this, SHP.ids.uid, "");
                                    SHP.set(dashboard.this, SHP.ids.sessionid, "");
                                    Intent i = new Intent(dashboard.this, com.goyo.parent.initials.login.class);
                                    startActivity(i);
                                    dashboard.this.finish();
                                } else {
                                    Toast.makeText(dashboard.this, "Faild to logout " + m.getErrcode() + " " + m.getErrmsg(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(dashboard.this, "Oops there is some issue! please logout later!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ea) {
                            Toast.makeText(dashboard.this, "Oops there is some issue! Error: " + ea.getMessage(), Toast.LENGTH_LONG).show();
                            ea.printStackTrace();
                        }
//                                                Global.hideProgress(loader);
                    }
                });
    }

    //adding user name,Hotspot ID and Rider ID to fabric crash
    public static void registerCrashReport() {
        // TODO: Use the current user's information
        Crashlytics.setUserIdentifier("Hotspot ID: "+Global.loginusr.getHsid() + "");
        Crashlytics.setUserEmail("Name: "+Global.loginusr.getFullname() + "");
        Crashlytics.setUserName("Rider ID: " +Global.loginusr.getDriverid() + "");
    }



    boolean isStatusDbCheck = false;

    private void getStatus() {
        Ion.with(this)
                .load("GET", getEmpStatus.value)
                .addQuery("flag", "getchkinout")
                .addQuery("uid", Global.loginusr.getDriverid() + "")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
//                            JsonObject o = result.get("data").getAsJsonObject();
                            JsonObject o= result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("funget_api_getuserstate").getAsJsonObject();
                            boolean avail = o.get("state").getAsBoolean();
                            TripId=o.get("tripid").getAsString();
                            isStatusDbCheck = true;
                            RiderStatusSwitch.setChecked(avail);
                            isStatusDbCheck = false;
                            if (avail){
//                                showNotification();
                                Online.setText(R.string.switch_online_text);
                                SwitchTurnedOnOFF("true");
                            }
                            Global.isOnline=avail;
                            StatusRider();
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, splash_screen.class), 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Online!")
                .setSmallIcon(R.drawable.rider)
                .setContentTitle("Your Online!")
                .setContentIntent(pi)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


    private void SwitchTurnedOnOFF(final String state) {
//        Global.showProgress(loader);
        if (latitude == null) {
            latitude = "0.0";
            longitude = "0.0";
        }
        JsonObject json = new JsonObject();
        json.addProperty("tripid", TripId);
        json.addProperty("uid", Global.loginusr.getDriverid());
        json.addProperty("lat", latitude);
        json.addProperty("lon", longitude);
        json.addProperty("enttid", Global.loginusr.getEnttid());
        json.addProperty("btr", getBatteryLevel() + "");
        Ion.with(this)
                .load((state == "true" ? Global.urls.starttripswitch.value :Global.urls.stoptripswitch.value))
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        // do stuff with the result or error
                        try {
                            String Status = (result.get("data").getAsJsonObject()).get("resstatus").toString();
                            if (state == "true") {
                                if (result != null) Log.v("result", result.toString());
                                if (Boolean.parseBoolean(Status)) {
                                    TripId = (result.get("data").getAsJsonObject()).get("tripid").toString();
                                    getDefaultSharedPreferences(dashboard.this).edit().putString("tripid",TripId ).apply();
//                                    Toast.makeText(dashboard.this, (result.get("data").getAsJsonObject()).get("resmessage").toString(), Toast.LENGTH_LONG).show();
                                    if (!isMyServiceRunning(RiderStatus.class)) {
                                        //Notification
//                                        showNotification();
                                        Online.setText(R.string.switch_online_text);
                                        mServiceIntent = new Intent(dashboard.this, RiderStatus.class);
                                        dashboard.this.startService(mServiceIntent);
                                    }
                                } else {
                                    Toast.makeText(dashboard.this, (result.get("data").getAsJsonObject()).get("resmessage").toString(), Toast.LENGTH_LONG).show();
                                    isStatusDbCheck=true;
                                    RiderStatusSwitch.setChecked(false);
                                    isStatusDbCheck=false;
                            }
                        } else {
                            if (Boolean.parseBoolean(Status)) {
//                                    notificationManager.cancel(0);
                                Online.setText(R.string.offline_switch_text);
                                handler.removeMessages(0);
                                if (isMyServiceRunning(RiderStatus.class)) {
                                    if (mServiceIntent != null) stopService(mServiceIntent);
                                    else
                                        stopService(new Intent(dashboard.this, RiderStatus.class));
                                }
                                TripId="0";
                                getDefaultSharedPreferences(dashboard.this).edit().putString("tripid",TripId ).apply();
                                if(isCallLogout){
                                    Logout();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), result.get("data").getAsJsonObject().get("msg").toString(), Toast.LENGTH_SHORT).show();
                                isStatusDbCheck=true;
                                RiderStatusSwitch.setChecked(true);
                                isStatusDbCheck=false;

                            }
                        }
                        } catch (Exception ea) {
                            if (state == "true") {
                                isStatusDbCheck = true;
                                RiderStatusSwitch.setChecked(false);
                                isStatusDbCheck = false;
                            }else {
                                isStatusDbCheck = true;
                                RiderStatusSwitch.setChecked(true);
                                isStatusDbCheck = false;
                            }
                            new Checker(dashboard.this).pass(new Checker.Pass() {
                                @Override
                                public void pass() {

                                }

                            }).check(Checker.Resource.NETWORK);
                            Toast.makeText(dashboard.this, "There was an error", Toast.LENGTH_SHORT).show();
                            ea.printStackTrace();
                        }
//                        Global.hideProgress(loader);
                    }
                });

//
//        Ion.with(this)
//                .load("GET", Global.urls.saveLiveBeat.value)
//                .addQuery("flag", "avail")
//                .addQuery("uid", Global.loginusr.getDriverid() + "")
//                .addQuery("lat", latitude)
//                .addQuery("lon", longitude)
//                .addQuery("onoff", state)
//                .addQuery("tripid", TripId)
////                .addQuery("hs_id", Global.loginusr.getHsid())
//                .addQuery("btr", getBatteryLevel() + "")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                            if (state == "true") {
//                                if (result.get("data").getAsJsonObject().get("status").getAsBoolean()) {
//                                    JsonObject Data=  result.get("data").getAsJsonObject();
//                                        TripId=Data.get("tripid").toString();
//                                        Toast.makeText(getApplicationContext(),Data.get("resmessage").toString()
//                                                ,Toast.LENGTH_SHORT).show();
//
//                                    //Starting Location service and running background
//
//                                    if (!isMyServiceRunning(RiderStatus.class)) {
//                                        //Notification
////                                        showNotification();
//                                        Online.setText(R.string.switch_online_text);
//                                        mServiceIntent = new Intent(dashboard.this, RiderStatus.class);
//                                        dashboard.this.startService(mServiceIntent);
//                                    }
//
//                                } else {
//                                    Toast.makeText(getApplicationContext(), result.get("data").getAsJsonObject().get("msg").toString(), Toast.LENGTH_SHORT).show();
//                                   isStatusDbCheck=true;
//                                    RiderStatusSwitch.setChecked(false);
//                                    isStatusDbCheck=false;
//
//                                }
//                            } else {
//                                if (result.get("data").getAsJsonObject().get("status").getAsBoolean()) {
////                                    notificationManager.cancel(0);
//                                    Online.setText(R.string.offline_switch_text);
//                                    handler.removeMessages(0);
//                                    if (isMyServiceRunning(RiderStatus.class)) {
//                                        if (mServiceIntent != null) stopService(mServiceIntent);
//                                        else
//                                            stopService(new Intent(dashboard.this, RiderStatus.class));
//                                    }
//                                    if(isCallLogout){
//                                        Logout();
//                                    }
//                                } else {
//
//                                    Toast.makeText(getApplicationContext(), result.get("data").getAsJsonObject().get("msg").toString(), Toast.LENGTH_SHORT).show();
//                                    isStatusDbCheck=true;
//                                    RiderStatusSwitch.setChecked(true);
//                                    isStatusDbCheck=false;
//
//                                }
//                            }
//                        } catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
//                    }
//                });
    }

    public void SendOfflineStopstoServer() {
        SQLBase db = new SQLBase(this);
        final List<HashMap<String,String>> d = db. Get_Tasks_Offline();

        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> TagsArray = gson.fromJson(d.get(i).get(Tables.tblofflinetask.Task_Tags), type);


                String tag= Joiner.on(",").join(TagsArray);
                final int pos=i;

                Ion.with(this)
                        .load(mobileupload.value)
                        .setMultipartParameter("enttid", Global.loginusr.getEnttid()+"")
                        .setMultipartParameter("uid",  Global.loginusr.getDriverid()+"")
                        .setMultipartParameter("stpnm",  d.get(i).get(Tables.tblofflinetask.Task_Title))
                        .setMultipartParameter("stpdesc", d.get(i).get(Tables.tblofflinetask.Task_Body))
                        .setMultipartParameter("lat", d.get(i).get(Tables.tblofflinetask.Task_Lat))
                        .setMultipartParameter("lng", d.get(i).get(Tables.tblofflinetask.Task_Lon))
                        .setMultipartParameter("trpid", TripId)
                        .setMultipartParameter("cuid", d.get(i).get(Tables.tblofflinetask.Task_Creat_On)+", "+d.get(i).get(Tables.tblofflinetask.Task_Time))
                        .setMultipartParameter("tag","{" + tag + "}")
                        .setMultipartParameter("expid", d.get(i).get(Tables.tblofflinetask.EXP_ID))
                        .setMultipartParameter("expval",  d.get(i).get(Tables.tblofflinetask.EXP_Value))
                        .setMultipartParameter("expdesc",  d.get(i).get(Tables.tblofflinetask.EXP_Disc))
                        .setMultipartFile("uploadimg", new File( d.get(i).get(Tables.tblofflinetask.Task_Images_Paths)))
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
//               String tag= Joiner.on("','").join(TagsArray);
//                JsonObject json = new JsonObject();
//                json.addProperty("stpnm", d.get(i).get(Tables.tblofflinetask.Task_Title));
//                json.addProperty("stpdesc", d.get(i).get(Tables.tblofflinetask.Task_Body));
//                json.addProperty("lat", d.get(i).get(Tables.tblofflinetask.Task_Lat));
//                json.addProperty("lng", d.get(i).get(Tables.tblofflinetask.Task_Lon));
//                json.addProperty("uid", Global.loginusr.getDriverid()+"");
//                json.addProperty("cuid",d.get(i).get(Tables.tblofflinetask.Task_Creat_On)+", "+d.get(i).get(Tables.tblofflinetask.Task_Time) );
//                json.addProperty("enttid", Global.loginusr.getEnttid()+"");
//                json.addProperty("trpid",TripId);
//                json.addProperty("tag","{'" + tag + "'}");
//                Ion.with(this)
//                        .load(Global.urls.saveTagInfo.value)
//                        .setJsonObjectBody(json)
//                        .asJsonObject()
//                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                // do stuff with the result or error
                                try {
//                                    Toast.makeText(dashboard.this,"Success!", Toast.LENGTH_SHORT).show();
                                    SQLBase db = new SQLBase(dashboard.this);
                                    db. OFFLINE_TASK_UPDATE(d.get(pos).get(Tables.tblofflinetask.Task_Title),"0");

                                } catch (Exception ea) {
                                    ea.printStackTrace();
                                }


                            }
                        });
            }
        }

//        return data;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public float getBatteryLevel() {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but added just in case.
        if (level == -1 || scale == -1) {
            return 50.0f;
        }

        return ((float) level / (float) scale) * 100.0f;
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
                            status.startResolutionForResult(dashboard.this, REQUEST_CHECK_SETTINGS);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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


    @OnClick(R.id.Pending_Order)
    void click() {
        Intent intent = new Intent(this, pending_order.class);
        startActivity(intent);
    }

    @OnClick(R.id.Cash_Collection)
    void click2() {
    Intent intent = new Intent(this, rejected_order.class);
        startActivity(intent);

    }

    @OnClick(R.id.Complated_Orders)
    void click3() {
        Intent intent = new Intent(this, complated_order.class);
        startActivity(intent);
    }

    @OnClick(R.id.Rejected_Orders)
    void click4() {
        Intent intent = new Intent(this, Leave.class);
        startActivity(intent);
    }

    @OnClick(R.id.All_Order)
    void click5() {
        Intent intent = new Intent(this, all_order.class);
        startActivity(intent);
//        Intent intent = new Intent(this, About_us.class);
//        startActivity(intent);
    }

    @OnClick(R.id.Notifications)
    void click6() {
        int flag = 1;
        Intent intent = new Intent(this, newOrder.class);
        intent.putExtra("FromDashboard", flag);
        startActivity(intent);
    }

    @OnClick(R.id.pushOrder)
    void click7() {
        Intent intent = new Intent(this, PushOrder.class);
        startActivity(intent);
    }
    @OnClick(R.id.All_Order_details)
    void click8() {
        Intent intent = new Intent(this, AllOrderDetails.class);
        startActivity(intent);
    }
    @OnClick(R.id.Holyday)
    void click9() {
        Intent intent = new Intent(this, Holyday.class);
        startActivity(intent);
    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        latitude = String.valueOf(location.getLatitude());
//        longitude = String.valueOf(location.getLongitude());
//
//        locationManager2.removeUpdates(this);
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }


    @Override
    protected void onDestroy() {

        // Unregister since the activity is about to be closed.
        super.onDestroy();
    }


//    public class Popup_Counter extends CountDownTimer {
//
//        public Popup_Counter(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onFinish() {
//            OrderPopup.dismiss();
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            PopUp_CountText.setText("" + String.format("%02d:%02d",
//                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
//                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
//        }
//    }

    private void AppVerCheck() {
        try {
            Integer VersionCode = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode;
            if (Global.loginusr.getAppver() > 0) {
                if (VersionCode < Global.loginusr.getAppver()) {
                    new AlertDialog.Builder(dashboard.this)
                            .setTitle(R.string.update_head)
                            .setCancelable(false)
                            .setMessage(R.string.update_body)
                            .setPositiveButton(R.string.update_yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.goyo.goyorider")));
                                    } catch (ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.goyo.goyorider")));
                                    }
                                }
                            })
                            .setNegativeButton(R.string.update_no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    startActivity(intent);
                                }
                            })
                            .show();

                }
            }
            //set config
            Global.loadConfig(Global.loginusr.getConf());

            } catch(PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
    }

    public void refreshMyData(){
        // do your operations here.
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void PendingCountOnCheck() {
//        if (Pending_element >= 0) {
//            Ion.with(this)
//                    .load("GET", getOrders.value)
//                    .addQuery("flag", "orders")
//                    .addQuery("subflag", "count")
//                    .addQuery("rdid", Global.loginusr.getDriverid() + "")
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//
//                            try {
//
//                                if (result != null) Log.v("result", result.toString());
//                                Pending_element = result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("count").getAsInt();
//                                Count_Pending.setText(Pending_element + "");
//                                Count_Pending.setVisibility(View.VISIBLE);
//                            } catch (Exception ea) {
//                                ea.printStackTrace();
//                            }
//                        }
//                    });
//        }
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c.getTime());
//
//        SQLBase db = new SQLBase(this);
//        List<HashMap<String,String>> Stops = db.Get_Today_Stops(formattedDate);
//        int StopCount=Stops.size();
//
//        List<HashMap<String,String>> Task = db.Get_Today_Task(formattedDate);
//        int TaskCount=Task.size();
//
//        Count_TodayVisits.setText(StopCount+"");
//        Count_Pending.setText(TaskCount+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.about_us:
                Intent intent = new Intent(this, About_us.class);
                startActivity(intent);
                return true;
            case R.id.logout:

                String LogoutMessage = getString(R.string.logout_msg_with_online);
                if (Global.isOnline) {
                    isCallLogout = true;

                }
                else {
                    LogoutMessage = getString(R.string.logout_msg_normal);
                    isCallLogout = false;
                }
                new AlertDialog.Builder(dashboard.this)
                        .setTitle(getResources().getString(R.string.logout))
                        .setMessage(LogoutMessage)
                        .setPositiveButton(R.string.alert_ok_text, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(isCallLogout==false)
                                    //calling logout api
                                    Logout();
                                else
                                    //turning switch off
                                    SwitchTurnedOnOFF("false");
                            }
                        })
                        .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                isCallLogout=false;
                            }
                        })
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_lock_lock).show();

                return true;
            case R.id.contact_us:
                Intent intent2 = new Intent(this, ContactDashBoard.class);
                startActivity(intent2);
                return true;

            case R.id.my_profile:
                Intent intent3 = new Intent(this, Profile_Page.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void StatusRider(){
        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
        editor.putBoolean("status", Global.isOnline);
        editor.apply();
    }


    @Override
    protected void onPause() {
        super.onPause();
//        StatusRider();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //storing rider online status
//        StatusRider();

        //Update Pending order Counts
//        PendingCountOnCheck();
        //Check App Version
        AppVerCheck();
    }
}
