package com.zsorg.neteasecloudmusic.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zsorg.neteasecloudmusic.DiscPageChanger;
import com.zsorg.neteasecloudmusic.DiscPagerAdapter;
import com.zsorg.neteasecloudmusic.OnMenuItemClickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.ImageCacheManager2;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.PlayerMenuModel;
import com.zsorg.neteasecloudmusic.models.beans.ConfigBean;
import com.zsorg.neteasecloudmusic.models.beans.MenuBean;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.presenters.ConfigPresenter;
import com.zsorg.neteasecloudmusic.presenters.PlayerPresenter;
import com.zsorg.neteasecloudmusic.utils.AlertUtil;
import com.zsorg.neteasecloudmusic.utils.BlurUtil;
import com.zsorg.neteasecloudmusic.utils.TimeUtil;
import com.zsorg.neteasecloudmusic.views.IConfigView;
import com.zsorg.neteasecloudmusic.views.IPlayerView;
import com.zsorg.neteasecloudmusic.widgets.MenuDialog;
import com.zsorg.neteasecloudmusic.widgets.PlaylistDialog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, IPlayerView, IConfigView {

    @BindView(R.id.activity_player)
    View rootView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.play_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.iv_needle)
    ImageView needle;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_order)
    ImageView ivOrder;
    @BindView(R.id.iv_heart)
    ImageView ivHeart;
    @BindView(R.id.sb_progress)
    SeekBar seekBar;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    private PlayerPresenter mPresenter;
    private PlaylistDialog mDialog;
    private DiscPageChanger mDiscChanger;
    private DiscPagerAdapter mAdapter;
    private ConfigPresenter mConfigPresenter;

    private int[] drawables = new int[]{R.drawable.ic_order_list, R.drawable.ic_order_random, R.drawable.ic_order_single};
    private String[] orders;

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.activity_right_in,R.anim.activity_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_left_in,R.anim.activity_right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAdapter = new DiscPagerAdapter(getLayoutInflater());
        mViewPager.setAdapter(mAdapter);

        mDiscChanger = new DiscPageChanger(mViewPager, needle);
        mViewPager.addOnPageChangeListener(mDiscChanger);


        mPresenter = new PlayerPresenter(this);
        mConfigPresenter = new ConfigPresenter(this);

//        mPresenter.setPlaylist(id, position);

//        ivPlay.setImageResource(PlayerManager.newInstance(this).isPause() ? R.drawable.ic_play : R.drawable.ic_pause);


//        setBlurBackground(BitmapFactory.decodeResource(getResources(), R.drawable.play_bg_night));
        orders = getResources().getStringArray(R.array.array_music_order);
        mPresenter.requestMusicInfo();
        mConfigPresenter.requestLoadingList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.syncPlayerInfo();
        mPresenter.requestMusicInfo();
    }

    @Override
    public void displayConfigList(final List<ConfigBean> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String order = list.get(0).getItemRight();
                String[] orders = getResources().getStringArray(R.array.array_music_order);
                int i = 0;
                while (!order.equals(orders[i])) {
                    i++;
                }
                ivOrder.setImageResource(drawables[i]);
            }
        });



    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateTrackInfo(int position) {
        seekBar.setProgress(position);
        tvStartTime.setText(TimeUtil.formatTime(position));
    }

    @Override
    public void updatePlayButton(boolean isPlay) {
        int resID = isPlay ? R.drawable.ic_pause_primary : R.drawable.ic_play_primary;
        ivPlay.setImageDrawable(VectorDrawableCompat.create(getResources(), resID, null));

//        if (isPlay) {
//            mDiscChanger.startEnd2StartAnimation();
//        } else {
//            mDiscChanger.startStart2EndAnimation();
//        }
    }

    @Override
    public void displayMusicInfo(MusicBean bean) {
        int currentPosition = PlayerManager.getInstance(this).getCurrentPosition();
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.setCurrentPlayPosition(currentPosition);
        }
        if (null != bean) {
//
//            if (!PlayerManager.newInstance(getContext()).isPause()) {
//                mDiscChanger.startEnd2StartAnimation();
//            } else {
//                mDiscChanger.startStart2EndAnimation();
//            }

            mViewPager.setCurrentItem(currentPosition, true);
            tvEndTime.setText(TimeUtil.formatTime(bean.getDuration()));
            seekBar.setMax((int) bean.getDuration());
            String name = bean.getName();
            setTitle(name);
            toolbar.setTitle(name);
            String singer = bean.getSinger();
            singer = null == singer ? getString(R.string.unknown) : singer;
            toolbar.setSubtitle(singer);

            updateFavoriteButton();


//            setBlurBackground(((BitmapDrawable)mViewPager.getBackground()).getBitmap());
            Bitmap bitmap = ImageCacheManager2.getInstance(this).loadCachedBitmap(bean.getPath());
            if (null == bitmap) {

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.disc_song_small);
            }
            setBlurBackground(bitmap);
        } else {
            finish();
        }
    }

    @OnClick(R.id.iv_order)
    public void onOrderClick() {
        int order = mConfigPresenter.getMusicOrder();
        order = (++order) % 3;
        Toast.makeText(PlayerActivity.this,orders[order],Toast.LENGTH_SHORT).show();
        mConfigPresenter.setMusicOrder(order);
        ivOrder.setImageResource(drawables[order]);
    }

    @OnClick(R.id.iv_heart)
    public void onHeartClick() {
        if (mPresenter.isAddedToFavorite()) {
            mPresenter.cancelFavorite();
        } else {
            mPresenter.addToFavorite();
        }

        updateFavoriteButton();
    }

    @OnClick(R.id.iv_right)
    public void onMenuClick() {
        MenuDialog dialog = new MenuDialog(this);
        dialog.setTitle(getTitle());
        List<MenuBean> menuList = PlayerMenuModel.getInstance(this).getMenuList();
        dialog.setMenuList(menuList);
        dialog.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int menuPosition) {
                final MusicBean bean = mPresenter.getPlayerMusicBean();
                switch (menuPosition) {
                    case 0:
                        //收藏

                        AlertUtil.showCollectionDialog(PlayerActivity.this, bean);
                        break;
                    case 1:
                        //分享
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("audio/x-mpeg");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(bean.getPath())));
                        startActivity(Intent.createChooser(intent, getString(R.string.share_to)));
                        break;
                }
            }
        });
        dialog.show();
    }

    @OnClick(R.id.iv_next)
    public void onNextClick() {
        mPresenter.nextMusic();
    }

    @OnClick(R.id.iv_playlist)
    public void onPlaylistClick() {
        mDialog = new PlaylistDialog(this);
        mDialog.setPlaylist(PlayerManager.getInstance(this).getPlaylist());
        mDialog.setCurrentPlayPosition(PlayerManager.getInstance(this).getCurrentPosition());
        mDialog.show();
        mDialog.setPeekHeight(this);
    }

    @OnClick(R.id.iv_previous)
    public void onPreviousClick() {
        mPresenter.preMusic();
    }

    @OnClick(R.id.iv_play)
    public void onPlayClick(View view) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            PlayerManager manager = PlayerManager.getInstance(this);
            if (manager.isPause()) {
                mPresenter.startPlay(false);
                imageView.setImageResource(R.drawable.ic_pause);
                mDiscChanger.startEnd2StartAnimation();
            } else {
                mPresenter.Pause();
                mDiscChanger.startStart2EndAnimation();
                imageView.setImageResource(R.drawable.ic_play);
            }
        }
    }

    /**
     * NavigationIcon onClick
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        finish();
    }


    private void updateFavoriteButton() {
        VectorDrawableCompat drawableCompat = VectorDrawableCompat.create(getResources(), mPresenter.isAddedToFavorite() ? R.drawable.ic_favorite_fill : R.drawable.ic_favorite, null);
        ivHeart.setImageDrawable(drawableCompat);
    }

    private void setBlurBackground(Bitmap background) {
        Observable.just(background).map(new Function<Bitmap, TransitionDrawable>() {
            @Override
            public TransitionDrawable apply(Bitmap bitmap) throws Exception {
                return new TransitionDrawable(new Drawable[]{rootView.getBackground(),BlurUtil.createBlurredImageFromBitmap(bitmap, PlayerActivity.this, 20) });
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TransitionDrawable>() {
            @Override
            public void accept(TransitionDrawable drawable) throws Exception {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.setBackground(drawable);
                } else {
                    rootView.setBackgroundDrawable(drawable);
                }
                drawable.startTransition(300);
            }
        });
//        Drawable blurredImageFromBitmap = BlurUtil.createBlurredImageFromBitmap(background, this, 20);

    }
}
