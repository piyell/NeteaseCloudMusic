package com.zsorg.neteasecloudmusic;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnItemCLickListener onItemClickListener;

    public BaseHolder(View itemView) {
        super(itemView);
        if (null != itemView) {
            itemView.setOnClickListener(this);
        }
    }

    public void setOnItemClickListener(OnItemCLickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (null!=onItemClickListener) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
