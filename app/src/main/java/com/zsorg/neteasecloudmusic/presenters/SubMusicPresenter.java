package com.zsorg.neteasecloudmusic.presenters;

import com.zsorg.neteasecloudmusic.CONST;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.views.ISubMusicView;

import java.util.ArrayList;
import java.util.Set;

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

    public void requestList(int mMusicType) {
        switch (mMusicType) {
            case CONST.TYPE_SINGLE:
                mIView.showItems(musicDao.queryAll());
                break;
            case CONST.TYPE_SINGER:
                mIView.showItems(musicDao.querySingerList());
                break;
            case CONST.TYPE_ALBUM:
                mIView.showItems(musicDao.queryAlbumList());
                break;
            case CONST.TYPE_FOLDER:
                mIView.showItems(musicDao.queryPathList());
                break;
        }

    }
}
