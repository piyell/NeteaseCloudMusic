package com.zsorg.neteasecloudmusic;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public abstract class BaseAdapter<T extends BaseHolder> extends RecyclerView.Adapter<T>{

    private OnItemCLickListener mListener;

    public final LayoutInflater mInflater;

    public BaseAdapter(@NonNull LayoutInflater inflater) {
        mInflater = inflater;
    }

    public abstract void onBindHolder(T holder,int position);

    public void setOnItemClickListener(OnItemCLickListener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        if (null!=mListener) {
            holder.setOnItemClickListener(mListener);
        }
        onBindHolder(holder, position);
    }
}
