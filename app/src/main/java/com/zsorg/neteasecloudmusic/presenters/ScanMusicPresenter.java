package com.zsorg.neteasecloudmusic.presenters;

import com.zsorg.neteasecloudmusic.models.ScanMusicModel;
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
    private final DiskMusicDao mScanMusicDao;

    public ScanMusicPresenter(IScanMusicView scanMusicView) {
        iScanMusicView = scanMusicView;
        mScanMusicDao = new DiskMusicDao(scanMusicView.getContext());
    }

    public void scanMusic() {
        new ScanMusicModel().scanMusicFile().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MusicBean>() {
            @Override
            public void onSubscribe(Subscription s) {
                iScanMusicView.startScan();
                mScanMusicDao.clearAll();
                s.request(Integer.MAX_VALUE);

            }

            @Override
            public void onNext(MusicBean musicBean) {
                mScanMusicDao.addMusic(musicBean);
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
