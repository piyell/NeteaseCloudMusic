package com.zsorg.neteasecloudmusic.models.beans;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/9.
 * E-mail:piyell@qq.com
 */

public class ConfigBean {
    private String mGroupTitle;
    private String mItemTitle;
    private String mItemContent;
    private String mItemRight;
    private boolean mSwitchVisible;
    private boolean mIsSwitchChecked;
    private boolean mIsBottomVisible;

    public ConfigBean(String groupTitle, String itemTitle, String content, String itemRight, boolean isSwitchVisible, boolean isSwitchChecked, boolean isBottomVisible) {
        mGroupTitle = groupTitle;
        mItemTitle = itemTitle;
        mItemContent = content;
        mItemRight = itemRight;
        mSwitchVisible = isSwitchVisible;
        mIsSwitchChecked = isSwitchChecked;
        mIsBottomVisible = isBottomVisible;
    }

    public String getGroupTitle() {
        return mGroupTitle;
    }

    public void setGroupTitle(String mGroupTitle) {
        this.mGroupTitle = mGroupTitle;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public void setItemTitle(String mItemTitle) {
        this.mItemTitle = mItemTitle;
    }

    public String getItemContent() {
        return mItemContent;
    }

    public void setItemContent(String mItemContent) {
        this.mItemContent = mItemContent;
    }

    public String getItemRight() {
        return mItemRight;
    }

    public void setItemRight(String mItemRight) {
        this.mItemRight = mItemRight;
    }

    public boolean isSwitchVisible() {
        return mSwitchVisible;
    }

    public void setRightVisible(boolean mRightVisible) {
        this.mSwitchVisible = mRightVisible;
    }

    public void setSwitchChecked(boolean isChecked) {
        this.mIsSwitchChecked = isChecked;
    }

    public boolean isSwitchChecked() {
        return mIsSwitchChecked;
    }

    public boolean isBottomVisible() {
        return mIsBottomVisible;
    }

    public void setIsBottomVisible(boolean mIsBottomVisible) {
        this.mIsBottomVisible = mIsBottomVisible;
    }
}
