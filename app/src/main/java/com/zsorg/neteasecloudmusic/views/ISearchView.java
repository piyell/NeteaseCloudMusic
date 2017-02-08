package com.zsorg.neteasecloudmusic.views;

import android.content.Context;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.List;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/8.
 * E-mail:piyell@qq.com
 */

public interface ISearchView {
    Context getContext();

    void updateSearchResult(List<MusicBean> results);
}
