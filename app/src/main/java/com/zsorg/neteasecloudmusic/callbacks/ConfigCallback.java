package com.zsorg.neteasecloudmusic.callbacks;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/9.
 * E-mail:piyell@qq.com
 */

public interface ConfigCallback {
    void onMusicOrderConfigChanged(int newOrder);
    void onIsShow60sConfigChanged(boolean isShow);
}
