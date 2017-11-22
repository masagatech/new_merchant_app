package com.goyo.marchant.forms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.goyo.marchant.R;
import com.goyo.marchant.common.Preferences;
import com.goyo.marchant.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Hashtable;
import java.util.Set;

import static com.goyo.marchant.forms.dashboard.SclId;

public class ExamResultActivity extends AppCompatActivity {

    Hashtable<String, String> Student_Name;
    public static String SemIDs="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        //getting Group Id and Name
        Intent intent = getIntent();
        String SemName = intent.getExtras().getString("SemName");
        SemIDs=intent.getExtras().getString("SemID");

        setTitle(SemName);

        GetStudent();
    }

private void GetStudent() {
    JsonObject json = new JsonObject();
    json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
    json.addProperty("flag", "student");
    json.addProperty("enttid", SclId + "");
    Ion.with(this)
            .load(Global.urls.getParentDetails.value)
            .setJsonObjectBody(json)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    // do stuff with the result or error
                    try {
                        Student_Name = new Hashtable<String, String>();
                        for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                            JsonObject o = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                            Student_Name.put(o.get("autoid").getAsString(), o.get("studentname").getAsString());
                        }
                        SetStudent();

                    } catch (Exception ea) {
                        ea.printStackTrace();
                    }


                }
            });
}

    private void SetStudent(){

        Set<String> keys = Student_Name.keySet();
        if (keys.size() == 0) return;
        FragmentPagerItems dynamicFragment = FragmentPagerItems.with(this).create();
        for (String key : keys) {
            //Student_Name.get(key);
            Bundle b = new Bundle();
            b.putString("ID", key);
            dynamicFragment.add(FragmentPagerItem.of(Student_Name.get(key), frag_exam_result.class, b));


        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), dynamicFragment);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}

