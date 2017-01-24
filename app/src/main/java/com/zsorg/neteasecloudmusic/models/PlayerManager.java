package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.zsorg.neteasecloudmusic.OnTrackListener;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/24.
 * E-mail:piyell@qq.com
 */

public class PlayerManager implements MediaPlayer.OnCompletionListener {

    private static PlayerManager ourInstance;
    private static MediaPlayer mPlayer;

    private List<MusicBean> mCurrentPlaylist;
    private int mPosition;
    private OnTrackListener onTrackListener;

    public static PlayerManager getInstance(Context context) {
        if (null == ourInstance) {
            synchronized (PlayerManager.class) {
                ourInstance = new PlayerManager(context);
            }
        }
        return ourInstance;
    }

    public void setPlaylistBean(List<MusicBean> beanList) {
        mCurrentPlaylist = beanList;
    }

    public void setCurrentPosition(int position) {
        if (mPosition!=position) {
            mPosition = position;
            if (null != mPlayer) {
                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(mCurrentPlaylist.get(position).getPath());
                    mPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public MediaPlayer playMusic(Context context) {
        String path = mCurrentPlaylist.get(mPosition).getPath();
        if (null != context && null != path) {
            if (null == mPlayer) {
                getPlayer(context, Uri.fromFile(new File(path)));
            } else {
                mPlayer.setOnCompletionListener(this);
            }
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            mPlayer.reset();

            try {
                mPlayer.setDataSource(path);
                mPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mPlayer.start();
        }
        return mPlayer;
    }

    public void setPauseMusic(Context context,boolean isPause) {
        if (null != mPlayer) {
            if (isPause && mPlayer.isPlaying()) {
                mPlayer.pause();
            } else if (!isPause && !mPlayer.isPlaying()){
                mPlayer.start();
            }
        } else if (!isPause) {
            playMusic(context);
        }
    }

    public void setOnTrackListener(OnTrackListener listener) {
        onTrackListener = listener;
    }

    public boolean isPause() {
        if (null != mPlayer && mPlayer.isPlaying()) {
            return false;
        }
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        nextMusic();
    }

    public void nextMusic() {
        if (null != mPlayer) {
            if (++mPosition<mCurrentPlaylist.size()) {
                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(mCurrentPlaylist.get(mPosition).getPath());
                    mPlayer.setOnCompletionListener(this);
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void getPlayer(Context context, Uri uri) {
        mPlayer = MediaPlayer.create(context, uri);
        if (null != mPlayer) {
            mPlayer.setLooping(false);
            mPlayer.stop();
            mPlayer.setOnCompletionListener(this);
        }
    }

    private PlayerManager(Context context) {
        getPlayer(context, Uri.EMPTY);
        Observable.interval(300, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (null != mPlayer && null!=onTrackListener) {
//                    Log.e("tag", "getCurrentPosition==" + mPlayer.getCurrentPosition());
                    onTrackListener.onTrack(mPlayer.getCurrentPosition());
                }
            }
        });
    }

}
