package com.zsorg.neteasecloudmusic.callbacks;

import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/24.
 * E-mail:piyell@qq.com
 */

public interface OnTrackListener {
    void onTrack(int position);

    void onNext(MusicBean bean);

    void onPlayStateChange(boolean isPlay);
}
