package com.zsorg.neteasecloudmusic;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.utils.BlurUtil;
import com.zsorg.neteasecloudmusic.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.activity_player)
    View rootView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.play_viewpager)
    ViewPager viewPager;
    @BindView(R.id.iv_needle)
    ImageView needle;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    private MusicBean mMusicBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        ButterKnife.bind(this);


        mMusicBean = getIntent().getParcelableExtra(CONST.INTENT_PLAYER_EXTRA);

        tvEndTime.setText(TimeUtil.formatTime(mMusicBean.getDuration()));


        setSupportActionBar(toolbar);

        setTitle(mMusicBean.getName());
        toolbar.setSubtitle(mMusicBean.getSinger());

        toolbar.setNavigationOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (null!= actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewPager.setAdapter(new DiscPagerAdapter(getLayoutInflater()));

        viewPager.addOnPageChangeListener(new DiscPageChangeListener(viewPager,needle));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.setBackground(BlurUtil.createBlurredImageFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.play_bg_night), this, 20));
        } else {
            rootView.setBackgroundDrawable(BlurUtil.createBlurredImageFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.play_bg_night), this, 20));
        }

    }

    @OnClick(R.id.iv_play)
    public void onPlayClick(View view){
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(R.drawable.ic_pause);
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

}
