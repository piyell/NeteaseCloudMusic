package com.zsorg.neteasecloudmusic.utils;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/7.
 * E-mail:piyell@qq.com
 */

public class FileUtil {
    public static void deleteFileOnDisk(Iterable<MusicBean> list) {
        Flowable.fromIterable(list).flatMap(new Function<MusicBean, Publisher<File>>() {
            @Override
            public Publisher<File> apply(MusicBean bean) throws Exception {
                return Flowable.just(new File(bean.getPath()));
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(File file) {
                        file.delete();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
