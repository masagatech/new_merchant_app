package com.goyo.traveltracker.model;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.goyo.traveltracker.forms.OrderStatus;
import com.pchmn.materialchips.model.ChipInterface;

/**
 * Created by mis on 21-Jul-17.
 */

public class model_tag_db implements ChipInterface {

    private String id;
    private String CreatedBy;
    private String Title;
    private String Remark1;
    private String Remark2;
    private String Remark3;
    private String IsFlag;

    public model_tag_db(String id, String name, String _Remark1,String _Remark2,String _Remark3,String _CreatedBy,String _IsFlag) {
        this.id = id;
        this.Title = name;
        this.Remark1 = _Remark1;
        this.Remark2 = _Remark2;
        this.Remark3 = _Remark3;
        this.CreatedBy = _CreatedBy;
        this.IsFlag = _IsFlag;
    }


    public String getIsFlag() {
        return IsFlag;
    }

    public void setIsFlag(String isFlag) {
        IsFlag = isFlag;
    }
    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Uri getAvatarUri() {
        return null;
    }


    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getLabel() {
        return Title;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
    public boolean isEnabled = false;


    @Override
    public String getInfo() {
        return Remark1;
    }
    public String getRemark2() {
        return Remark2;
    }

    public String getRemark3() {
        return Remark3;
    }

    public OrderStatus status = OrderStatus.ACTIVE;
}
