package com.zsorg.neteasecloudmusic.presenters;

import com.zsorg.neteasecloudmusic.ConfigCallback;
import com.zsorg.neteasecloudmusic.models.ConfigModel;
import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;
import com.zsorg.neteasecloudmusic.views.IConfigView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/9.
 * E-mail:piyell@qq.com
 */

public class ConfigPresenter implements ConfigCallback {

    private final IConfigView configView;
    private ConfigModel mModel;

    public ConfigPresenter(IConfigView configView) {
        this.configView = configView;
    }

    public void requestLoadingList() {
        Observable.create(new ObservableOnSubscribe<List<ConfigBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ConfigBean>> e) throws Exception {
                mModel = ConfigModel.getInstance(configView.getContext());
                e.onNext(mModel.getConfigList());
                mModel.setConfigCallback(ConfigPresenter.this);
            }
        })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ConfigBean>>() {
                    @Override
                    public void accept(List<ConfigBean> list) throws Exception {
                        configView.displayConfigList(list);
                    }
                });

    }

    public void setMusicOrder(int order) {
        mModel.setMusicOrder(order);
    }

    public int getMusicOrder() {
        return mModel.getMusicOrder();
    }

    public void setIsFilter60s(boolean isFilter) {
        mModel.setIsFilter60s(isFilter);
    }

    @Override
    public void onMusicOrderConfigChanged(int newOrder) {
        configView.displayConfigList(mModel.getConfigList());
    }

    @Override
    public void onIsShow60sConfigChanged(boolean isShow) {
        configView.displayConfigList(mModel.getConfigList());
    }
}
