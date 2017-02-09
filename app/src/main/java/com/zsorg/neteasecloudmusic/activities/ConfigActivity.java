package com.zsorg.neteasecloudmusic.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zsorg.neteasecloudmusic.ConfigAdapter;
import com.zsorg.neteasecloudmusic.ConfigHolder;
import com.zsorg.neteasecloudmusic.LineItemDecorator;
import com.zsorg.neteasecloudmusic.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.ConfigModel;
import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;
import com.zsorg.neteasecloudmusic.presenters.ConfigPresenter;
import com.zsorg.neteasecloudmusic.views.IConfigView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigActivity extends AppCompatActivity implements View.OnClickListener, IConfigView {

    @BindView(R.id.rv_config)
    RecyclerView rvConfig;
    private ConfigAdapter mAdapter;
    private ConfigPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (null!=actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(this);
        }

        ButterKnife.bind(this);

        rvConfig.setLayoutManager(new LinearLayoutManager(this));
        rvConfig.addItemDecoration(LineItemDecorator.getInstance());
        mAdapter = new ConfigAdapter(getLayoutInflater());
//        mAdapter.setOnItemClickListener(this);
        rvConfig.setAdapter(mAdapter);

        mPresenter = new ConfigPresenter(this);
        mPresenter.requestLoadingList();
    }

    @Override
    public void onClick(View view) {
        finish();
    }



    @Override
    public void displayConfigList(List<ConfigBean> list) {
        mAdapter.setDataList(list);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
