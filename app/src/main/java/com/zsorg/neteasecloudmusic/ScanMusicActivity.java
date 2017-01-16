package com.zsorg.neteasecloudmusic;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.zsorg.neteasecloudmusic.presenters.ScanMusicPresenter;
import com.zsorg.neteasecloudmusic.views.IScanMusicView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanMusicActivity extends AppCompatActivity implements IScanMusicView{

    private final int radius = 40;

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_scan_effect)
    ImageView ivScanEffect;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_scan_or_cancel)
    TextView tvScanOrCancel;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    private ValueAnimator searchAnimation;
    private TranslateAnimation scanAnimation;
    private ScanMusicPresenter mPresenter;

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_music);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initAnimations();


        mPresenter = new ScanMusicPresenter(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 12);
    }

    @OnClick({R.id.iv_close,R.id.tv_back})
    public void onCloseClick() {
        finish();
    }

    @OnClick(R.id.tv_scan_or_cancel)
    public void onScanOrCancelClick(TextView view) {
        Boolean tag = (Boolean) view.getTag();
        if (null==tag || !tag) {
            view.setText(R.string.cancel_scan);
            view.setTag(true);
            mPresenter.scanMusic();
        } else {
            view.setTag(false);
            view.setText(R.string.scan_immediately);
        }


    }

    @Override
    public void startScan() {
        playAnimations();
        tvScan.setText(R.string.scanning_percent);
    }

    @Override
    public void finishScan() {
        finishAnimations();
        ivSearch.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_ok, null));
        tvScanOrCancel.setVisibility(View.GONE);
        tvBack.setVisibility(View.VISIBLE);
        tvScan.setText(R.string.scan_finish);
    }

    @Override
    public void onError(Throwable throwable) {
        finishAnimations();
        ivSearch.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_error, null));
        tvScanOrCancel.setText(R.string.scan_immediately);
        tvScanOrCancel.setTag(false);
        tvScan.setText(getString(R.string.error_msg,throwable.getMessage()));
    }

    public void playAnimations() {
        ivScanEffect.setVisibility(View.VISIBLE);
        ivScanEffect.startAnimation(scanAnimation);
        searchAnimation.start();
    }

    public void finishAnimations() {
        scanAnimation.cancel();
        searchAnimation.cancel();
        ivScanEffect.clearAnimation();
        ivScanEffect.setVisibility(View.GONE);
        ivSearch.clearAnimation();
        ViewHelper.setTranslationX(ivSearch, 0f);
        ViewHelper.setTranslationY(ivSearch, 0f);

    }

    private void initAnimations() {
        searchAnimation = ValueAnimator.ofFloat(0f, 1f);
        searchAnimation.setDuration(50000);
        searchAnimation.setRepeatCount(ValueAnimator.INFINITE);
        searchAnimation.setRepeatMode(ValueAnimator.RESTART);
        searchAnimation.setInterpolator(new LinearInterpolator());
        searchAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float angle = (valueAnimator.getAnimatedFraction() * 360);
                ViewHelper.setTranslationX(ivSearch, (float) Math.sin(angle) * radius);
                ViewHelper.setTranslationY(ivSearch, (float) Math.cos(angle) * radius);
            }
        });


        scanAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.61f);
        scanAnimation.setDuration(2000);
        scanAnimation.setRepeatCount(TranslateAnimation.INFINITE);
        scanAnimation.setRepeatMode(TranslateAnimation.RESTART);
    }


}
