package com.goyo.parent.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.goyo.parent.R;
import com.goyo.parent.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Calendar;

public class ContactUS extends AppCompatActivity {

    private Button Send;
    private EditText name,campany_name,contact_number,remarks;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Send=(Button)findViewById(R.id.send_form);
        name=(EditText)findViewById(R.id.name);
        campany_name=(EditText)findViewById(R.id.campany_name);
        contact_number=(EditText)findViewById(R.id.contact_number);
        remarks=(EditText)findViewById(R.id.remarks);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader = new ProgressDialog(ContactUS.this);
                loader.setCancelable(false);
                loader.setMessage(ContactUS.this.getString(R.string.wait_msg));
                loader.show();
                SentToServer();
            }
        });
    }


    private void SentToServer(){
        String  names,remark,campany,Phone,currentDateTimeString;
        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        names=name.getText().toString();
        remark=remarks.getText().toString();
        campany=campany_name.getText().toString();
        Phone=contact_number.getText().toString();

        JsonObject json = new JsonObject();
        json.addProperty("uname", names);
        json.addProperty("cmpname", campany);
        json.addProperty("contactno", Phone);
        json.addProperty("msg", remark);
        json.addProperty("cuid", currentDateTimeString);
        json.addProperty("uid","0");
        json.addProperty("utype","other");
        Ion.with(this)
                .load(Global.urls.saveContactUs.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            JsonObject o= result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("funsave_contactus").getAsJsonObject();
                            Toast.makeText(ContactUS.this, o.get("msg").toString(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();


                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
