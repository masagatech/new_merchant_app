package com.goyo.marchant.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.goyo.marchant.R;
import com.goyo.marchant.forms.Orientation;
import com.goyo.marchant.forms.PendingOrdersView;
import com.goyo.marchant.model.model_pending;

import java.util.List;

import static com.goyo.marchant.gloabls.Global.IMAGE_URL;

/**
 * Created by fajar on 22-May-17.
 */

public class pending_order_adapter extends RecyclerView.Adapter<pending_order_viewHolder> {

    private List<model_pending> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;


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


    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

        final model_pending timeLineModel = mFeedList.get(position);
//
//

        holder.mMarchant.setText(timeLineModel.studentname+"");
        holder.mDate.setText("Father : "+timeLineModel.fathername+"");
        holder.Custmer_name.setText("Mother : "+timeLineModel.mothername+"");
        holder.mOrder.setText(timeLineModel.classname+"");
        holder.mTime.setText(timeLineModel.mobileno1+"");
        holder.mDeliver_at.setText(timeLineModel.schoolname+"");
        holder.Remark.setText("Enrollment ID : "+timeLineModel.enrollmentno+"");
        holder.DeliverTexts.setText(timeLineModel.mobileno2+"");

        String imgUrl = IMAGE_URL+timeLineModel.studentphoto;

        holder.ArrowRemark.bringToFront();

//        Bitmap imageBitmap= BitmapFactory.decodeResource(mContext.getResources(),  R.drawable.student);
//        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(mContext.getResources(), imageBitmap);
//
////setting radius
//        roundedBitmapDrawable.setCornerRadius(50.0f);
//        roundedBitmapDrawable.setAntiAlias(true);
//        holder.ArrowRemark.setImageDrawable(roundedBitmapDrawable);

        Glide.with(mContext).load(imgUrl)
//                .crossFade()
//                .thumbnail(0.5f)
                .placeholder(R.drawable.student)
                .into(holder.ArrowRemark);



//        roundDrawable.setBounds(left, top, right, bottom);
//        roundDrawable.draw(canvas);
    }




    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }


}
