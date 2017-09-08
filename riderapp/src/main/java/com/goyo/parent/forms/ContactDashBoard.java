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

public class ContactDashBoard extends AppCompatActivity {
    private Button Send;
    private EditText Name,Title,Message;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_dash_board);

        setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Send=(Button)findViewById(R.id.send_form);
        Name=(EditText)findViewById(R.id.Name);
        Title=(EditText)findViewById(R.id.Title);
        Message=(EditText)findViewById(R.id.Message);

        Name.setText(Global.loginusr.getFullname()+"");

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader = new ProgressDialog(ContactDashBoard.this);
                loader.setCancelable(false);
                loader.setMessage(ContactDashBoard.this.getString(R.string.wait_msg));
                loader.show();
                SentToServer();

            }
        });
    }


    private void SentToServer(){
        String  name,remark_1,remark_2,currentDateTimeString;
        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        name=Name.getText().toString();
        remark_1=Title.getText().toString();
        remark_2=Message.getText().toString();

        JsonObject json = new JsonObject();
        json.addProperty("uname", name);
        json.addProperty("title", remark_1);
        json.addProperty("msg", remark_2);
        json.addProperty("cuid", currentDateTimeString);
        json.addProperty("uid", Global.loginusr.getDriverid()+"");
        json.addProperty("utype","emp");
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
                            Toast.makeText(ContactDashBoard.this, o.get("msg").toString(), Toast.LENGTH_SHORT).show();
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
