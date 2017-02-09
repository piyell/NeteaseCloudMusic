package com.zsorg.neteasecloudmusic.presenters;

import com.zsorg.neteasecloudmusic.models.ConfigModel;
import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;
import com.zsorg.neteasecloudmusic.views.IConfigView;

import java.util.List;

import io.reactivex.Observable;
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

public class ConfigPresenter {

    private final IConfigView configView;
    private final ConfigModel mModel;

    public ConfigPresenter(IConfigView configView) {
        this.configView = configView;
        mModel = ConfigModel.getInstance(configView.getContext());
    }

    public void requestLoadingList() {
        Observable.just(mModel.getConfigList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ConfigBean>>() {
                    @Override
                    public void accept(List<ConfigBean> list) throws Exception {
                        configView.displayConfigList(list);
                    }
                });

    }
}
