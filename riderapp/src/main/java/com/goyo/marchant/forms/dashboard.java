package com.goyo.marchant.forms;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.JsonObject;
import com.goyo.marchant.R;
import com.goyo.marchant.common.Preferences;
import com.goyo.marchant.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class dashboard extends AppCompatActivity {

//    @BindView(R.id.Pending_Order)
//    FrameLayout Pending_Order;
//    @BindView(R.id.All_Order_details)
//    FrameLayout OrderDetails;
//    @BindView(R.id.pushOrder)
//    FrameLayout Push_Order;
//    @BindView(R.id.Complated_Orders)
//    FrameLayout Complated_Orders;
//    @BindView(R.id.Rejected_Orders)
//    FrameLayout Rejected_Orders;
//    @BindView(R.id.Cash_Collection)
//    FrameLayout Cash_Collection;
//    @BindView(R.id.Notifications)
//    FrameLayout Notifications;
//    @BindView(R.id.All_Order)
//    FrameLayout All_Order;
//    @BindView(R.id.Holyday)
//    FrameLayout Holyday;
//    @BindView(R.id.Announcment)
//    FrameLayout Announcment;
//    @BindView(R.id.Schadule)
//    FrameLayout Schedule;
//    @BindView(R.id.Gallery)
//    FrameLayout Gallery;
    @BindView(R.id.fees)
    FrameLayout Fees;

    private PopupWindow OrderPopup;
    private Button Btn_Accept, Btn_Reject;
    private FloatingActionButton Flot_add_Task,Map;
    private TextView Deliver_at_Text;
    private TextView PopUp_CountText;
    private TextView Online, RiderName;
    private ImageButton Logout;
    private ImageView SclLogo;
    private SwitchCompat RiderStatusSwitch;
    Intent mServiceIntent;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
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
    public static String SclId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerCrashReport();

        setContentView(R.layout.activity_dashboard);

        //checking app version
//        AppVerCheck();

        //getting Last TripId
//        TripId=PreferenceManager.getDefaultSharedPreferences(dashboard.this).getString("tripid", "0");


        //counts
        Count_Pending=(TextView)findViewById(R.id.Count);
//        Count_TodayVisits=(TextView)findViewById(R.id.Count_Todayvisit);
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




        RiderName = (TextView) findViewById(R.id.nameRider);
        SclLogo=(ImageView)findViewById(R.id.Images) ;
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


        //getting Group Id and Name
//        Intent intent = getIntent();
//        String SclName = intent.getExtras().getString("SclName");
//        SclId=intent.getExtras().getString("SclId");
//        String SclLogoURL=intent.getExtras().getString("SclLogo");


//        //loading Logo
//        Glide.with(this).load(SclLogoURL)
//                .thumbnail(0.5f)
//                .placeholder(R.drawable.scho)
//                .into(SclLogo);
////        showing name in actionbar
//        RiderName.setText(SclName);

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

//        String sessionid = SHP.get(dashboard.this, SHP.ids.sessionid, "-1").toString();
//        String uid = SHP.get(dashboard.this, SHP.ids.uid, "-1").toString();
        JsonObject json = new JsonObject();
        json.addProperty("login_id", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        json.addProperty("v_token", Preferences.getValue_String(getApplicationContext(), Preferences.USER_AUTH_TOKEN));
        json.addProperty("device", "ANDROID");
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

                            String message=result.get("message").getAsString();
                            if (result.get("status").getAsInt() == 1) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Preferences.setValue(getApplicationContext(), Preferences.USER_ID, "");
                             Intent i = new Intent(dashboard.this, com.goyo.marchant.initials.login.class);
                                startActivity(i);
                                dashboard.this.finish();
                            } else {
                                Toast.makeText(dashboard.this, "Logout Failed! "+message, Toast.LENGTH_SHORT).show();
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
    public void registerCrashReport() {
        // TODO: Use the current user's information
//        Crashlytics.setUserIdentifier("Hotspot ID: "+Global.loginusr.getHsid() + "");
//        Crashlytics.setUserEmail("Name: "+Global.loginusr.getFullname() + "");
        Crashlytics.setUserName("MOB/Email: " + Preferences.getValue_String(dashboard.this, Preferences.USER_ID) + "");
    }



    boolean isStatusDbCheck = false;




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



//        return data;

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







//    @OnClick(R.id.Pending_Order)
//    void click() {
//        Intent intent = new Intent(this, pending_order.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.Cash_Collection)
//    void click2() {
//        Intent intent = new Intent(this, ResultActivity.class);
//        startActivity(intent);
//
//    }
//
//    @OnClick(R.id.Complated_Orders)
//    void click3() {
//        Intent intent = new Intent(this, Semester.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.Rejected_Orders)
//    void click4() {
//        Intent intent = new Intent(this, StudentLeaveActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.All_Order)
//    void click5() {
//        Intent intent = new Intent(this, all_order.class);
//        startActivity(intent);
////        Intent intent = new Intent(this, About_us.class);
////        startActivity(intent);
//    }
//
//    @OnClick(R.id.Notifications)
//    void click6() {
//        int flag = 1;
//        Intent intent = new Intent(this, newOrder.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.pushOrder)
//    void click7() {
//        Intent intent = new Intent(this, StillDev.class);
//        startActivity(intent);
//    }
//    @OnClick(R.id.All_Order_details)
//    void click8() {
//        Intent intent = new Intent(this, Attentence.class);
//        startActivity(intent);
//    }
//    @OnClick(R.id.Holyday)
//    void click9() {
//        Intent intent = new Intent(this, rejected_order.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.Announcment)
//    void click10() {
////        Intent intent = new Intent(this, complated_order.class);
////        startActivity(intent);
//
//        Intent intent = new Intent(this, Announcment.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.Schadule)
//    void click11() {
//        Intent intent = new Intent(this, SchodulActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.Gallery)
//    void click12() {
//        Intent intent = new Intent(this, Album.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.fees)
//    void click13() {
//        Intent intent = new Intent(this, FeesActivity.class);
//        startActivity(intent);
//    }

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
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_settings, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//            case R.id.about_us:
//                Intent intent = new Intent(this, About_us.class);
//                startActivity(intent);
//                return true;
//            case R.id.logout:
//                //calling logout api
//                Logout();
//                return true;
//
//            case R.id.my_profile:
//                Intent intent3 = new Intent(this, Profile_Page.class);
//                startActivity(intent3);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }




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
//        AppVerCheck();
    }
}
