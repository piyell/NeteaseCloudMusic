package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/31.
 * E-mail:piyell@qq.com
 */

public class GroupSongMenuModel implements IMenuModel{

    private static GroupSongMenuModel mModel;

    List<MenuBean> mList;


    @Override
    public List<MenuBean> getMenuList() {
        return mList;
    }

    public static GroupSongMenuModel getInstance(Context context) {
        if (null == mModel) {
            synchronized (GroupSongMenuModel.class) {
                mModel = new GroupSongMenuModel(context);
            }
        }
        return mModel;
    }



    private GroupSongMenuModel(@NonNull Context context) {
        mList = new ArrayList<>();
        mList.add(new MenuBean(context.getString(R.string.play), R.drawable.ic_menu_play));
        mList.add(new MenuBean(context.getString(R.string.delete), R.drawable.ic_menu_delete));
    }
}
