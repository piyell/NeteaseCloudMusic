package com.zsorg.neteasecloudmusic.presenters;

import android.content.Context;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;
import com.zsorg.neteasecloudmusic.views.ISearchView;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by piyel_000 on 2017/1/4.
 */

public class MusicSearchPresenter {
    private final DiskMusicDao mDao;
    private final ISearchView searchView;

    public MusicSearchPresenter(ISearchView searchView) {
        this.searchView = searchView;
        mDao = new DiskMusicDao(searchView.getContext());
    }

    public void searchMusic(String text) {
        Flowable.just(mDao.queryWords(text))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MusicBean>>() {
                    @Override
                    public void accept(List<MusicBean> list) throws Exception {
                        searchView.updateSearchResult(list);
                    }
                });
    }
}
