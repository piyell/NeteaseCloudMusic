package com.zsorg.neteasecloudmusic;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.callbacks.OnItemCLickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/9.
 * E-mail:piyell@qq.com
 */

public class ConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_right)
    public TextView tvRight;
    @BindView(R.id.tv_group_title)
    public TextView tvGroup;
    @BindView(R.id.tv_content_title)
    public TextView tvContent;
    @BindView(R.id.tv_item_title)
    public TextView tvTitle;
    @BindView(R.id.sc_right)
    public SwitchCompat scRight;
    @BindView(R.id.view_bottom)
    public View bottom;
    @BindView(R.id.layout_item)
    public View item;

    private OnItemCLickListener onItemClickListener;

    public ConfigHolder(View itemView) {
        super(itemView);

        itemView.findViewById(R.id.layout_item).setOnClickListener(this);

        ButterKnife.bind(this, itemView);
    }

    public void setGroupTitleVisible(boolean isVisible) {
        tvGroup.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setBottomLayoutVisible(boolean isVisible) {
        bottom.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setContentVisible(boolean isVisible) {
        tvContent.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setSwitchVisible(boolean isVisible) {
        scRight.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        tvRight.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    public void setOnItemClickListener(OnItemCLickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (scRight.getVisibility()==View.VISIBLE) {
            scRight.toggle();
        }else if (null!=onItemClickListener) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
