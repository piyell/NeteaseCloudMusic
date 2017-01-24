package com.zsorg.neteasecloudmusic;

import android.app.Application;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/24.
 * E-mail:piyell@qq.com
 */

public class MusicApplication extends Application implements ServiceConnection {
    @Override
    public void onCreate() {
        super.onCreate();
        MusicPlayerService.startService(this,this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
