package com.zsorg.neteasecloudmusic.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.OnDeleteListener;
import com.zsorg.neteasecloudmusic.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.OnTextSubmitListener;
import com.zsorg.neteasecloudmusic.PlaylistAdapter;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.models.PlaylistModel;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.models.db.DiskMusicDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/2/3.
 * E-mail:piyell@qq.com
 */

public class AlertUtil {
    public static void showDeleteDialog(Context context, final OnDeleteListener listener) {
        final boolean[] isDeleteOnDisk = new boolean[1];
        new AlertDialog.Builder(context)
                .setTitle(R.string.confirm_to_remove_music)
                .setMultiChoiceItems(R.array.delete_on_disk_choice, new boolean[]{false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        isDeleteOnDisk[0] = b;
                    }
                })
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listener != null) {
                            listener.onDelete(isDeleteOnDisk[0]);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public static void showDeletePlaylistDialog(Context context, final OnDeleteListener listener) {
        final boolean[] isDeleteOnDisk = new boolean[1];
        new AlertDialog.Builder(context)
                .setMessage(R.string.confirm_to_delete_playlist)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listener != null) {
                            listener.onDelete(isDeleteOnDisk[0]);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public static void showCollectionDialog(final Context context, final MusicBean bean) {
        final PlaylistModel model = new PlaylistModel(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.collect_to_playlist).create();

        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView rvPlaylist = (RecyclerView) inflater.inflate(R.layout.dialog_collection_to_playlist, null);
        rvPlaylist.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        rvPlaylist.setLayoutManager(new LinearLayoutManager(context));
        final PlaylistAdapter adapter = new PlaylistAdapter(inflater,true);
        View view = inflater.inflate(R.layout.header_add_playlist, null);
        adapter.addHeaderView(view);
        adapter.setOnItemClickListener(new OnItemCLickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        showNewPlaylistDialog(context, new OnTextSubmitListener() {
                            @Override
                            public void onTextSubmit(String text) {
                                int listID = model.newPlaylist(text);
                                model.addToPlaylist(listID,bean.getPath());
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        break;
                    default:
                        model.addToPlaylist((int) adapter.getDataAtPosition(position).getDuration(),bean.getPath());
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        break;
                }
            }
        });
//        adapter.addHeaderView(inflater.inflate(R.layout.header_new_playlist,null));
        rvPlaylist.setAdapter(adapter);

        dialog.setView(rvPlaylist);
        dialog.show();

        Observable.just(model.loadPlaylistList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MusicBean>>() {
                    @Override
                    public void accept(List<MusicBean> list) throws Exception {
                        if (null != list) {
                            adapter.setDatas(list);
                        }
                    }
                });
    }

    public static void showNewPlaylistDialog(Context context, final OnTextSubmitListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.header_add_playlist_edit_text, null);
        final EditText etInput = (EditText) view.findViewById(R.id.et_input);
        final TextView tvHint = (TextView) view.findViewById(R.id.tv_hint);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvHint.setText(editable.length() + "/40");
            }
        });

        new AlertDialog.Builder(context)
                .setTitle(R.string.new_playlist)
                .setView(view)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (null != listener) {
                            listener.onTextSubmit(etInput.getText().toString());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel,null)
                .show();

    }
}
