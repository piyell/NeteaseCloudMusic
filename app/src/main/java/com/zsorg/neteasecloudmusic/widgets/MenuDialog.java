package com.zsorg.neteasecloudmusic.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MenuBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/31.
 * E-mail:piyell@qq.com
 */

public class MenuDialog extends BaseBottomSheetDialog implements OnItemCLickListener {

    private TextView mTitle;
    private List<MenuBean> mMenuList;
    private MenuAdapter mAdapter;
    private OnMenuItemClickListener mMenuItemListener;

    public MenuDialog(@NonNull Context context) {
        super(context);
        initMenu();
    }

    public MenuDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        initMenu();
    }

    public MenuDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initMenu();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        if (null != mTitle) {
            mTitle.setText(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (null != mTitle) {
            mTitle.setText(getContext().getString(titleId));
        }
    }

    public void setTitle(String title) {
        if (null != mTitle) {
            mTitle.setText(title);
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mMenuItemListener = onMenuItemClickListener;
    }


    public void setMenuList(List<MenuBean> menuList) {
        this.mMenuList = menuList;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        cancel();
        if (null != mMenuItemListener) {
            mMenuItemListener.onMenuItemClick(position);
        }
    }

    private void initMenu() {
        View view =setHeaderView(R.layout.dialog_menu_header);

        mTitle = (TextView) findViewById(R.id.tv_title);

        mAdapter = new MenuAdapter(getLayoutInflater());
        mAdapter.setOnItemClickListener(this);
        setListAdapter(mAdapter);
    }

    private class MenuAdapter extends BaseAdapter<MenuHolder> {

        public MenuAdapter(LayoutInflater layoutInflater) {
            super(layoutInflater);
        }

        @Override
        public BaseHolder onCreateHolder(ViewGroup parent, int viewType) {
            return new MenuHolder(mInflater.inflate(R.layout.dialog_menu_item, parent, false));
        }

        @Override
        public void onBindHolder(MenuHolder holder, int position) {
            MenuBean bean = mMenuList.get(position);
            holder.tvName.setText(bean.getName());
            if (bean.getIconID() > 0) {
                holder.ivIcon.setImageDrawable(VectorDrawableCompat.create(getContext().getResources(), bean.getIconID(), null));
            }
        }

        @Override
        public int getDataCount() {
            return mMenuList==null?0:mMenuList.size();
        }
    }

    class MenuHolder extends BaseHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        public MenuHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
