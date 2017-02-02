package com.zsorg.neteasecloudmusic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public abstract class BaseAdapter<T extends BaseHolder> extends RecyclerView.Adapter<BaseHolder> {

    public static final int TYPE_HEADER = Integer.MAX_VALUE;

    public static final int TYPE_FOOTER = Integer.MIN_VALUE;

    public final LayoutInflater mInflater;

    private OnItemCLickListener mListener;

    private View mHeaderView;

    private View mFooterView;


    public BaseAdapter(@NonNull LayoutInflater inflater) {
        mInflater = inflater;
    }

    public abstract BaseHolder onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(T holder, int position);

    public abstract int getDataCount();

    public void setDatas(List<MusicBean> list){}

    public int getItemType(int position){
        return super.getItemViewType(position);
    }

    public MusicBean getDataAtPosition(int position) {
        return null;
    }

    public ArrayList<MusicBean> getDataList() {
        return null;
    }

    public void setOnItemClickListener(OnItemCLickListener listener) {
        mListener = listener;
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
    }

    public void addFooterView(View view) {
        mFooterView = view;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            //headerView
            return TYPE_HEADER;
        } else if (mFooterView != null && position == getItemCount() - 1) {
            //footerView
            return TYPE_FOOTER;
        } else {
            //normalView
            if (mFooterView != null) {
                position--;
            }
            if (mHeaderView != null) {
                position--;
            }
            return getItemType(position);
        }
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new BaseHolder(mHeaderView);
            case TYPE_FOOTER:
                return new BaseHolder(mFooterView);
            default:
                return onCreateHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (null != mListener) {
            holder.setOnItemClickListener(mListener);
        }

        if (mHeaderView != null && position == 0) {
            //bind headerView
        } else if (mFooterView != null && position == getItemCount() - 1) {
            //bind footerView
        } else {
            //bind normalView
            if (mFooterView != null) {
                position--;
            }
            if (mHeaderView != null) {
                position--;
            }
            onBindHolder((T) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getDataCount() + (null == mHeaderView ? 0 : 1) + (null == mFooterView ? 0 : 1);
    }
}
