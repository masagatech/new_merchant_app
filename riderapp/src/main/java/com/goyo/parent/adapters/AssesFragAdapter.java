package com.goyo.parent.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.parent.R;
import com.goyo.parent.forms.AssesmeResultActivity;
import com.goyo.parent.forms.Orientation;
import com.goyo.parent.forms.PendingOrdersView;
import com.goyo.parent.model.modal_data;

import java.util.List;

/**
 * Created by mis on 13-Oct-17.
 */

public class AssesFragAdapter extends RecyclerView.Adapter<pending_order_viewHolder>  {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;





    public AssesFragAdapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding) {
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

        view = mLayoutInflater.inflate(R.layout.item_asses_type, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final modal_data timeLineModel = mFeedList.get(position);


        holder.mOrder.setText(timeLineModel.asstyphead + "");
        holder.mMarchant.setText(timeLineModel.asstypname + "");

        holder.Border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View m) {
                Intent intent = new Intent(mContext, AssesmeResultActivity.class);
                intent.putExtra("AsseName", timeLineModel.asstypname);
                intent.putExtra("AsseType", timeLineModel.asstyp);
                intent.putExtra("AseeID", timeLineModel.asstypid);
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
//    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}



