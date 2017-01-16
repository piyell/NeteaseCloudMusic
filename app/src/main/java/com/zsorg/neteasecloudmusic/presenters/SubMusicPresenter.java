package com.zsorg.neteasecloudmusic.presenters;

import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.views.ISubMusicView;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/16.
 * E-mail:piyell@qq.com
 */

public class SubMusicPresenter {

    private final ISubMusicView mIView;
    private final DiskMusicDao musicDao;

    public SubMusicPresenter(ISubMusicView view) {
        mIView = view;
        musicDao = new DiskMusicDao(view.getContext());
    }

    public void requestList() {
        mIView.showItems(musicDao.querryAll());
    }
}
