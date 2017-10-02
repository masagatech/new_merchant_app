package com.goyo.parent.initials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.goyo.parent.R;
import com.goyo.parent.common.Checker;
import com.goyo.parent.common.Preferences;
import com.goyo.parent.forms.PushOrder;
import com.goyo.parent.utils.common;

public class sessionchecker extends AppCompatActivity {
    boolean isLanguageShow = false;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessionchecker);
        isLanguageShow = !common.setLanguage(getApplicationContext(), getBaseContext());
        loader = new ProgressDialog(sessionchecker.this);
        setTitle("Login Checking...");
    }

    private void checkLogin() {

        loader.setCancelable(false);
        loader.setMessage("Session Login. Please wait.");
        loader.show();

        if (!Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID).isEmpty()) {
            loader.setMessage("Auto Login...");
            loader.hide();
            Intent i = new Intent(sessionchecker.this, PushOrder.class);
            startActivity(i);

            finish();
        }else {
            loader.hide();
            nextScreen();

        }
//        if (!SHP.get(sessionchecker.this, SHP.ids.sessionid, "").toString().equals("")) {
//
//            String sessionid = SHP.get(sessionchecker.this, SHP.ids.sessionid, "-1").toString();
//            String uid = SHP.get(sessionchecker.this, SHP.ids.uid, "-1").toString();
//
//            String token = "";
//            try{
//                token  = FirebaseInstanceId.getInstance().getToken();
//            }catch (Exception ex){
//
//            }
//            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//
//            JsonObject json = new JsonObject();
//            json.addProperty("base", "_sid");
//            json.addProperty("sid", sessionid);
//            //json.addProperty("uid", uid);
//            json.addProperty("token", token);
//            json.addProperty("type", "driver");
//            json.addProperty("otherdetails", "{}");
//            json.addProperty("src", "m");
//            json.addProperty("imei",telephonyManager.getDeviceId());

//            Ion.with(this)
//                    .load(Global.urls.getlogin.value)
//                    .setJsonObjectBody(json)
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            // do stuff with the result or error
//                            try {
//                                if (result != null) {
//                                    //Log.v("result", result.toString())
//                                    Gson gson = new Gson();
//                                    Type listType = new TypeToken<List<model_loginusr>>() {
//                                    }.getType();
//                                    List<model_loginusr> login = (List<model_loginusr>) gson.fromJson(result.get("data"), listType);
//                                    if (login.size() > 0) {

//                                        Global.loginusr = login.get(0);
//                                        if (Global.loginusr.getStatus() == 1) {
//                                            //Toast.makeText(sessionchecker.this, "Auto Login Successfully!", Toast.LENGTH_SHORT).show();
//                                            Intent i = new Intent(sessionchecker.this, dashboard.class);
//                                            startActivity(i);
//                                            finish();
//                                        } else {
//                                            Toast.makeText(sessionchecker.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//                                            nextScreen();
//                                        }
//                                    } else {
//                                        Toast.makeText(sessionchecker.this, "Oops there is some issue! please login later!", Toast.LENGTH_SHORT).show();
//                                        nextScreen();
//                                    }
//                                } else {
//                                    if (e.getMessage().contains("Time")) {
//                                        Toast.makeText(sessionchecker.this, "Auto Login Failed!", Toast.LENGTH_SHORT).show();
//                                        nextScreen();
//                                    } else {
//                                        Toast.makeText(sessionchecker.this, "Auto Login Failed! " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        nextScreen();
//                                    }
//                                    return;
//                                }
//
//                            } catch (Exception ea) {
//                                Toast.makeText(sessionchecker.this, "Error: " + ea.getMessage(), Toast.LENGTH_LONG).show();
//                                ea.printStackTrace();
//                                nextScreen();
//                            }

//                        }
//                    });


        }

    private void nextScreen() {
        Intent i;
        i = new Intent(sessionchecker.this, login.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Checker(this).pass(new Checker.Pass() {
            @Override
            public void pass() {
                checkLogin();
            }

        }).check(Checker.Resource.NETWORK);
    }
}
