package com.zsorg.neteasecloudmusic.utils;

import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by piyel_000 on 2017/1/8.
 */

public class ViewUtil {
    public static void rotateView(View view,float fromDegree,float toDegree){
        if (Build.VERSION.SDK_INT < 11) {
            RotateAnimation animation = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(100);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        } else {
            view.setRotation(toDegree);
        }
    }
}
