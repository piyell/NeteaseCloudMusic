package com.zsorg.neteasecloudmusic.models.beans;

/**
 * Created by piyel_000 on 2017/1/13.
 */

public class MusicBean {
    private String mPath;
    private String mName;

    public MusicBean(String path,String name) {
        mPath = path;
        mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
