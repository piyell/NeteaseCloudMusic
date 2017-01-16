package com.zsorg.neteasecloudmusic.presenters;

import android.util.Log;

import com.google.common.base.Throwables;
import com.zsorg.neteasecloudmusic.models.DiskMusicModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.views.IScanMusicView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/16.
 * E-mail:piyell@qq.com
 */

public class ScanMusicPresenter {

    private final IScanMusicView iScanMusicView;

    public ScanMusicPresenter(IScanMusicView scanMusicView) {
        iScanMusicView = scanMusicView;
    }

    public void scanMusic() {
        new DiskMusicModel().loadMusicList().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MusicBean>() {
            @Override
            public void onSubscribe(Subscription s) {
                iScanMusicView.startScan();
                s.request(Integer.MAX_VALUE);

            }

            @Override
            public void onNext(MusicBean musicBean) {
                Log.e("Tag", musicBean.getPath());
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
