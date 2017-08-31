package com.goyo.traveltracker.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.forms.OrderStatus;
import com.goyo.traveltracker.forms.Orientation;
import com.goyo.traveltracker.forms.PendingOrdersView;
import com.goyo.traveltracker.model.model_expense;
import com.goyo.traveltracker.utils.VectorDrawableUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajar on 26-May-17.
 */

public class RejectedOrderAdapter extends RecyclerView.Adapter<pending_order_viewHolder>  {

   public static List<model_expense> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;


    public RejectedOrderAdapter(List<model_expense> feedList, Orientation orientation, boolean withLinePadding) {
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

//        if (viewType == TYPE_FOOTER) {
//
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_expense,
//                    parent, false);
//            return new pending_order_viewHolder(view, viewType);
//
//        }
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.rejected_order_timeline, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

        final model_expense timeLineModel = mFeedList.get(position);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            if (timeLineModel.status == OrderStatus.INACTIVE) {
                holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
            } else if (timeLineModel.status == OrderStatus.ACTIVE) {
                holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.round));
            } else {
                holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.round));
            }
        }

    final Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        ArrayList<String> TagsArray = gson.fromJson(timeLineModel.get_is_active(), type);


//        String s=timeLineModel.get_code();
//        final String date = s.split(",")[0]; // "Before"
//        final String time = s.split(",")[1]; // "After"

        //setting order no. and marchant name on start
    holder.mTime.setText(timeLineModel.get_disc());
        holder.Custmer_name.setText("Approved Amount: "+timeLineModel.get_appr_amt());
        holder.mMarchant.setText("₹ "+timeLineModel.get_value());
        holder.mOrder.setText(timeLineModel.get_name());
        if(TagsArray!=null) {
            holder.Tags.setTags(TagsArray);
        }
        holder.mDate.setText(timeLineModel.get_code());
        holder.mDeliver_at.setText(timeLineModel.get_exp_id());



        if(timeLineModel.get_is_server()!=null) {
            switch (timeLineModel.get_is_server()) {
                case "2": {
                    GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                    int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
                    gd.setStroke(width_px, Color.parseColor("#ff99cc00"));

                    break;
                }
                case "1": {
                    GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                    int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
                    gd.setStroke(width_px, Color.parseColor("#ffff4444"));
                    break;
                }

                case "task": {
                GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
                gd.setStroke(width_px, Color.parseColor("#ff0099cc"));
                break;
                }

                case "stop": {
                    GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                    int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
                    gd.setStroke(width_px, Color.parseColor("#ffffbb33"));
                    break;
                }
                default:
                    GradientDrawable gd = (GradientDrawable) holder.Border.getBackground();
                    int width_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, mContext.getResources().getDisplayMetrics());
                    gd.setStroke(width_px, Color.parseColor("#ffffbb33"));
            }
        }

        //click to expand and hide when press agine
//        holder.ButtonHide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View m) {
//                if (holder.ClickToHide.getVisibility() == View.VISIBLE) {
//                    holder.ClickToHide.setVisibility(View.GONE);
//                    holder.mDate.setVisibility(View.GONE);
//                    holder.mOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                    holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                    timeLineModel.isEnabled = false;
//                } else {
//                    if(timeLineModel.custaddr==null) {
//                        loader = new ProgressDialog(mContext);
//                        loader.setCancelable(false);
//                        loader.setMessage(mContext.getString(R.string.wait_msg));
//                        loader.show();
//
//                        //getting data when user expand
//                        RejectedOrder(timeLineModel, holder, position);
//
//                    } else {
//                        timeLineModel.isEnabled = true;
//                    }
//                    notifyDataSetChanged();
////                    holder.ClickToHide.setVisibility(View.VISIBLE);
////                    holder.mDate.setVisibility(View.VISIBLE);
////                    holder.mOrder.setCompoundDrawablesWithIntrinsicBounds(R.drawable.order_id, 0, 0, 0);
////                    holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_outlets, 0, 0, 0);
//                }
//            }
//        });
//        holder.collected_cash.setEnabled(false);
//        if (timeLineModel.isEnabled) {
//            holder.mTime.setText(mContext.getString(R.string.returned_at)+" "+timeLineModel.deltime + "");
//            holder.collected_cash.setText("₹ " + timeLineModel.amtcollect + "");
//            holder.Custmer_name.setText(timeLineModel.custname + "");
//            holder.mDeliver_at.setText(timeLineModel.custaddr + " ");
//            holder.mDate.setText(timeLineModel.dltm + "");
//
//            holder.ClickToHide.setVisibility(View.VISIBLE);
//            holder.mDate.setVisibility(View.VISIBLE);
//            holder.mOrder.setCompoundDrawablesWithIntrinsicBounds(R.drawable.order_id, 0, 0, 0);
//            holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pending_outlets, 0, 0, 0);
//        } else {
//            holder.ClickToHide.setVisibility(View.GONE);
//            holder.mDate.setVisibility(View.GONE);
//            holder.mOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            holder.mMarchant.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//
//            holder.mTime.setText("");
//            holder.collected_cash.setText("");
//            holder.Custmer_name.setText("");
//            holder.mDeliver_at.setText("");
//            holder.mDate.setText("");
//
//
//        }
    }


//    private void RejectedOrder(final model_completed timeLineModel, final pending_order_viewHolder holder,final int position){
//
//        Ion.with(mContext)
//                .load("GET", getOrders.value)
//                .addQuery("flag", "completed")
//                .addQuery("subflag", "detl")
//                .addQuery("ordid", timeLineModel.ordid + "")
//                .addQuery("orddid", timeLineModel.orderdetailid + "")
//                .addQuery("rdid", Global.loginusr.getDriverid() + "")
//                .addQuery("stat","2")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_completed>>() {
//                            }.getType();
//                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
//
//                            if (events.size() > 0) {
//
//                                JsonObject Data = result.get("data").getAsJsonArray().get(0).getAsJsonObject();
//                                timeLineModel.custaddr = Data.get("cadr").getAsString();
//                                timeLineModel.custname = Data.get("cnm").getAsString();
//                                timeLineModel.deltime = Data.get("dtm").getAsString();
//                                timeLineModel.custname = Data.get("cnm").getAsString();
//                                timeLineModel.dltm = Data.get("dltm").getAsString();
//
//
////                                holder.mTime.setText(mContext.getString(R.string.returned_at)+timeLineModel.deltime + "");
////                                holder.collected_cash.setText("₹ " + timeLineModel.amtcollect + "");
////                                holder.Custmer_name.setText(timeLineModel.custname + "");
////                                holder.mDeliver_at.setText(timeLineModel.custaddr + " ");
////                                holder.mDate.setText(timeLineModel.dltm + "");
//                                mFeedList.get(position).isEnabled = true;
//                                notifyDataSetChanged();
//
//                            }
//
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
//                        loader.hide();
//                    }
//                });
//    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public View View;
        public FooterViewHolder(View v) {
            super(v);
            View = v;
            TextView EXP=(TextView)v.findViewById(R.id.exp_value);
            TextView Stops=(TextView)v.findViewById(R.id.stop_value);
            TextView Tasks=(TextView)v.findViewById(R.id.task_value);
            // Add your UI Components here

        }

    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }


}

