package com.zsorg.neteasecloudmusic.views.viewholders;

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

public class SingerHolder extends BaseItemHolder {
    @BindView(R.id.iv_singer)
    public ImageView iv;
    @BindView(R.id.tv_singer)
    public TextView tvTitle;
    @BindView(R.id.tv_songs_in_total)
    public TextView tvContent;
    @BindView(R.id.iv_right)
    public ImageView ivRight;
    public SingerHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
