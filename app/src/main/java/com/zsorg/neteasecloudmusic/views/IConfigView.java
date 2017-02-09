package com.zsorg.neteasecloudmusic.views;

import android.content.Context;

import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;

import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/9.
 * E-mail:piyell@qq.com
 */

public interface IConfigView {
    void displayConfigList(List<ConfigBean> list);

    Context getContext();
}
