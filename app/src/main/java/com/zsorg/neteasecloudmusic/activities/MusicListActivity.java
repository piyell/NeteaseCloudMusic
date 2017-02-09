package com.zsorg.neteasecloudmusic.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.LineItemDecorator;
import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.presenters.MusicSingleAdapter;
import com.zsorg.neteasecloudmusic.presenters.PlayerPresenter;
import com.zsorg.neteasecloudmusic.views.IPlayerView;
import com.zsorg.neteasecloudmusic.widgets.PlaylistDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicListActivity extends AppCompatActivity implements IPlayerView,OnItemCLickListener, View.OnClickListener {

    private static final String EXTRA_MUSIC_LIST = "com.zsorg.neteasecloudmusic.extra.MUSIC_LIST";
    private static final String EXTRA_TITLE = "com.zsorg.neteasecloudmusic.extra.TITLE";

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;
    @BindView(R.id.iv_album)
    ImageView ivAlbum;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.bottomLayout)
    View mBottomLayout;
    private MusicSingleAdapter mAdapter;
    private PlayerPresenter mPresenter;
    private PlaylistDialog mDialog;

    public static void startMusicList(Context context,String title, ArrayList<MusicBean> list) {

        Intent intent = new Intent(context, MusicListActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_MUSIC_LIST, list);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(LineItemDecorator.getInstance());
        mAdapter = new MusicSingleAdapter(getLayoutInflater());
        mAdapter.setOnItemClickListener(this);
        rvList.setAdapter(mAdapter);

        Intent intent = getIntent();

        String singer = intent.getStringExtra(EXTRA_TITLE);
        singer = singer == null ? getString(R.string.unknown) : singer;
        setTitle(singer);
        toolbar.setTitle(singer);

        if (null != intent) {
            ArrayList<MusicBean> list = intent.getParcelableArrayListExtra(EXTRA_MUSIC_LIST);
            mAdapter.setDatas(list);
        }

        mPresenter = new PlayerPresenter(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position>=0) {
            MusicPlayerService.startActionSet(this,mAdapter.getDataList(),position>0?position-1:0);
            MusicPlayerService.startActionPlay(this,true);
        }
    }

    @OnClick(R.id.bottomLayout)
    public void onBottomLayoutClick(){
        startActivity(new Intent(this,PlayerActivity.class));
    }


    @OnClick(R.id.iv_list)
    public void onPlaylistClick(){
        mDialog = new PlaylistDialog(this);
        mDialog.setPlaylist(PlayerManager.getInstance(this).getPlaylist());
        mDialog.setCurrentPlayPosition(PlayerManager.getInstance(this).getCurrentPosition());
        mDialog.show();
    }

    @OnClick(R.id.iv_play)
    public void onPlayClick(){
        if (PlayerManager.getInstance(this).isPause()) {
            mPresenter.startPlay(true);
        } else {
            mPresenter.Pause();
        }
    }

    @OnClick(R.id.iv_next)
    public void onNextClick(){
        mPresenter.nextMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mPresenter) {
            mPresenter.requestMusicInfo();
            mPresenter.syncPlayerInfo();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void displayMusicInfo(MusicBean bean) {
        if (null!=mDialog && mDialog.isShowing()) {
            mDialog.setCurrentPlayPosition(PlayerManager.getInstance(this).getCurrentPosition());
        }
        if (null != bean) {
            ImageCacheManager2.getInstance(this).displayImage(ivAlbum,bean.getPath());
            mBottomLayout.setVisibility(View.VISIBLE);
            tvSongName.setText(bean.getName());
            String singer = bean.getSinger();
            singer = null == singer ? getString(R.string.unknown) : singer;
            tvSinger.setText(singer);
            progressBar.setMax((int) bean.getDuration());
        } else {
            mBottomLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateTrackInfo(int position) {
        progressBar.setProgress(position);
    }

    @Override
    public void updatePlayButton(boolean isPlay) {
        int resID = isPlay? R.drawable.ic_pause_primary : R.drawable.ic_play_primary;
        ivPlay.setImageDrawable(VectorDrawableCompat.create(getResources(), resID, null));
    }
}
