package com.zsorg.neteasecloudmusic;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by piyel_000 on 2017/1/10.
 */

class DiscPageChangeListener implements ViewPager.OnPageChangeListener, Animator.AnimatorListener {

    private final float START_VALUE = 0;
    private final float END_VALUE = -20;

    private final ViewPager mViewPager;
    private final ImageView mNeedle;
    private ObjectAnimator mAnimator;
    private boolean playAfterEndAnimation;
    private int mViewPagerState;

    public DiscPageChangeListener(ViewPager viewPager, ImageView needle) {
        mViewPager = viewPager;
        mNeedle = needle;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mAnimator = ObjectAnimator.ofFloat(needle, "rotation", START_VALUE, END_VALUE);
            mAnimator.setDuration(500);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addListener(this);
        } else {
        }


    }


    @Override
    public void onPageScrolled(int position, final float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0 || position == 4) {
            mViewPager.setCurrentItem(position == 0 ? 3 : 1,false);
        }

    }

    @Override
    public void onPageScrollStateChanged(final int state) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {//固定状态
//                if (((float) mAnimator.getAnimatedValue()) == END_VALUE) {
//
//                    mAnimator.setFloatValues(END_VALUE, START_VALUE);
//                    if (!mAnimator.isRunning()) {
//                        mAnimator.start();
//                    }
//                }
                mViewPagerState = state;
                playAnimation();
            } else {//正在滚动
                mViewPagerState = state;
                playAfterEndAnimation = false;
                if (((float) mAnimator.getAnimatedValue()) == START_VALUE) {
                    mAnimator.setFloatValues(START_VALUE, END_VALUE);
                    if (!mAnimator.isRunning()) {
                        mAnimator.start();
                    }
                }
            }

        }
    }

    private void playAnimation() {
        if (mViewPagerState == ViewPager.SCROLL_STATE_IDLE) {
//            if (((float) mAnimator.getAnimatedValue()) == END_VALUE) {
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (mViewPagerState == ViewPager.SCROLL_STATE_IDLE) {
                                    if (((float) mAnimator.getAnimatedValue()) == END_VALUE) {

                                        mAnimator.setFloatValues(END_VALUE, START_VALUE);
                                        if (!mAnimator.isRunning()) {
                                            mAnimator.start();
                                        } else {
                                            playAfterEndAnimation = true;
                                        }
                                    }
                                }
                            }
                        });
//            }
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
