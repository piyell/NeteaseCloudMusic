package com.zsorg.neteasecloudmusic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.List;

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

    public abstract void setDatas(List<MusicBean> list);

    public MusicBean getDataAtPosition(int position){
        return null;}

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
