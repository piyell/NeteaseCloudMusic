package com.zsorg.neteasecloudmusic.presenters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsorg.neteasecloudmusic.BaseHolder;
import com.zsorg.neteasecloudmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by piyel_000 on 2017/1/6.
 */

public class FolderHolder extends BaseHolder {
    @BindView(R.id.iv_folder)
    ImageView ivFolder;
    @BindView(R.id.tv_folder_name)
    TextView tvFolderName;
    @BindView(R.id.tv_songs_count)
    TextView tvCount;
    @BindView(R.id.tv_folder_path)
    TextView tvFolderPath;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    public FolderHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }
}
