package com.zsorg.neteasecloudmusic.models.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by piyel_000 on 2017/1/13.
 */

public class MusicBean implements Parcelable{
    private final String mSinger;
    private final String mAlbum;
    private String mPath;
    private String mName;
    private long mDuration;

    protected MusicBean(Parcel in) {
        mSinger = in.readString();
        mAlbum = in.readString();
        mPath = in.readString();
        mName = in.readString();
        mDuration = in.readLong();
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mSinger);
        parcel.writeString(mAlbum);
        parcel.writeString(mPath);
        parcel.writeString(mName);
        parcel.writeLong(mDuration);
    }

    public MusicBean(String name, String singer, String album, long duration, String path) {
        mPath = path;
        mName = name;
        mSinger = singer;
        mAlbum = album;
        mDuration = duration;
    }

    public String getSinger() {
        return mSinger;
    }

    public String getAlbum() {
        return mAlbum;
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

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }
}
