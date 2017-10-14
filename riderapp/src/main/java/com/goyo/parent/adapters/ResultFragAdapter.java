package com.goyo.parent.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.parent.R;
import com.goyo.parent.forms.Orientation;
import com.goyo.parent.forms.PendingOrdersView;
import com.goyo.parent.model.modal_data;

import java.util.List;

import az.plainpie.animation.PieAngleAnimation;

/**
 * Created by mis on 12-Oct-17.
 */

public class ResultFragAdapter  extends RecyclerView.Adapter<ResultFragViewHolder>  {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;





    public ResultFragAdapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;

    }
    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public ResultFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.item_result_exam_frag, parent, false);

        return new ResultFragViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final ResultFragViewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final modal_data timeLineModel = mFeedList.get(position);


        //setting Group name and last update date
        holder.mOrder.setText(timeLineModel.resremark + "");
        holder.mMarchant.setText(timeLineModel.ressubname + "");
        holder.Status.setText(timeLineModel.status + "");
        holder.mDate.setText("Out of "+timeLineModel.outofmarks );
//        holder.Custmer_name.setText(timeLineModel.countannc + "");

//        if(Float.parseFloat(timeLineModel.marks))

        holder.pieView.setMaxPercentage(Float.parseFloat(timeLineModel.outofmarks));

        holder.pieView.setPercentage(Float.parseFloat(timeLineModel.marks));

         holder.pieView.setInnerText(timeLineModel.marks+"");
        if(timeLineModel.status.equals("Passed")) {
            holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ff99cc00"));
        }else {
            holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ffff4444"));
        }
         holder.pieView.setInnerTextVisibility(View.VISIBLE);
        PieAngleAnimation animation = new PieAngleAnimation(holder.pieView);
        animation.setDuration(1000); //This is the duration of the animation in millis
        holder.pieView.startAnimation(animation);


//        if(timeLineModel.countannc.equals("0")){
//            Toast.makeText(mContext, "There is no Announcement in this group!", Toast.LENGTH_SHORT).show();
//        }else {
        holder.Border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View m) {
//                    Intent intent = new Intent(mContext, complated_order.class);
//                    intent.putExtra("GrpName", timeLineModel.grpname);
//                    intent.putExtra("GrpID", timeLineModel.grpid);
//                    mContext.startActivity(intent);
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

