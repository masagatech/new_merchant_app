package com.goyo.marchant.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.vipulasri.timelineview.TimelineView;
import com.goyo.marchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fajar on 22-May-17.
 */

public class pending_order_viewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_timeline_date)
    TextView mDate;
    @BindView(R.id.text_timeline_title)
    TextView mOrder;
    @BindView(R.id.text_marchent)
    TextView mMarchant;
    @BindView(R.id.Time)
    TextView mTime;
    @BindView(R.id.Deliver_at)
    TextView mDeliver_at;
    @BindView(R.id.Custmer_name)
    TextView Custmer_name;
    @BindView(R.id.Remark)
    TextView Remark;
    @BindView(R.id.Btn_Call)
    ImageButton Btn_Call;
    @BindView(R.id.Btn_Delivery)
    Button Btn_Delivery;
    @BindView(R.id.Btn_map)
    ImageButton Btn_Map;
    @BindView(R.id.remarkhide)
    ImageView ArrowRemark;
    @BindView(R.id.Btn_Return)
    Button Btn_Return;
    @BindView(R.id.Btn_AcceptReject)
    ImageButton Btn_AcceptReject;
    @BindView(R.id.Collected_Cash)
    EditText collected_cash;
    @BindView(R.id.time_marker)
    TimelineView mTimelineView;
    @BindView(R.id.ClickToHide)
    LinearLayout ClickToHide;
    @BindView(R.id.hideButton)
    ToggleButton ButtonHide;
    @BindView(R.id.border)
    RelativeLayout Border;
    @BindView(R.id.DeliverText)
    TextView DeliverTexts;
    @BindView(R.id.Cardview)
    CardView CardView;
    @BindView(R.id.nature_of_work)
    Spinner nature_of_work;
    @BindView(R.id.status)
    Spinner status;
    @BindView(R.id.nature_value)
    EditText nature_value;
    @BindView(R.id.Expense)
    FrameLayout Expense;

    public pending_order_viewHolder(View itemView, int viewType) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mTimelineView.initLine(viewType);

    }
}

