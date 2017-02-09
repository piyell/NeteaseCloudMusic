package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;

import com.zsorg.neteasecloudmusic.CONST;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;
import com.zsorg.neteasecloudmusic.models.beans.MenuBean;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/31.
 * E-mail:piyell@qq.com
 */

public class ConfigModel implements IConfigModel{

    private static ConfigModel mModel;
    private final SoftReference<Context> mContext;

    List<ConfigBean> mList;
    private final SharedPreferences mSP;
    private final String[] orders;
    private int mMusicOrder;

    @Override
    public List<ConfigBean> getConfigList() {
        return mList;
    }

    public void setMusicOrder(int musicOrder) {
        mList.get(0).setItemRight(orders[musicOrder]);
        mMusicOrder = musicOrder;
        SharedPreferences.Editor edit = mSP.edit();
        edit.putInt(CONST.SP_MUSIC_ORDER, musicOrder);
        edit.apply();
    }

    public void setIsFilter60s(boolean isFilter) {
        mList.get(1).setSwitchChecked(isFilter);
        SharedPreferences.Editor edit = mSP.edit();
        edit.putBoolean(CONST.SP_IS_FILTER_60, isFilter);
        edit.apply();
    }

    public boolean isFilter60s() {
        return mList.get(1).isSwitchChecked();
    }

    public String getMusicOrder() {
        return orders[mMusicOrder];
    }

    public static ConfigModel getInstance(Context context) {
        if (null == mModel) {
            synchronized (ConfigModel.class) {
                mModel = new ConfigModel(context);
            }
        }
        return mModel;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e("tag", "ConfigModel finalize");
    }

    private ConfigModel(@NonNull Context context) {
        mContext = new SoftReference<Context>(context);
        mList = new ArrayList<>();
        mSP = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFilter = mSP.getBoolean(CONST.SP_IS_FILTER_60, true);
        mMusicOrder = mSP.getInt(CONST.SP_MUSIC_ORDER, CONST.SP_MUSIC_ORDER_DEFAULT);
        orders = context.getResources().getStringArray(R.array.array_music_order);
        mList.add(new ConfigBean(getString(R.string.music), getString(R.string.music_play_order), null, orders[mMusicOrder], false, false, true));
        mList.add(new ConfigBean(getString(R.string.scan), getString(R.string.filter_less_than_60), null, null, true, isFilter, false));
    }

    private String getString(@StringRes int stringID) {
        Context context = mContext.get();
        if (null != context) {
            return context.getString(stringID);
        }
        return null;
    }

}
