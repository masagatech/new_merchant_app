package com.goyo.parent.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.parent.R;
import com.goyo.parent.forms.Orientation;
import com.goyo.parent.forms.PendingOrdersView;
import com.goyo.parent.model.model_notification;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fajar on 02-Jun-17.
 */

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapterViewHolder> {

    private List<model_notification> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    static HashMap<Integer, CountDownTimer> thread = new HashMap<>();

    public NewOrderAdapter(List<model_notification> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public NewOrderAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        view = mLayoutInflater.inflate(R.layout.timeline_new_order, parent, false);
        return new NewOrderAdapterViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final NewOrderAdapterViewHolder holder,int position) {
//        if (holder.timer != null) KillTimer(holder.timer);
        final model_notification timeLineModel = mFeedList.get(position);

        holder.mStops.setText(timeLineModel.msg + "");
        holder.popup_counter.setText(timeLineModel.createdon + "");
//        holder.Cash_amount.setText(String.format("%.2f", Double.parseDouble(timeLineModel.amt)));
        holder.mOulets.setText(timeLineModel.title+"");
//        holder.mTime.setText(timeLineModel.pcktm);


        //accept/reject button hide if order is expired
//        if(!timeLineModel.isExpired) {
//            holder.Btn_Reject.setVisibility(View.VISIBLE);
//            holder.Btn_Accept.setVisibility(View.VISIBLE);
//        }


//        holder.Btn_Accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setNoti("accept");
//                int newPosition = holder.getAdapterPosition();
//
//                //calling Api
//                setStatus("accord", timeLineModel.ordid, newPosition, timeLineModel.autoid, holder,"");
//
//
//            }
//        });

//        holder.Btn_Reject.setOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     final EditText edittext = new EditText(mContext);
//                                                     edittext.setMaxLines(100);
//
//                                                     //showing alert dialog if press reject
//                                                    final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
//                                                             .setTitle(mContext.getResources().getString(R.string.reject_reason_head))
//                                                             .setMessage(mContext.getResources().getString(R.string.reject_reason_body))
//                                                             .setView(edittext)
//                                                             .setPositiveButton(mContext.getResources().getString(R.string.yes), null)
//                                                             .setNegativeButton(R.string.alert_no_text, null)
//                                                             .setIcon(R.drawable.rider_del)
//                                                             .create();
//
//                                                     alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//
//                                                         @Override
//                                                         public void onShow(DialogInterface dialog) {
//
//                                                             Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
//                                                             button.setOnClickListener(new View.OnClickListener() {
//                                                                 @Override
//                                                                 public void onClick(View view) {
//                                                                     String feedabck = "";
//                                                                     //Getting rider feed back
//                                                                     feedabck = edittext.getText().toString();
//                                                                     //hiding button and canceling timer
//
//                                                                     int newPosition = holder.getAdapterPosition();
//                                                                     if (feedabck.equals("")) {
//                                                                         Toast.makeText(mContext, "Please enter the reason!", Toast.LENGTH_SHORT).show();
//                                                                     } else {
//                                                                         //calling api
//                                                                         setNoti("reject");
//                                                                         setStatus("rejord", timeLineModel.ordid, newPosition, timeLineModel.autoid, holder, feedabck);
//                                                                         alertDialog.dismiss();
//                                                                     }
//                                                                 }
//                                                             });
//
//                                                             Button button2 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
//                                                             button2.setOnClickListener(new View.OnClickListener() {
//                                                                 @Override
//                                                                 public void onClick(View view) {
//                                                                     alertDialog.dismiss();
//                                                                 }
//                                                             });
//                                                         }
//                                                     });
//                                                     alertDialog.show();
//                                                 }
//                                             });
//        Log.v("rmtm", "" + timeLineModel.remaintime);
//
//
//        //timer in each order
//        holder.timer = new CountDownTimer(timeLineModel.remaintime, 1000) {
//            public void onFinish() {
//                int newPosition = holder.getAdapterPosition();
//                Log.v("autoid", "" + timeLineModel.autoid);
//                Log.v("pos", "" + newPosition);
//                sb.NOTIFICATION_DELETE(timeLineModel.autoid + "");
//                holder.popup_counter.setText(mContext.getResources().getString(R.string.exp));
//                if(mFeedList.size()>0) {
//                    mFeedList.remove(newPosition);
//                    notifyItemRemoved(newPosition);
//                    notifyItemRangeChanged(newPosition, getItemCount());
//                }
//                holder.Btn_Reject.setVisibility(View.GONE);
//                holder.Btn_Accept.setVisibility(View.GONE);
//                timeLineModel.isExpired = true;
//            }
//
//            public void onTick(long millisUntilFinished) {
//                timeLineModel.remaintime -= 1000;
//                holder.popup_counter.setText("" + String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
//                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
//            }
//        }.start();
    }


    @Override
    public int getItemCount() {
        return mFeedList.size();
    }


}
