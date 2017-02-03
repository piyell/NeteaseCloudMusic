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

public class SingleSongMenuModel implements IMenuModel{

    private static SingleSongMenuModel mModel;

    List<MenuBean> mList;


    @Override
    public List<MenuBean> getMenuList() {
        return mList;
    }

    public static SingleSongMenuModel getInstance(Context context) {
        if (null == mModel) {
            synchronized (SingleSongMenuModel.class) {
                mModel = new SingleSongMenuModel(context);
            }
        }
        return mModel;
    }



    private SingleSongMenuModel(@NonNull Context context) {
        mList = new ArrayList<>();
        mList.add(new MenuBean(context.getString(R.string.play_at_next), R.drawable.ic_menu_play_next));
        mList.add(new MenuBean(context.getString(R.string.collect_to_playlist), R.drawable.ic_menu_collect));
        mList.add(new MenuBean(context.getString(R.string.share), R.drawable.ic_menu_share));
        mList.add(new MenuBean(context.getString(R.string.delete), R.drawable.ic_menu_delete));
        mList.add(new MenuBean(context.getString(R.string.watch_music_info), R.drawable.ic_menu_music_info));
    }
}
