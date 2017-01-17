package com.zsorg.neteasecloudmusic.presenters;

import android.util.Log;

import com.zsorg.neteasecloudmusic.models.DiskMusicModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.views.IScanMusicView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/16.
 * E-mail:piyell@qq.com
 */

public class ScanMusicPresenter {

    private final IScanMusicView iScanMusicView;
    private final DiskMusicDao mDiskMusicDao;

    public ScanMusicPresenter(IScanMusicView scanMusicView) {
        iScanMusicView = scanMusicView;
        mDiskMusicDao = new DiskMusicDao(scanMusicView.getContext());
    }

    public void scanMusic() {
        new DiskMusicModel().scanMusicFile().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MusicBean>() {
            @Override
            public void onSubscribe(Subscription s) {
                iScanMusicView.startScan();
                mDiskMusicDao.clearAll();
                s.request(Integer.MAX_VALUE);

            }

            @Override
            public void onNext(MusicBean musicBean) {
                mDiskMusicDao.addMusic(musicBean);
            }

            @Override
            public void onError(Throwable t) {
                iScanMusicView.onError(t);
            }

            @Override
            public void onComplete() {
                iScanMusicView.finishScan();
            }
        });

    }

}
