package com.goyo.traveltracker.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.forms.OrderStatus;
import com.goyo.traveltracker.forms.Orientation;
import com.goyo.traveltracker.forms.PendingOrdersView;
import com.goyo.traveltracker.forms.dashboard;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_pending;
import com.goyo.traveltracker.model.model_tag_db;
import com.goyo.traveltracker.utils.VectorDrawableUtils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.goyo.traveltracker.Service.RiderStatus.Rider_Lat;
import static com.goyo.traveltracker.Service.RiderStatus.Rider_Long;
import static com.goyo.traveltracker.forms.pending_order.TripId;
import static com.goyo.traveltracker.gloabls.Global.urls.saveNatureTask;
import static com.goyo.traveltracker.gloabls.Global.urls.setTripAction;

/**
 * Created by fajar on 22-May-17.
 */

public class pending_order_adapter extends RecyclerView.Adapter<pending_order_viewHolder> {

    private List<model_pending> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    public String tripid = "0";
    private ProgressDialog loader;
    private Spinner RejectSpinner;
    private Double CashCollected;
    private  String Selected_Nature,Selected_Status,Value,Remark;
    private String Status,Nature;
    List<String> RejectList;
    List<String> Ntr_Work;
    List<String> Status_Work;
    String SelectedReason;


    public pending_order_adapter(List<model_pending> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
    }
    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public pending_order_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.pending_order_timeline, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    public void updateTripId(String Tripid){
        tripid = Tripid;
    }



    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

        final model_pending timeLineModel = mFeedList.get(position);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            if (timeLineModel.status == OrderStatus.INACTIVE) {
                holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
            } else if (timeLineModel.status == OrderStatus.ACTIVE) {
                holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorAccent));
            } else {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorAccent));
            }
        }


        holder.mOrder.setText(timeLineModel.tskid +"");
        holder.mMarchant.setText(timeLineModel.task);
//        holder.Custmer_name.setText(timeLineModel.custname);
//        holder.mDeliver_at.setText(timeLineModel.custaddr+"\n");
//        holder.Remark.setText("Remark: "+timeLineModel.remark);
        holder.mTime.setText(timeLineModel.todt);
        holder.mDate.setText(timeLineModel.frmdt);
//        holder.collected_cash.setText(+timeLineModel.amtcollect +"");
        final int newPosition = holder.getAdapterPosition();


        holder.collected_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.collected_cash.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.collected_cash, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        //getting nature of work data from server
        GetNtr_Work(holder);

        SQLBase db = new SQLBase(mContext);

        List<model_tag_db> data = new ArrayList<model_tag_db>();
        List<HashMap<String,String>> d = db.Get_Tags();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_tag_db( d.get(i).get(Tables.tbltags.Tag_Id), d.get(i).get(Tables.tbltags.Tag_Title),d.get(i).get(Tables.tbltags.Tag_remark_1),d.get(i).get(Tables.tbltags.Tag_remark_2),d.get(i).get(Tables.tbltags.Tag_remark_3),d.get(i).get(Tables.tbltags.Tag_Creat_On),d.get(i).get(Tables.tbltags.Is_Server_Send)));
            }
        }

        holder.chipsInput.setFilterableList(data);

        //getting Status of work data from server
        GetStatus_Work(holder);

        GetCurrentStatus(newPosition,holder);


//        holder.setIsRecyclable(false);


//        if(Status!=null) {
//
//        }
//
//        if(Nature!=null) {
//            int pos2 = Ntr_Work.indexOf(Nature);
//            holder.nature_of_work.setSelection(pos2);
//        }

        holder.Btn_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.Btn_Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remark= holder.collected_cash.getText().toString();
                Value = holder.nature_value.getText().toString();
                Selected_Nature = holder.nature_of_work.getSelectedItem().toString();
                Selected_Status = holder.status.getSelectedItem().toString();
                if(Remark.equals("")){
                    Toast.makeText(mContext, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                }else {
                    List<model_tag_db> contactsSelected = (List<model_tag_db>) holder.chipsInput.getSelectedChipList();
//                    String Tags[]=new String[50];
                    List<String> Tags = new ArrayList<String>();
                    if(contactsSelected.size()>0) {
                        for (int i = 0; i <= contactsSelected.size() - 1; i++) {
                            Tags.add(contactsSelected.get(i).getLabel());
                        }
                    }
                    loader = new ProgressDialog(mContext);
                    loader.setCancelable(false);
                    loader.setMessage(mContext.getString(R.string.wait_msg));
                    loader.show();
                    if(Value.equals("")){
                        Value="0";
                    }
                    Update(timeLineModel, position, newPosition, Selected_Nature, Selected_Status, Remark, Value,Tags);
                }
            }
        });



//        holder.Btn_Call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialContactPhone(timeLineModel.custmob);
//            }
//        });
//
//        holder.Btn_Delivery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CashCollected= Double.valueOf(holder.collected_cash.getText().toString());
//                loader = new ProgressDialog(mContext);
//                loader.setCancelable(false);
//                loader.setMessage(mContext.getString(R.string.wait_msg));
//                loader.show();
//
//               // calling api
//                Deliver(timeLineModel,position,newPosition,CashCollected);
//            }
//        });
//
//        holder.Btn_Return.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Dialog dialog = new Dialog(mContext);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.reject_reason);
//                dialog.setCancelable(true);
//
//               RejectSpinner = (Spinner) dialog.findViewById(R.id.rej_list_spinner);
//                Button Cancel = (Button) dialog.findViewById(R.id.cancel);
//                Button Return = (Button) dialog.findViewById(R.id.returne);
//
//                GetRejectReason();
//
//                RejectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                        SelectedReason=RejectList.get(position);
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//               Cancel.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                Return.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        loader = new ProgressDialog(mContext);
//                        loader.setCancelable(false);
//                        loader.setMessage(mContext.getString(R.string.wait_msg));
//                        loader.show();
//                        dialog.dismiss();
//                        Return(timeLineModel,position,newPosition,SelectedReason);
//
//                    }
//                });
//                dialog.show();
//            }
//        });
//
//
//
//        holder.Btn_Map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(mContext, CustemerMapsActivity.class);
//                //passing custemer address
//                intent.putExtra("Address", timeLineModel.custaddr);
//                mContext.startActivity(intent);
//            }
//        });


    }


    private void GetCurrentStatus(final int position,final pending_order_viewHolder holder){
        JsonObject json = new JsonObject();
        json.addProperty("flag", "byemp");
        json.addProperty("empid",Global.loginusr.getDriverid() + "");
        Ion.with(mContext)
                .load(Global.urls.getAllocateTask.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());

                            if(result.get("data").getAsJsonArray().get(position).getAsJsonObject().get("tsknature").toString().equals("null")) {

                            }else {
                                Status= result.get("data").getAsJsonArray().get(position).getAsJsonObject().get("tsknature").getAsJsonArray().get(0).getAsJsonObject().get("tstype").getAsString();
                                final int pos = Status_Work.indexOf(Status);
                                holder.status.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.status.setSelection(pos);
                                    }
                                });


                                Nature= result.get("data").getAsJsonArray().get(position).getAsJsonObject().get("tsknature").getAsJsonArray().get(0).getAsJsonObject().get("tntype").getAsString();
                                final int pos2 = Ntr_Work.indexOf(Nature);

                                holder.nature_of_work.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.nature_of_work.setSelection(pos2);
                                    }
                                });


                            }
//                            Status=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("tstype").getAsString();
//                                Nature=result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("tntype").getAsString();
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    private void Update(final model_pending timeLineModel, final int position, final int newPosition,final String Selected_Nature,final String Selected_Status,final String Remark,final String Value, List<String> Tags){

        String tag= Joiner.on("','").join(Tags);

        //JSONArray jsArray = new JSONArray(Tags);

        JsonObject json = new JsonObject();
        json.addProperty("tntype", Selected_Nature);
        json.addProperty("tstype", Selected_Status);
        json.addProperty("value", Value);
        json.addProperty("remark", Remark);
        json.addProperty("cuid", Global.loginusr.getDriverid()+ "");
        json.addProperty("tskid", timeLineModel.tskid+ "");
        json.addProperty("tag","{'" + tag + "'}");


        Ion.with(mContext)
                .load(saveNatureTask.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (result != null) Log.v("result", result.toString());
//                            if(result.get("data").getAsJsonObject().get("status").getAsBoolean()){

                                final model_pending status = mFeedList.get(mFeedList.size() == position + 1 ? position : position + 1);
                                status.status = (OrderStatus.ACTIVE);
//                                //promt message to stop trip if its last order
//                                AutoStop();

                                if(Status_Work != null && !Status_Work.isEmpty()) {
                                    if (Status_Work.get(Status_Work.size() - 1).equals(Selected_Status)) {
                                        //removing order from list
                                        mFeedList.remove(newPosition);
                                        notifyItemRemoved(newPosition);
                                        notifyItemRangeChanged(newPosition, mFeedList.size());
                                    }
                                }
                            JsonObject o= result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("funsave_tasknature").getAsJsonObject();
                            Toast.makeText(mContext, o.get("msg").toString(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(mContext, result.get("data").getAsJsonObject().get("msg").toString()
//                                        , Toast.LENGTH_SHORT).show();
//                            }
////                            }
//                            else{
//                                Toast.makeText(mContext,result.get("data").getAsJsonObject().get("msg").toString()
//                                        ,Toast.LENGTH_SHORT).show();
//                            }

                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
                    }
                });

    }

    private void AutoStop(){
            if(mFeedList.size()==1){
                new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getString(R.string.head_stoptrip))
                        .setMessage(mContext.getString(R.string.msg_stoptrip))
                        .setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //calling api
                               stopTrip();

                            }
                        })
                        .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.stop_trip).show();
            }

        }

    private void GetStatus_Work(final pending_order_viewHolder holder){
        JsonObject json = new JsonObject();
        json.addProperty("flag", "all");
        json.addProperty("group", "taskstatus");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        Ion.with(mContext)
                .load(Global.urls.getMOM2.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            Status_Work = new ArrayList<String>();
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                Status_Work.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("val").getAsString());
                            }
                            bindCurrentTrips3(Status_Work,holder);

                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    private void bindCurrentTrips3(List<String> lst,final pending_order_viewHolder holder) {
        if (lst.size() > 0) {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, lst);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.status.setAdapter(dataAdapter);
        }
    }




    private void GetNtr_Work(final pending_order_viewHolder holder){
        JsonObject json = new JsonObject();
        json.addProperty("flag", "dropdown");
        Ion.with(mContext)
                .load(Global.urls.getAllocateTask.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            Ntr_Work = new ArrayList<String>();
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                Ntr_Work.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("val").getAsString());
                            }
                            bindCurrentTrips2(Ntr_Work,holder);

                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    private void bindCurrentTrips2(List<String> lst,final pending_order_viewHolder holder) {
        if (lst.size() > 0) {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, lst);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.nature_of_work.setAdapter(dataAdapter);
        }
    }

    public void stopTrip(){


        JsonObject json = new JsonObject();
        json.addProperty("flag", "stop");
        json.addProperty("loc", Rider_Lat+","+Rider_Long);
        json.addProperty("tripid", TripId);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");

        Ion.with(mContext)
                .load(setTripAction.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            if(result.get("data").getAsJsonObject().get("status").getAsBoolean()){
                                Toast.makeText(mContext,result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                                //StartRide.setVisibility(View.GONE);
//                               mContext.onBackPressed();
                                TripId="0";
                                Intent intent=new Intent(mContext,dashboard.class);
                                mContext.startActivity(intent);
                            }
                            else{
                                Toast.makeText(mContext,result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }



    private void GetRejectReason(){
        loader = new ProgressDialog(mContext);
        loader.setCancelable(false);
        loader.setMessage(mContext.getString(R.string.wait_msg));
        loader.show();

        JsonObject json = new JsonObject();
        json.addProperty("flag", "");
        json.addProperty("group", "rejectreason");
        Ion.with(mContext)
                .load(Global.urls.getMOM.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            RejectList = new ArrayList<String>();
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                RejectList.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("val").getAsString());
                            }
                            bindCurrentTrips(RejectList);

                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
                    }
                });

    }

    private void bindCurrentTrips(List<String> lst) {
        if (lst.size() > 0) {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, lst);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            RejectSpinner.setAdapter(dataAdapter);
        }
    }

    private void dialContactPhone(final String phoneNumber) {
        mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void Deliver(final model_pending timeLineModel, final int position, final int newPosition,double Cash){


        JsonObject json = new JsonObject();
        json.addProperty("flag", "delvr");
        json.addProperty("loc", Rider_Lat+","+Rider_Long);
        json.addProperty("tripid", tripid);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");
        json.addProperty("amtrec", timeLineModel.amtcollect + "");   //        Cash
        json.addProperty("ordid", timeLineModel.ordid + "");
        json.addProperty("orddid", timeLineModel.orderdetailid + "");
        json.addProperty("remark", timeLineModel.remark);


        Ion.with(mContext)
                .load(setTripAction.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            if(result.get("data").getAsJsonObject().get("status").getAsBoolean()){

                                    final model_pending status = mFeedList.get(mFeedList.size() == position + 1 ? position : position + 1);
                                    status.status = (OrderStatus.ACTIVE);
                                //promt message to stop trip if its last order
                                    AutoStop();

                                //removing order from list
                                    mFeedList.remove(newPosition);
                                    notifyItemRemoved(newPosition);
                                    notifyItemRangeChanged(newPosition, mFeedList.size());

                                    Toast.makeText(mContext, result.get("data").getAsJsonObject().get("msg").toString()
                                            , Toast.LENGTH_SHORT).show();
                                }
//                            }
                            else{
                                Toast.makeText(mContext,result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
                    }
                });

    }

    private void Return(final model_pending timeLineModel, final int position, final int newPosition,String feedback){

        if(feedback==null){
            feedback="";
        }

        JsonObject json = new JsonObject();
        json.addProperty("flag", "retn");
        json.addProperty("loc", Rider_Lat+","+Rider_Long);
        json.addProperty("tripid", tripid);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");
        json.addProperty("amtrec", timeLineModel.amtcollect + "");
        json.addProperty("ordid", timeLineModel.ordid + "");
        json.addProperty("orddid", timeLineModel.orderdetailid + "");
        json.addProperty("remark", feedback);
        Ion.with(mContext)
                .load(setTripAction.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            if(result.get("data").getAsJsonObject().get("status").getAsBoolean()){
                                    final model_pending status = mFeedList.get(mFeedList.size() == position + 1 ? position : position + 1);
                                    status.status = (OrderStatus.ACTIVE);
                                     AutoStop();
                                    mFeedList.remove(newPosition);
                                    notifyItemRemoved(newPosition);
                                    notifyItemRangeChanged(newPosition, mFeedList.size());
                                    Toast.makeText(mContext, result.get("data").getAsJsonObject().get("msg").toString()
                                            , Toast.LENGTH_SHORT).show();
//                                }
                            }
                            else{
                                Toast.makeText(mContext,result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
                    }
                });

    }


    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }


}
