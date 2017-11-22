package com.goyo.marchant.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.marchant.R;
import com.goyo.marchant.forms.Orientation;
import com.goyo.marchant.forms.PendingOrdersView;
import com.goyo.marchant.forms.dashboard;
import com.goyo.marchant.gloabls.Global;
import com.goyo.marchant.model.model_expense;
import com.goyo.marchant.model.model_push_order;
import com.goyo.marchant.model.model_rider_list;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.goyo.marchant.gloabls.Global.IMAGE_URL;
import static com.goyo.marchant.gloabls.Global.urls.getAvailRider;
import static com.goyo.marchant.gloabls.Global.urls.setStatus;

/**
 * Created by fajar on 29-May-17.
 */

public class PushOrderAdapter extends RecyclerView.Adapter<PushOrderViewHolder>{

    private List<model_expense> mFeedList;
    private List<model_push_order> mMainList;
    private Context mContext;
   private String PushLat,PushLon;
    private ProgressDialog loader;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    Runnable updateTimerMethod;
    private int OrderNo;
    private Filter Pushfilter;
//    private int times[];
    protected boolean stopFlag = false;
    HashMap<String, Runnable> threads = new HashMap<>();
    ListView lstRider;
    Dialog dialogOut;
    private Handler myHandler = new Handler();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");


    public PushOrderAdapter(List<model_expense> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;

    }

    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public PushOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.push_order_timeline, parent, false);

        return new PushOrderViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final PushOrderViewHolder holder, final int position) {

        final model_expense timeLineModel = mFeedList.get(position);

//        OrderNo=timeLineModel.ordno.size();

        holder.mOrderID.setText(timeLineModel.schoolid);
        holder.mMarchant.setText(timeLineModel.schoolname);
//        holder.mAddr.setText(timeLineModel._disc);
//        holder.mTime.setText(timeLineModel._code);

        String imgUrl = IMAGE_URL+timeLineModel.schoollogo;

        Glide.with(mContext).load(imgUrl)
                .thumbnail(0.5f)
                .placeholder(R.drawable.scho)
                .into(holder.Logo);


        holder.Clicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, dashboard.class);
                intent.putExtra("SclName", timeLineModel.schoolname);
                intent.putExtra("SclId", timeLineModel.schoolid);
                intent.putExtra("SclLogo", IMAGE_URL+timeLineModel.schoollogo);
                mContext.startActivity(intent);
            }
        });

//        String url = myUrls.get(position);




//       if(timeLineModel._is_active.equals("true")){
//            holder.Cardview.setVisibility(View.VISIBLE);
//       }else {
//           holder.Cardview.setVisibility(View.GONE);
//       }
    }
//        holder.mCash.setText("₹ "+timeLineModel.totamt+"");
//        PushLat=timeLineModel.geoloc.lat;
//        PushLon=timeLineModel.geoloc.lon;
//
//        if(timeLineModel.nm != null){
//            holder.mRider.setText(timeLineModel.nm);
//        }

        //selecting rider
//        holder.mRider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               onClickRider(holder,timeLineModel );
//
//            }
//        });

        //Including a timer for to show alert
//        updateTimerMethod = new Runnable() {
//
//
//            public void run() {
//                Date date2 = null;
//                Date date1 = null;
//
//                try {
//                    date2 = simpleDateFormat.parse(timeLineModel.pchtm);
//                    date1 = simpleDateFormat.parse(simpleDateFormat.format((new Date()).getTime()));
//                    long difference = date1.getTime() - date2.getTime();
//                    Integer minutes = (int) (difference / (1000 * 60));
//
////                    times[position]=minutes;
//
//                    // If time diffrence is greater than alert time text will blink and turn to red color
//
//                    if (minutes > Global.RedAlert) {
//                        holder.mAlertTime.setTextColor(Color.RED);
//                        Animation anim = new AlphaAnimation(0.4f, 1.0f);
//                        anim.setDuration(1000); //You can manage the time of the blink with this parameter
//                        anim.setStartOffset(20);
//                        anim.setRepeatMode(Animation.REVERSE);
//                        anim.setRepeatCount(Animation.INFINITE);
//                        holder.mAlertTime.startAnimation(anim);
//                    }else {
//                        holder.mAlertTime.setTextColor(Color.BLACK);
//                    }
//
//                    Log.v("minuts", minutes + "");
//                    holder.mAlertTime.setText("( " + minutes + " )");
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }


//                if (!stopFlag)
//                    myHandler.postDelayed(this, 2000);
//
//
//            }
//        }


//        ;
//        threads.put(position + "", updateTimerMethod);
//        myHandler.postDelayed(updateTimerMethod, 0);


//        holder.Btn_Push.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View m) {
//                if( holder.mRider.getText().toString().equals(mContext.getString(R.string.select_rider))){
//                    //if not selected any rider from lisst
//                    Toast.makeText(mContext, mContext.getString(R.string.select_rider_msg), Toast.LENGTH_SHORT).show();
//                }else {
//                    //calling api
//                    setStatus(timeLineModel.ordid,holder.mRider_Id.getText().toString(),holder);
//                }
//            }
//        });
//    }
//
//    @Override
//    public Filter getFilter() {
//        if (Pushfilter == null)
//            Pushfilter = new PushFilter();
//
//        return Pushfilter;
//    }
//
//    public void resetData() {
//        mFeedList = mMainList;
//    }


    private void setStatus(String ordid,String rdid,final PushOrderViewHolder holder) {

        JsonObject json = new JsonObject();
        json.addProperty("flag", "accord");
        json.addProperty("subflag", "push");
        json.addProperty("rdid", rdid + "");
        json.addProperty("ordid", ordid);
        json.addProperty("uid", Global.loginusr.getDriverid() + "");

        Ion.with(mContext)
                .load(setStatus.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) {
                                JsonObject data = result.get("data").getAsJsonObject();

                                if (data.get("status").getAsBoolean()) {
                                    String msg = data.get("msg").toString();
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                    holder.mHide.setVisibility(View.GONE);
                                    holder.mRider_Assign.setText("Assigned to "+holder.mRider.getText().toString());
                                    holder.mRider_Assign.setVisibility(View.VISIBLE);

                                }else {
                                    holder.mHide.setVisibility(View.VISIBLE);
                                    holder.mRider_Assign.setText("");
                                    holder.mRider_Assign.setVisibility(View.GONE);
                                    String msg = data.get("msg").toString();
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    public void onClickRider(final PushOrderViewHolder holder, final model_push_order timeLineModel) {

//        GetRiderList(timeLineModel.rdrid);

        dialogOut = new Dialog(mContext);
        dialogOut.setContentView(R.layout.rider_list);
        lstRider = (ListView) dialogOut.findViewById(R.id.lstRiderList);
        dialogOut.setCancelable(true);

        lstRider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model_rider_list Rider = (model_rider_list) parent.getItemAtPosition(position);
                if(Rider.rdrid == timeLineModel.rdrid){
                        Toast.makeText(mContext, "Already assigned!!!",Toast.LENGTH_SHORT).show();
                }else{
                    if(!Rider.onoff){
                        Dialoge(holder,Rider);
                    }else {
                        holder.mRider.setText(Rider.nm);
                        holder.mRider_Id.setText(Rider.rdrid + "");
                        dialogOut.hide();
                    }
                }
            }
        });
        dialogOut.show();
    }

    private void Dialoge(final PushOrderViewHolder holder,final model_rider_list Rider){
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.offline_rider_head)
                .setMessage(R.string.offline_rider_body)
                .setPositiveButton(mContext.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        holder.mRider.setText(Rider.nm);
                        holder.mRider_Id.setText(Rider.rdrid + "");
                        dialogOut.hide();
                    }
                })
                .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.stop_trip).show();
    }

    public void Kill() {
        Set<Map.Entry<String, Runnable>> s = threads.entrySet();
        if(s!= null) {
            Iterator it = s.iterator();
            stopFlag = true;
            while (it.hasNext()) {
                try {

                    Map.Entry pairs = (Map.Entry) it.next();
                    Runnable r = (Runnable) pairs.getValue();
                    myHandler.removeCallbacks(r);
                    r = null;
                } catch (Exception x) {

                }
            }
            s = null;
            it = null;
            threads.clear();
        }
    }

    private void GetRiderList(final Integer rdrid){
        loader = new ProgressDialog(mContext);
        loader.setCancelable(false);
        loader.setMessage(mContext.getString(R.string.wait_msg));
        loader.show();

        Ion.with(mContext)
                .load("GET", getAvailRider.value)
                .addQuery("flag", "avail")
                .addQuery("callr","mob" )
                .addQuery("hsid", Global.loginusr.getHsid() + "")
                .addQuery("lat", PushLat + "")
                .addQuery("lon", PushLon + "")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {

                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_rider_list>>() {
                            }.getType();
                            ArrayList<model_rider_list> events = (ArrayList<model_rider_list>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events,rdrid);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
                    }
                });

    }

    private void bindCurrentTrips(ArrayList<model_rider_list> lst,Integer rdrid) {
        if (lst.size() > 0) {
            dialogOut.findViewById(R.id.txtNodata).setVisibility(View.GONE);
            RiderListAdapter adapter = new RiderListAdapter(mContext, lst, rdrid);
            lstRider.setAdapter(adapter);
        } else {
            dialogOut.findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }



//
//    private class PushFilter extends Filter {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//
//            constraint = constraint.toString().toLowerCase();
//            FilterResults result = new FilterResults();
//            if (constraint != null && constraint.toString().length() > 0) {
//                ArrayList<model_push_order> filteredItems = new ArrayList<>();
//
//                for (int i = 0, l = mMainList.size(); i < l; i++) {
//                    model_push_order push = mMainList.get(i);
//                    if (push.stsi.equals(constraint))
//                        filteredItems.add(push);
//                }
//                result.count = filteredItems.size();
//                result.values = filteredItems;
//            } else {
//                synchronized (this) {
//                    result.values = mMainList;
//                    result.count = mMainList.size();
//                }
//            }
//            return result;
//        }
//
//        @SuppressWarnings("unchecked")
//        @Override
//        protected void publishResults(CharSequence constraint,
//                                      FilterResults results) {
//
//            if (results.count == 0) {
//                mFeedList = (ArrayList<model_push_order>) results.values;
//                notifyDataSetChanged();
//            } else {
//                mFeedList = (ArrayList<model_push_order>) results.values;
//                notifyDataSetChanged();
//            }
//        }
//    }

}
