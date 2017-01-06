package com.zsorg.neteasecloudmusic;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by piyel_000 on 2017/1/5.
 */

class SongListItemHolder extends BaseHolder{
    @BindView(R.id.tv_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_songs_in_total)
    TextView tvAlbumName;

    public SongListItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
