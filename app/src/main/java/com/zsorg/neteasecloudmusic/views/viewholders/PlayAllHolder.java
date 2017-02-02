package com.zsorg.neteasecloudmusic.views.viewholders;

import android.view.View;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class PlayAllHolder extends BaseItemHolder {
    @BindView(R.id.tv_songs_in_total)
    public TextView tvCount;

    public PlayAllHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

    }
}
