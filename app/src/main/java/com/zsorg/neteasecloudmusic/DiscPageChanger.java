package com.zsorg.neteasecloudmusic;

import android.support.v4.view.ViewPager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by piyel_000 on 2017/1/10.
 */

class DiscPageChanger implements ViewPager.OnPageChangeListener, Animator.AnimatorListener {

    private final float START_VALUE = 0;
    private final float END_VALUE = -30;

    private final ViewPager mViewPager;
    private final ImageView mNeedle;
    private final List<MusicBean> playlist;
    private ObjectAnimator mAnimator;
    private boolean playAfterEndAnimation;
    private boolean isPlayEnd2Start = false;
    private int mViewPagerState;


    public DiscPageChanger(ViewPager viewPager, ImageView needle) {
        mViewPager = viewPager;
        mNeedle = needle;

        mAnimator = ObjectAnimator.ofFloat(needle, "rotation", START_VALUE, END_VALUE);
        mAnimator.setDuration(200);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addListener(this);

        playlist = PlayerManager.getInstance(needle.getContext()).getPlaylist();
    }


    @Override
    public void onPageScrolled(int position, final float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
//        if (position == 0 || position == playlist.size() ) {
//            mViewPager.setCurrentItem(position == 0 ? playlist.size() : 1, false);
//        } else {
//
//        }
        PlayerManager manager = PlayerManager.getInstance(mViewPager.getContext());
        if (manager.getCurrentPosition()!=position) {
            manager.setCurrentPosition(position);
            manager.playMusic(mViewPager.getContext());
        }

    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {//固定状态
            mViewPagerState = state;
            playAnimation();
        } else {//正在滚动
            mViewPagerState = state;
            playAfterEndAnimation = false;
            if (((float) mAnimator.getAnimatedValue()) != END_VALUE) {
                startStart2EndAnimation();
            }
        }
    }

    public void startStart2EndAnimation() {
        mAnimator.setFloatValues((float) mAnimator.getAnimatedValue(), END_VALUE);
//        mAnimator.setFloatValues((float)START_VALUE, END_VALUE);
        if (!mAnimator.isRunning()) {
            mAnimator.start();
        } else {
//            isPlayEnd2Start = true;
        }
    }


    public void startEnd2StartAnimation() {
//        if (!PlayerManager.getInstance(mNeedle.getContext()).isPause()) {
            mAnimator.setFloatValues((float) mAnimator.getAnimatedValue(), START_VALUE);
//            mAnimator.setFloatValues((float) END_VALUE, START_VALUE);
            if (!mAnimator.isRunning()) {
                mAnimator.start();
            } else {
                isPlayEnd2Start = true;
            }
//        }
    }

    private void playAnimation() {
        if (mViewPagerState == ViewPager.SCROLL_STATE_IDLE) {
            Observable.timer(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (mViewPagerState == ViewPager.SCROLL_STATE_IDLE) {
                                if (((float) mAnimator.getAnimatedValue()) == END_VALUE) {

                                    startEnd2StartAnimation();
                                }
                            }
                        }
                    });
        }
    }


    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (playAfterEndAnimation) {
            playAfterEndAnimation = false;
            mAnimator.start();
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        if (playAfterEndAnimation) {
            playAfterEndAnimation = false;
            mAnimator.start();
        }
    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
