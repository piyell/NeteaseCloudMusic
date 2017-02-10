package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.zsorg.neteasecloudmusic.CONST;
import com.zsorg.neteasecloudmusic.callbacks.OnTrackListener;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
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
    private final ConfigModel mConfigModel;

    private List<MusicBean> mCurrentPlaylist;
    private int mPosition;
    private OnTrackListener onTrackListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static PlayerManager getInstance(Context context) {
        if (null == ourInstance) {
            synchronized (PlayerManager.class) {
                ourInstance = new PlayerManager(context);
            }
        }
        return ourInstance;
    }

    @Override
    protected void finalize() throws Throwable {
        if (null != mPlayer) {
            mPlayer.release();
        }
        super.finalize();
    }

    public void setCurrentPlaylist(List<MusicBean> beanList) {
        mCurrentPlaylist = beanList;
    }

    public void setCurrentPositionOnly(int position) {
        mPosition = position;
    }

    public void setCurrentPosition(int position) {
        if (true) {//mPosition != position
            mPosition = position;
            if (null != mPlayer) {
                try {
                    final boolean playing = mPlayer.isPlaying();
                    mPlayer.reset();
                    mPlayer.setDataSource(mCurrentPlaylist.get(position).getPath());
                    mPlayer.prepare();
                    if (null != onTrackListener) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onTrackListener.onNext(mCurrentPlaylist.get(mPosition));
                                onTrackListener.onTrack(0);
                                onTrackListener.onPlayStateChange(playing);
                            }
                        });
                    }
                    if (playing) {
                        mPlayer.start();
                    }
                    mPlayer.setOnCompletionListener(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public List<MusicBean> getPlaylist() {
        return mCurrentPlaylist;
    }

    public int getCurrentPosition() {
        return mPosition;
    }

    public MusicBean getMusicBean() {

        if (null != mCurrentPlaylist && mPosition >= 0 && mPosition < mCurrentPlaylist.size()) {
            return mCurrentPlaylist.get(mPosition);
        }
        return null;
    }

    public MediaPlayer playMusic(Context context) {
        if (null != mCurrentPlaylist && mPosition >= 0 && mPosition < mCurrentPlaylist.size()) {
            final MusicBean bean = mCurrentPlaylist.get(mPosition);

            if (null == mPlayer) {

                getPlayer(context, Uri.fromFile(new File(bean.getPath())));
            }
            mPlayer.start();
            if (null != onTrackListener) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onTrackListener.onNext(bean);
                        onTrackListener.onPlayStateChange(true);
                    }
                });

            }
        }
        return mPlayer;
    }



    public void stop() {
        if (null != mPlayer) {
            mPlayer.stop();
            if (null != onTrackListener) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onTrackListener.onNext(null);
                        onTrackListener.onPlayStateChange(false);
                    }
                });

            }
        }
    }

    public void setPauseMusic(Context context, boolean isPause) {
        if (null != mPlayer) {

            if (isPause && mPlayer.isPlaying()) {
                mPlayer.pause();
                if (null != onTrackListener) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onTrackListener.onPlayStateChange(false);
                        }
                    });
                }
            } else if (!isPause && !mPlayer.isPlaying()) {
                mPlayer.start();
                if (null != onTrackListener) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onTrackListener.onPlayStateChange(true);
                        }
                    });
                }
            }
        } else if (!isPause) {
            playMusic(context);
        }
    }

    public void setOnTrackListener(OnTrackListener listener) {
        onTrackListener = listener;
        if (null != listener) {
            if (null != mPlayer) {
                listener.onPlayStateChange(mPlayer.isPlaying());
            } else {
                listener.onPlayStateChange(false);
            }

        }
    }

    public boolean isPause() {
        if (null != mPlayer && mPlayer.isPlaying()) {
            return false;
        }
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (null!=mPlayer && !mPlayer.isPlaying() && mPlayer.getCurrentPosition()+200>=mPlayer.getDuration()) {
            int musicOrder = mConfigModel.getMusicOrder();
            switch (musicOrder) {
                case CONST.MUSIC_ORDER_SINGLE:
                    setCurrentPosition(mPosition);
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (null != onTrackListener) {
                                    onTrackListener.onPlayStateChange(mPlayer.isPlaying());
                                }
                            }
                        });

                    }

                    break;
                case CONST.MUSIC_ORDER_LIST:
                    nextMusic();
                    break;
                case CONST.MUSIC_ORDER_RANDOM:
                    Random random = new Random();
                    int position = random.nextInt(mCurrentPlaylist.size());
                    setCurrentPosition(position);
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (null != onTrackListener) {
                                onTrackListener.onPlayStateChange(mPlayer.isPlaying());
                            }
                        }
                    });
                    break;
            }

        }
    }


    public void preMusic() {
        playMusicAtPosition(false);
    }

    public void nextMusic() {
        playMusicAtPosition(true);
    }

    public void playMusicAtPosition(boolean isNext) {
        if (null != mPlayer) {
            int i = isNext ? 1 : -1;
            if (mPosition+i < mCurrentPlaylist.size() && mPosition+i>=0) {
                mPosition+=i;
                try {
                    mPlayer.reset();
                    final MusicBean bean = mCurrentPlaylist.get(mPosition);
                    mPlayer.setDataSource(bean.getPath());
                    mPlayer.setOnCompletionListener(this);
                    mPlayer.prepare();
                    mPlayer.start();
                    if (null != onTrackListener) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onTrackListener.onPlayStateChange(false);
                                onTrackListener.onNext(bean);
                                onTrackListener.onPlayStateChange(true);
                            }
                        });

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    if (null != onTrackListener) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onTrackListener.onPlayStateChange(false);
                                final MusicBean bean = mCurrentPlaylist.get(mPosition);
                                onTrackListener.onNext(bean);
                            }
                        });

                    }
                }

            } else {
                mPosition = -1;
                playMusicAtPosition(true);
            }
        }
    }

    private void getPlayer(Context context, Uri uri) {
        mPlayer = MediaPlayer.create(context, uri);
        if (null != mPlayer) {
            mPlayer.setLooping(false);
//            mPlayer.reset();
            mPlayer.setOnCompletionListener(this);
        }
    }

    private PlayerManager(Context context) {
        mConfigModel = ConfigModel.getInstance(context);
        getPlayer(context, Uri.EMPTY);
        Observable.interval(300, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (null != mPlayer && null != onTrackListener) {
//                    Log.e("tag", "getCurrentPosition==" + mPlayer.getCurrentPosition())
                    onTrackListener.onTrack(mPlayer.getCurrentPosition());
                }
            }
        });
    }

}
