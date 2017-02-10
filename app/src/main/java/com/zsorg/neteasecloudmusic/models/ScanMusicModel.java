package com.zsorg.neteasecloudmusic.models;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.zsorg.neteasecloudmusic.utils.MusicUtil;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.utils.MP3Util;

import org.reactivestreams.Publisher;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by piyel_000 on 2017/1/13.
 */


public class ScanMusicModel implements IMusicModel {

    private boolean isFilter60s;

    @Override
    public Flowable<MusicBean> scanMusicFile() {
        return mScanMusicOnStorage(Environment.getExternalStorageDirectory())
                .observeOn(Schedulers.computation())
                .flatMap(new Function<File, Publisher<MusicBean>>() {
                    @Override
                    public Publisher<MusicBean> apply(File file) throws Exception {
                        MusicBean bean = MP3Util.parseMP3File(file.getAbsolutePath());
                        if (null!=bean && (bean.getDuration()>60000 || !isFilter60s)) {
                            return Flowable.just(bean).subscribeOn(Schedulers.computation());
                        }
                        return Flowable.empty();
                    }
                });
    }

    @Override
    public Flowable<MusicBean> loadMusicFile() {
        return null;
    }

    @Override
    public void setIsFilter60s(boolean isFilter60s) {
        this.isFilter60s = isFilter60s;
    }

    private Flowable<File> mScanMusicOnStorage(@NonNull final File file) {
        File[] items = file.listFiles();
        if (null != items) {
            return Flowable.fromArray(items).observeOn(Schedulers.computation()).flatMap(new Function<File, Publisher<File>>() {
                @Override
                public Publisher<File> apply(File file) throws Exception {
                    if (file != null) {
                        if (file.isDirectory()) {
                            return mScanMusicOnStorage(file);
                        } else {
                            return Flowable.just(file).filter(new Predicate<File>() {
                                @Override
                                public boolean test(File file) throws Exception {
                                    return file.exists() && file.canRead() && MusicUtil.isMusic(file);
                                }
                            });
                        }

                    }
                    //null is forbidden in RxJava2
                    return Flowable.empty();
                }
            });
        } else {
            return Flowable.empty();
        }
    }
}
