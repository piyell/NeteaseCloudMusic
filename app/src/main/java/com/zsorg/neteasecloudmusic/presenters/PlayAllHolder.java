package com.zsorg.neteasecloudmusic.presenters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class PlayAllHolder extends BaseHolder {
    @BindView(R.id.tv_songs_in_total)
    TextView tvCount;

    public PlayAllHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
