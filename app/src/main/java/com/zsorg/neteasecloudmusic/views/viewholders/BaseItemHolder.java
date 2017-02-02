package com.zsorg.neteasecloudmusic.views.viewholders;

import android.app.Activity;
import android.view.View;

import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MenuBean;
import com.zsorg.neteasecloudmusic.widgets.MenuDialog;

import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/31.
 * E-mail:piyell@qq.com
 */

public class BaseItemHolder extends BaseHolder implements OnMenuItemClickListener {

    private String mTitle;
    private List<MenuBean> mMenuList;
    private OnMenuItemClickListener mMenuItemListener;

    public BaseItemHolder(View itemView) {
        super(itemView);

        View view = itemView.findViewById(R.id.iv_right);
        if (null != view) {
            view.setOnClickListener(this);
        }
    }

    public void setMenuList(List<MenuBean> menuList) {
        this.mMenuList = menuList;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mMenuItemListener = onMenuItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.iv_right) {
            MenuDialog dialog = new MenuDialog(view.getContext());
            dialog.setTitle(mTitle);
            dialog.setMenuList(mMenuList);
            dialog.setOnMenuItemClickListener(this);
            dialog.show();
            if (view.getContext() instanceof Activity) {
                dialog.setPeekHeight((Activity) view.getContext());
            }
        } else {
            super.onClick(view);
        }
    }

    @Override
    public void onMenuItemClick(int position) {
        if (null != mMenuItemListener) {
            mMenuItemListener.onMenuItemClick(position);
        }
    }
}
