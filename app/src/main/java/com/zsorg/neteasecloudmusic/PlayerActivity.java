package com.zsorg.neteasecloudmusic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.presenters.PlayerPresenter;
import com.zsorg.neteasecloudmusic.utils.BlurUtil;
import com.zsorg.neteasecloudmusic.utils.MP3Util;
import com.zsorg.neteasecloudmusic.utils.MusicUtil;
import com.zsorg.neteasecloudmusic.utils.TimeUtil;
import com.zsorg.neteasecloudmusic.views.IPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener,IPlayerView {

    @BindView(R.id.activity_player)
    View rootView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.play_viewpager)
    ViewPager viewPager;
    @BindView(R.id.iv_needle)
    ImageView needle;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.sb_progress)
    SeekBar seekBar;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    private PlayerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);


        int id = getIntent().getIntExtra(CONST.INTENT_PLAYLIST_ID, CONST.DEFAULT_PLAYLIST_ID);
        int position = getIntent().getIntExtra(CONST.INTENT_PLAYLIST_POSITION, 0);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (null!= actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewPager.setAdapter(new DiscPagerAdapter(getLayoutInflater()));

        viewPager.addOnPageChangeListener(new DiscPageChangeListener(viewPager,needle));


        mPresenter = new PlayerPresenter(this);
        mPresenter.requestMusicInfo(id,position);
        mPresenter.setPlaylist(id, position);

        ivPlay.setImageResource(PlayerManager.getInstance(this).isPause() ? R.drawable.ic_play : R.drawable.ic_pause);


        setBlurBackground(BitmapFactory.decodeResource(getResources(), R.drawable.play_bg_night));

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateTrackInfo(int position) {
        seekBar.setProgress(position);
        tvStartTime.setText(TimeUtil.formatTime(position));
    }

    @Override
    public void displayMusicInfo(MusicBean bean) {
        if (null!=bean) {
            tvEndTime.setText(TimeUtil.formatTime(bean.getDuration()));
            seekBar.setMax((int) bean.getDuration());
            setTitle(bean.getName());
            toolbar.setTitle(bean.getName());
            toolbar.setSubtitle(bean.getSinger());
//            ImageCacheManager2.getInstance(this).displayImage(ivPlay,bean.getPath());
        }
    }

    @OnClick(R.id.iv_play)
    public void onPlayClick(View view){
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            PlayerManager manager = PlayerManager.getInstance(this);
            if (manager.isPause()) {
                mPresenter.startPlay(false);
                imageView.setImageResource(R.drawable.ic_pause);
            } else {
                mPresenter.Pause();
                imageView.setImageResource(R.drawable.ic_play);
            }
        }
    }

    @OnClick(R.id.iv_previous)
    public void onPreviousClick(View view){
    }

    @OnClick(R.id.iv_next)
    public void onNextClick(View view){
    }

    /**
     * NavigationIcon onClick
     * @param view
     */
    @Override
    public void onClick(View view) {
        finish();
    }

    private void setBlurBackground(Bitmap background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.setBackground(BlurUtil.createBlurredImageFromBitmap(background, this, 20));
        } else {
            rootView.setBackgroundDrawable(BlurUtil.createBlurredImageFromBitmap(background, this, 20));
        }
    }
}
