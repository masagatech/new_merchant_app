package com.goyo.marchant.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.marchant.R;
import com.goyo.marchant.forms.Orientation;
import com.goyo.marchant.forms.PendingOrdersView;
import com.goyo.marchant.model.modal_data;

import java.util.List;

/**
 * Created by mis on 12-Oct-17.
 */

public class sem_frag_adapter extends RecyclerView.Adapter<pending_order_viewHolder> {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;


    public sem_frag_adapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;

    }

    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public pending_order_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.item_frag_sem, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final modal_data timeLineModel = mFeedList.get(position);


        //setting Group name and last update date
        holder.mDate.setText(timeLineModel.examdate + "");
        holder.mOrder.setText(timeLineModel.chptrname + "");
        holder.mMarchant.setText(timeLineModel.subname + "");
//        holder.Custmer_name.setText(timeLineModel.countannc + "");
        holder.Remark.setText(timeLineModel.frmtm + "");
        holder.mTime.setText( timeLineModel.totm + "");
        holder.mDeliver_at.setText(timeLineModel.passmarks + "");
        holder.DeliverTexts.setText(timeLineModel.outofmarks + "");

//        if(timeLineModel.countannc.equals("0")){
//            Toast.makeText(mContext, "There is no Announcement in this group!", Toast.LENGTH_SHORT).show();
//        }else {
        holder.Border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View m) {
                if (holder.ClickToHide.getVisibility() == View.VISIBLE) {
                    holder.ClickToHide.setVisibility(View.GONE);
                    timeLineModel.isEnabled = false;
                } else {

                    timeLineModel.isEnabled = true;
                    notifyDataSetChanged();
                    holder.ClickToHide.setVisibility(View.VISIBLE);
                }
            }
        });

        if(timeLineModel.isEnabled){
            holder.ClickToHide.setVisibility(View.VISIBLE);
        } else{
            holder.ClickToHide.setVisibility(View.GONE);

        }
    }



//
//        if (timeLineModel.isEnabled) {
//            holder.ClickToHide.setVisibility(View.VISIBLE);
//        } else {
//            holder.ClickToHide.setVisibility(View.GONE);
//        }
//    }

    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

}


