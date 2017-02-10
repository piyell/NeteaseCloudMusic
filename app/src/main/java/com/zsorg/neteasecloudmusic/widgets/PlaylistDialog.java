package com.zsorg.neteasecloudmusic.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.adapters.BaseAdapter;
import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.callbacks.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.callbacks.OnItemCloseListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.PlayerManager;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/28.
 * E-mail:piyell@qq.com
 */

public class PlaylistDialog extends BaseBottomSheetDialog implements OnItemCLickListener, OnItemCloseListener, View.OnClickListener {
    private TextView tvTitle;
    private TextView tvClear;
    private List<MusicBean> mList;
    private PlaylistAdapter mAdapter;
    private int mPosition;
    private PlayerManager mPlayer;

    public PlaylistDialog(@NonNull Context context) {
        super(context);
        initPlaylist();
    }

    public PlaylistDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        initPlaylist();
    }

    public PlaylistDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initPlaylist();
    }


    @Override
    public void onItemClick(View view, int position) {
        mPlayer.setCurrentPosition(position);
        mPlayer.playMusic(getContext());
        setCurrentPlayPosition(position);
    }

    @Override
    public void onClick(View view) {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.confirm_to_clear_playlist)
                .setPositiveButton(R.string.clear, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                        mPlayer.stop();
                        mPlayer.setCurrentPositionOnly(0);
                        PlaylistDialog.this.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void onClose(int position) {
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
        if (mList.size() > 0) {
            if (mPosition > position) {
                mPlayer.setCurrentPositionOnly(--mPosition);
            } else if (mPosition == position) {
                mPosition = mPosition % mList.size();
                mPlayer.setCurrentPosition(mPosition);
//                mAdapter.notifyItemChanged(mPosition);
            }
//            setCurrentPlayPosition(mPosition);
        } else {
            mAdapter.notifyDataSetChanged();
            mPlayer.stop();
        }

        tvTitle.setText(getContext().getString(R.string.play_list_count, String.valueOf(mList != null ? mList.size() : 0)));
    }

    public void setPlaylist(List<MusicBean> list) {
        mList = list;
        tvTitle.setText(getContext().getString(R.string.play_list_count, String.valueOf(list != null ? list.size() : 0)));

        mAdapter.notifyDataSetChanged();
    }

    public void setCurrentPlayPosition(int position) {
        int lastPosition = this.mPosition;
        this.mPosition = position;
        mAdapter.notifyItemChanged(lastPosition);
        mAdapter.notifyItemChanged(position);
        tvTitle.setText(getContext().getString(R.string.play_list_count, String.valueOf(mList != null ? mList.size() : 0)));

    }

    private void initPlaylist() {
        View view = setHeaderView(R.layout.dialog_playlist_header);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvClear = (TextView) findViewById(R.id.tv_clear);

        tvClear.setOnClickListener(this);

        mPlayer = PlayerManager.getInstance(getContext());

        mAdapter = new PlaylistAdapter(getLayoutInflater());
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemCloseListener(this);
        setListAdapter(mAdapter);
    }

    private class PlaylistAdapter extends BaseAdapter<MyHolder> {

        private final String unknown;
        private OnItemCloseListener onItemCloseListener;

        public PlaylistAdapter(@NonNull LayoutInflater inflater) {
            super(inflater);
            unknown = getContext().getString(R.string.unknown);
        }

        @Override
        public MyHolder onCreateHolder(ViewGroup parent, int viewType) {
            return new MyHolder(mInflater.inflate(R.layout.dialog_play_list_item, parent, false));
        }

        @Override
        public void onBindHolder(MyHolder holder, int position) {
            holder.setOnItemCloseListener(onItemCloseListener);

            MusicBean bean = mList.get(position);

            holder.tvName.setText(bean.getName());
            String singer = bean.getSinger() == null ? unknown : bean.getSinger();
            singer = " - " + singer;
            holder.tvSinger.setText(singer);

            holder.setHighlight(mPosition == position);


        }

        @Override
        public int getDataCount() {
            return null == mList ? 0 : mList.size();
        }

        public void setOnItemCloseListener(OnItemCloseListener onItemCloseListener) {
            this.onItemCloseListener = onItemCloseListener;
        }
    }


    class MyHolder extends BaseHolder {
        @BindView(R.id.tv_name)
        CheckedTextView tvName;
        @BindView(R.id.tv_singer)
        CheckedTextView tvSinger;
        @BindView(R.id.iv_close)
        ImageView ivClose;
        @BindView(R.id.iv_voice)
        ImageView ivVoice;

        private OnItemCloseListener onItemCloseListener;

        public MyHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(MyHolder.this, itemView);

            ivClose.setOnClickListener(this);

        }

        public void setOnItemCloseListener(OnItemCloseListener onItemCloseListener) {
            this.onItemCloseListener = onItemCloseListener;
        }

        public void setHighlight(boolean isHighlight) {
            tvName.setChecked(isHighlight);
            tvSinger.setChecked(isHighlight);
            ivVoice.setVisibility(isHighlight ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.iv_close) {
                if (null != onItemCloseListener) {
                    onItemCloseListener.onClose(getAdapterPosition());
                }
            } else {
                super.onClick(view);
            }
        }
    }
}
