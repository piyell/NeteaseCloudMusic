package com.zsorg.neteasecloudmusic.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.zsorg.neteasecloudmusic.CONST;
import com.zsorg.neteasecloudmusic.utils.MP3Util;
import com.zsorg.neteasecloudmusic.utils.MusicUtil;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by piyel_000 on 2016/2/6.
 * QQ:479683952.
 */

public class ImageCacheManager2 implements Handler.Callback {
    private static final int HARD_CACHE_CAPACITY = 30;

    private static final int nThreads = Runtime.getRuntime().availableProcessors();

    private Context context;
    private LruCache<String, Bitmap> fstCacheMap;

    private boolean mPauseFlag = false;

    private ConcurrentHashMap<ImageView, String> requestMap = new ConcurrentHashMap<>();
    private Handler mMainHandler;
    private static ImageCacheManager2 imageCacheManager;
    private ExecutorService executorService;
    private final int maxMemory;


    private ImageCacheManager2(Context context) {
        this.context = context;
        mMainHandler = new Handler(context.getMainLooper(), this);

        maxMemory = (int) Runtime.getRuntime().maxMemory();

        fstCacheMap = new LruCache<String, Bitmap>(maxMemory / 8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public boolean isPause() {
        return mPauseFlag;
    }

    public void setPause(boolean mPauseFlag) {
        this.mPauseFlag = mPauseFlag;
        if (!mPauseFlag) {
            if (null == executorService) {
                executorService = Executors.newFixedThreadPool(nThreads);
            }

        }
    }

    public void displayImage(ImageView imageView, String path) {

        Bitmap bitmap = loadCachedBitmap(path);
        if (null != bitmap) {
            imageView.setImageBitmap(bitmap);
            imageView.setTag(path);
            return;
        }

        imageView.setTag(path);

        if (null == executorService) {
            executorService = Executors.newFixedThreadPool(nThreads);
        }
        executorService.execute(new LoadImageRunnable(imageView,path));
    }

    public static ImageCacheManager2 getInstance(Context context) {
        if (null == imageCacheManager) {
            imageCacheManager = new ImageCacheManager2(context.getApplicationContext());
        }
        return imageCacheManager;
    }

    public Bitmap loadCachedBitmap(String mPath) {
//        if (null == mPath) {
//            return null;
//        }

        return null != mPath ? (null != fstCacheMap ? (fstCacheMap.get(mPath)) : null) : null;
//        if (null != fstCacheMap && null != fstCacheMap.get(mPath) && !fstCacheMap.get(mPath).isRecycled()) {
//            return fstCacheMap.get(mPath);
//        }
//        return null;
    }

    public void recycle() {
        fstCacheMap.evictAll();
        requestMap.clear();
        context = null;
        imageCacheManager = null;
    }

    public boolean loadImg2Cache(String mPath) {
        if ( null != fstCacheMap.get(mPath) && !fstCacheMap.get(mPath).isRecycled()) {
            return true;
        } else {
            Bitmap bitmap = null;
            String picPath = MusicUtil.getAlbumArt(context, mPath);
            if (null!=picPath) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                    bitmap = MP3Util.getAlbumArtFromMP3File(picPath);
                }
            }

            if (null != bitmap) {
                fstCacheMap.put(mPath, bitmap);
                return true;
            }
        }
        return false;
    }

    /**
     * 显示已缓存的图片
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case CONST.IMAGE_LOADED:
                Iterator<ImageView> iterator = requestMap.keySet().iterator();
                while (!mPauseFlag && iterator.hasNext()) {
                    ImageView imageView = iterator.next();
                    if (null != imageView) {
                        String mPath = requestMap.get(imageView);
                        Bitmap bitmap = loadCachedBitmap(mPath);

                        if (!mPauseFlag && null != bitmap) {
                            imageView.setImageBitmap(bitmap);
                            requestMap.remove(imageView);

                        }

                    }
                }
                return true;
        }
        return false;
    }

    public void cancelTask(ImageView iv) {
        requestMap.remove(iv);
    }

    private class LoadImageRunnable implements Runnable {

        private final ImageView imageView;
        private final String path;



        @Override
        public void run() {
            if (path.equals(imageView.getTag())) {
                loadImg2Cache(imageView, path);
            }
        }

        public LoadImageRunnable(ImageView imageView, String path) {
            this.imageView = imageView;
            this.path = path;
        }

        private boolean loadImg2Cache(final ImageView imageView, String path) {
            final String mPath = path;

            if (null == mPath) {
                return false;
            }
            if (null != fstCacheMap.get(mPath) && !fstCacheMap.get(mPath).isRecycled()) {
                return true;
            } else {
                Bitmap bitmap = null;
                String picPath = MusicUtil.getAlbumArt(context, mPath);
                if (null!=picPath) {

                }

                if (null != bitmap) {
                    final Bitmap finalBitmap = bitmap;
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mPath.equals(imageView.getTag())) {
                                imageView.setImageBitmap(finalBitmap);
                            }
                        }
                    });
                    fstCacheMap.put(mPath, bitmap);
                    return true;
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                        bitmap = MP3Util.getAlbumArtFromMP3File(mPath);
                        final Bitmap finalBitmap1 = bitmap;
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mPath.equals(imageView.getTag())) {
                                    imageView.setImageBitmap(finalBitmap1);
                                }
                            }
                        });
                        if (bitmap!=null) {
                            fstCacheMap.put(mPath, bitmap);
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    }

}
