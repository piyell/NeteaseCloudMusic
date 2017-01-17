package com.zsorg.neteasecloudmusic.presenters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by piyel_000 on 2017/1/6.
 */

public class SingerHolder extends BaseHolder {
    @BindView(R.id.iv_singer)
    ImageView iv;
    @BindView(R.id.tv_singer)
    TextView tvTitle;
    @BindView(R.id.tv_songs_in_total)
    TextView tvContent;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    public SingerHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
