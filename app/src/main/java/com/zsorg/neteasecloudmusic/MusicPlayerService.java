package com.zsorg.neteasecloudmusic;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.PlaylistModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MusicPlayerService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_PLAY = "com.zsorg.neteasecloudmusic.action.PLAY";
    private static final String ACTION_PAUSE = "com.zsorg.neteasecloudmusic.action.PAUSE";
    private static final String ACTION_STOP ="com.zsorg.neteasecloudmusic.action.STOP";
    private static final String ACTION_SET = "com.zsorg.neteasecloudmusic.action.SET";

    private static final String EXTRA_PLAYLIST = "com.zsorg.neteasecloudmusic.extra.PLAYLIST_ID";
    private static final String EXTRA_IS_RESTART = "com.zsorg.neteasecloudmusic.extra.IS_RESTART";
    private static final String EXTRA_PLAYLIST_POSITION = "com.zsorg.neteasecloudmusic.extra.PLAYLIST_POSITION";


    private final MusicPlayerBinder mBinder;

    private List<MusicBean> mCurrentPlaylist;

    public MusicPlayerService() {
        super("MyIntentService");
        mBinder = new MusicPlayerBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        context.bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPlay(Context context,boolean isRestart) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.putExtra(EXTRA_IS_RESTART, isRestart);
        intent.setAction(ACTION_PLAY);
        context.startService(intent);
    }

    public static void startActionSet(Context context, ArrayList<MusicBean> playlist, int position) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_SET);
        intent.putParcelableArrayListExtra(EXTRA_PLAYLIST, playlist);
        intent.putExtra(EXTRA_PLAYLIST_POSITION, position);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPause(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_PAUSE);
        context.startService(intent);
    }

    public static void startActionStop(Context context) {
        Intent intent = new Intent(context, MusicPlayerService.class);
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PLAY.equals(action)) {
                boolean isRestart = intent.getBooleanExtra(EXTRA_IS_RESTART, true);
                handleActionPlay(isRestart);
            } else if (ACTION_PAUSE.equals(action)) {
                handleActionPause();
            }else if (ACTION_SET.equals(action)) {
                ArrayList<MusicBean> list = intent.getParcelableArrayListExtra(EXTRA_PLAYLIST);
                if (null != list) {
                    mCurrentPlaylist = list;
                }
                final int position = intent.getIntExtra(EXTRA_PLAYLIST_POSITION,CONST.DEFAULT_PLAYLIST_POSITION);
                handleActionSetPlaylist(position);
            }
        }
    }

    private void handleActionPause() {
        PlayerManager playerManager = PlayerManager.getInstance(this);
        playerManager.setPauseMusic(this,true);
    }

    /**
     * Handle action Play in the provided background thread with the provided
     * parameters.
     * @param isRestart
     */
    private void handleActionPlay(boolean isRestart) {
        PlayerManager playerManager = PlayerManager.getInstance(this);
        if (isRestart) {
            playerManager.playMusic(this);
        } else {
            playerManager.setPauseMusic(this,false);
        }
    }

    private void handleActionSetPlaylist(int position) {
        PlayerManager playerManager = PlayerManager.getInstance(this);
        if (mCurrentPlaylist != null) {
            playerManager.setCurrentPlaylist(mCurrentPlaylist);
        }
        playerManager.setCurrentPosition(position);
    }

//    private void playMusic(final int position) {
//        if (position < mCurrentPlaylist.size()) {
//            PlayerManager.newInstance(this).playMusic(this, mCurrentPlaylist.get(position).getPath()).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    playMusic(position + 1);
//                }
//            });
//        }
//    }

    /**
     * Handle action Stop in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStop() {
        PlayerManager playerManager = PlayerManager.getInstance(this);
//        playerManager.stopMusic()
    }

    private class MusicPlayerBinder extends Binder {

    }
}
