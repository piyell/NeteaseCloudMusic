package com.zsorg.neteasecloudmusic.views.viewholders;

import android.view.View;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by piyel_000 on 2017/1/5.
 */

public class SongListItemHolder extends BaseItemHolder {
    @BindView(R.id.tv_song_name)
    public TextView tvTitle;
    @BindView(R.id.tv_songs_in_total)
    public TextView tvContent;

    public SongListItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }




}
