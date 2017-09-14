package com.goyo.parent.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goyo.parent.R;
import com.goyo.parent.forms.Orientation;
import com.goyo.parent.forms.PendingOrdersView;
import com.goyo.parent.forms.complated_order;
import com.goyo.parent.model.modal_data;

import java.util.List;

/**
 * Created by mis on 14-Sep-17.
 */

public class AnnouncmentAdapter extends RecyclerView.Adapter<pending_order_viewHolder>  {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;





    public AnnouncmentAdapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding) {
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

        view = mLayoutInflater.inflate(R.layout.item_announce, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final modal_data timeLineModel = mFeedList.get(position);


        //setting Group name and last update date
        holder.mOrder.setText("Last Update : "+timeLineModel.createdon + "");
        holder.mMarchant.setText(timeLineModel.grpname + "");
        holder.Custmer_name.setText(timeLineModel.countannc + "");

        if(timeLineModel.countannc.equals("0")){
            Toast.makeText(mContext, "There is no Announcement in this group!", Toast.LENGTH_SHORT).show();
        }else {
            holder.Border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View m) {
                    Intent intent = new Intent(mContext, complated_order.class);
                    intent.putExtra("GrpName", timeLineModel.grpname);
                    intent.putExtra("GrpID", timeLineModel.grpid);
                    mContext.startActivity(intent);
                }
            });
        }
//
//        if (timeLineModel.isEnabled) {
//            holder.ClickToHide.setVisibility(View.VISIBLE);
//        } else {
//            holder.ClickToHide.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}


