package com.zsorg.neteasecloudmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanMusicActivity extends AppCompatActivity {

    private final int radius = 40;

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_scan_effect)
    ImageView ivScanEffect;
    private ValueAnimator searchAnimation;
    private TranslateAnimation scanAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_music);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initAnimations();

        playAnimations();
    }

    @OnClick({R.id.iv_close,R.id.tv_back})
    public void onCloseClick() {
        finish();
    }

    public void playAnimations() {
        ivScanEffect.startAnimation(scanAnimation);
        searchAnimation.start();
    }

    public void finishAnimations() {
        scanAnimation.cancel();
        searchAnimation.cancel();
    }

    private void initAnimations() {
        searchAnimation = ValueAnimator.ofFloat(0f, 1f);
        searchAnimation.setDuration(50000);
        searchAnimation.setRepeatCount(ValueAnimator.INFINITE);
        searchAnimation.setRepeatMode(ValueAnimator.REVERSE);
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
        ivScanEffect.clearAnimation();
    }

}
