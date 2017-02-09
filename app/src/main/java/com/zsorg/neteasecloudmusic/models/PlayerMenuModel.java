package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
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

public class PlayerMenuModel implements IMenuModel{

    private static PlayerMenuModel mModel;

    List<MenuBean> mList;


    @Override
    public List<MenuBean> getMenuList() {
        return mList;
    }

    public static PlayerMenuModel getInstance(Context context) {
        if (null == mModel) {
            synchronized (PlayerMenuModel.class) {
                mModel = new PlayerMenuModel(context);
            }
        }
        return mModel;
    }



    private PlayerMenuModel(@NonNull Context context) {
        mList = new ArrayList<>();
        mList.add(new MenuBean(context.getString(R.string.collect_to_playlist), R.drawable.ic_menu_collect));
        mList.add(new MenuBean(context.getString(R.string.share), R.drawable.ic_menu_share));
        mList.add(new MenuBean(context.getString(R.string.watch_music_info), R.drawable.ic_menu_music_info));
    }
}
