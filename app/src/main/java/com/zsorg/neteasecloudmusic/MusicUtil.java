package com.zsorg.neteasecloudmusic;

import java.io.File;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/15.
 * E-mail:piyell@qq.com
 */

public class MusicUtil {

    public static boolean isMusic(File file) {
        return null!=file && file.getName().toLowerCase().endsWith(".mp3");
    }
}
