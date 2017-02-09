package com.zsorg.neteasecloudmusic.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.LineItemDecorator;
import com.zsorg.neteasecloudmusic.MainAdapter;
import com.zsorg.neteasecloudmusic.MusicPlayerService;
import com.zsorg.neteasecloudmusic.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.presenters.MusicSearchPresenter;
import com.zsorg.neteasecloudmusic.presenters.PlayerPresenter;
import com.zsorg.neteasecloudmusic.presenters.SearchAdapter;
import com.zsorg.neteasecloudmusic.views.IPlayerView;
import com.zsorg.neteasecloudmusic.views.ISearchView;
import com.zsorg.neteasecloudmusic.widgets.PlaylistDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements IPlayerView,NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchView.OnCloseListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, SearchView.OnQueryTextListener, ISearchView, OnItemCLickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.radio)
    RadioGroup mRadioGroup;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.bottomLayout)
    View mBottomLayout;
    @BindView(R.id.layout_search)
    View layoutSearch;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
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
    private MainAdapter adapter;
    private PlayerPresenter mPlayerPresenter;
    private PlaylistDialog mDialog;
    private int mCurrentPosition=0;
    private MusicSearchPresenter mSearchPresenter;
    private SearchAdapter mSearchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mRvSearch.setLayoutManager(new LinearLayoutManager(this));
        mRvSearch.addItemDecoration(LineItemDecorator.getInstance());
        mSearchAdapter = new SearchAdapter(getLayoutInflater());
        mSearchAdapter.setOnItemClickListener(this);
        mRvSearch.setAdapter(mSearchAdapter);

        mNavigationView.setNavigationItemSelectedListener(this);
        mSearchView.setOnSearchClickListener(this);
        mSearchView.setOnCloseListener(this);

        adapter = new MainAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(adapter);
        mViewpager.addOnPageChangeListener(this);

        mRadioGroup.setOnCheckedChangeListener(this);

        mSearchView.setOnQueryTextListener(this);

        mPlayerPresenter = new PlayerPresenter(this);
        mSearchPresenter = new MusicSearchPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (null!=mPlayerPresenter) {
            mPlayerPresenter.requestMusicInfo();
            mPlayerPresenter.syncPlayerInfo();
            Fragment fragment = adapter.getItem(mCurrentPosition);
            if (null!=fragment) {
                fragment.onResume();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public void displayMusicInfo(MusicBean bean) {
        if (null!=mDialog && mDialog.isShowing()) {
            mDialog.setCurrentPlayPosition(PlayerManager.getInstance(this).getCurrentPosition());
        }
        if (null != bean) {
            mBottomLayout.setVisibility(layoutSearch.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
            tvSongName.setText(bean.getName());
            ImageCacheManager2.getInstance(this).displayImage(ivAlbum,bean.getPath());
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
    public void updateSearchResult(List<MusicBean> results) {
        mSearchAdapter.setDatas(results);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scan) {
            startActivity(new Intent(this, ScanMusicActivity.class));
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && layoutSearch.getVisibility()==View.VISIBLE) {
            mSearchView.setIconified(true);
            onClose();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * SearchView单击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        mRadioGroup.setVisibility(View.GONE);
        mBottomLayout.setVisibility(View.GONE);
        mViewpager.setVisibility(View.GONE);
        mSearchAdapter.setDatas(null);
        layoutSearch.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bottomLayout)
    public void onBottomLayoutClick(){
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.tv_config)
    public void onConfigClick(){
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.tv_quit)
    public void onQuitClick(){
        mPlayerPresenter.stop();
        finish();
        stopService(new Intent(this, MusicPlayerService.class));
    }

    @OnClick(R.id.iv_list)
    public void onPlaylistClick(){
        mDialog = new PlaylistDialog(this);
        mDialog.setPlaylist(PlayerManager.getInstance(this).getPlaylist());
        mDialog.setCurrentPlayPosition(PlayerManager.getInstance(this).getCurrentPosition());
        mDialog.show();
        mDialog.setPeekHeight(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        mPlayerPresenter.addToPlaylist(mSearchAdapter.getDataAtPosition(position));
        mPlayerPresenter.startPlay(true);
    }

    @OnClick(R.id.iv_play)
    public void onPlayClick(){
        if (PlayerManager.getInstance(this).isPause()) {
            mPlayerPresenter.startPlay(true);
        } else {
            mPlayerPresenter.Pause();
        }
    }

    @OnClick(R.id.iv_next)
    public void onNextClick(){
        mPlayerPresenter.nextMusic();
    }

    /**
     * SearchView关闭事件
     *
     */
    @Override
    public boolean onClose() {
        mRadioGroup.setVisibility(View.VISIBLE);
        mBottomLayout.setVisibility(View.VISIBLE);
        mViewpager.setVisibility(View.VISIBLE);

        layoutSearch.setVisibility(View.GONE);

        mPlayerPresenter.requestMusicInfo();
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        mRadioGroup.check(position == 0 ? R.id.action_music : R.id.action_list);
        Fragment fragment = adapter.getItem(mCurrentPosition);
        if (null!=fragment) {
            fragment.onResume();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        mViewpager.setCurrentItem(i == R.id.action_music ? 0 : 1);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query)) {
            mSearchPresenter.searchMusic(query);
        } else {
            mSearchAdapter.setDatas(null);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!TextUtils.isEmpty(newText)) {
            mSearchPresenter.searchMusic(newText);
        } else {
            mSearchAdapter.setDatas(null);
        }
        return true;
    }

}
