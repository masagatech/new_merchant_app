package com.goyo.marchant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.goyo.marchant.R;

import az.plainpie.PieView;

/**
 * Created by mis on 12-Oct-17.
 */

public class ResultFragViewHolder extends RecyclerView.ViewHolder {

    TextView mDate,Header,From,To,Sub;
    TextView mOrder;
    TextView Status;
    TextView mMarchant;
    TimelineView mTimelineView;
    RelativeLayout Border;
    LinearLayout Hide;
    PieView pieView;



    public ResultFragViewHolder(View itemView, int viewType) {
        super(itemView);

        mDate=(TextView) itemView.findViewById(R.id.text_timeline_date);
        From=(TextView) itemView.findViewById(R.id.From);
        To=(TextView) itemView.findViewById(R.id.To);
        Sub=(TextView) itemView.findViewById(R.id.sub);
        Hide=(LinearLayout) itemView.findViewById(R.id.Hide);
        Header=(TextView) itemView.findViewById(R.id.Header);
        mOrder=(TextView) itemView.findViewById(R.id.text_timeline_title);
        Status=(TextView) itemView.findViewById(R.id.Remark);
        mMarchant=(TextView) itemView.findViewById(R.id.text_marchent);
        mTimelineView=(TimelineView) itemView.findViewById(R.id.time_marker);
        pieView=(PieView) itemView.findViewById(R.id.pieView);
        Border=(RelativeLayout) itemView.findViewById(R.id.border);

        mTimelineView.initLine(viewType);

    }
}
