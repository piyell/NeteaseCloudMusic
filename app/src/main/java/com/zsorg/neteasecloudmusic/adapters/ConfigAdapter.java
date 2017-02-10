package com.zsorg.neteasecloudmusic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.zsorg.neteasecloudmusic.ConfigHolder;
import com.zsorg.neteasecloudmusic.callbacks.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.ConfigModel;
import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;
import com.zsorg.neteasecloudmusic.utils.AlertUtil;

import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/9.
 * E-mail:piyell@qq.com
 */

public class ConfigAdapter extends RecyclerView.Adapter<ConfigHolder> implements OnItemCLickListener {

    private final LayoutInflater mInflater;
    private List<ConfigBean> mList;
    private OnItemCLickListener onItemCLickListener;

    public ConfigAdapter(LayoutInflater layoutInflater) {
        super();
        mInflater = layoutInflater;
    }

    @Override
    public ConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConfigHolder holder = new ConfigHolder(mInflater.inflate(R.layout.config_item, parent, false));
        holder.setOnItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ConfigHolder holder, int position) {
        final ConfigBean bean = mList.get(position);

        if (bean.isSwitchVisible()) {
            holder.setSwitchVisible(true);
            holder.scRight.setChecked(bean.isSwitchChecked());
        } else {
            holder.setSwitchVisible(false);
            holder.tvRight.setText(bean.getItemRight());
        }
        holder.tvGroup.setVisibility(bean.getGroupTitle()==null? View.GONE: View.VISIBLE);
        holder.tvGroup.setText(bean.getGroupTitle());
        holder.tvTitle.setText(bean.getItemTitle());
        holder.bottom.setVisibility(bean.isBottomVisible()? View.VISIBLE: View.GONE);
        holder.tvContent.setText(bean.getItemContent());
        holder.tvContent.setVisibility(bean.getItemContent() == null ? View.GONE : View.VISIBLE);

        holder.scRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ConfigModel.getInstance(holder.tvTitle.getContext()).setIsFilter60s(b);
            }
        });

    }



    public void setDataList(List<ConfigBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public ConfigBean getDataAtPos(int position) {
        return mList==null?null:mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void setOnItemClickListener(OnItemCLickListener listener) {
        onItemCLickListener = listener;
    }

    @Override
    public void onItemClick(View view, int position) {
        AlertUtil.showChooseMusicOrderDialog(view.getContext());
        if (null != onItemCLickListener) {
            onItemCLickListener.onItemClick(view,position);
        }
    }
}
