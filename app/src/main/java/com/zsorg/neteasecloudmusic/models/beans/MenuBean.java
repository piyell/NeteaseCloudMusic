package com.zsorg.neteasecloudmusic.models.beans;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/31.
 * E-mail:piyell@qq.com
 */

public class MenuBean {
    private String mName;
    private int mIconID;

    public MenuBean() {

    }

    public MenuBean(String name, int iconID) {
        mName = name;
        mIconID = iconID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getIconID() {
        return mIconID;
    }

    public void setIconID(int iconID) {
        this.mIconID = iconID;
    }
}
