package com.zsorg.neteasecloudmusic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.LineItemDecorator;
import com.zsorg.neteasecloudmusic.callbacks.OnItemCLickListener;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.activities.MusicListActivity;
import com.zsorg.neteasecloudmusic.adapters.PlaylistAdapter;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.presenters.PlaylistPresenter;
import com.zsorg.neteasecloudmusic.views.IPlaylistView;

import java.util.List;


public class PlayListFragment extends Fragment implements IPlaylistView, OnItemCLickListener {

    private PlaylistAdapter mAdapter;
    private PlaylistPresenter mPresenter;

    public PlayListFragment() {
    }

    @SuppressWarnings("unused")
    public static PlayListFragment newInstance() {
        return new PlayListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(LineItemDecorator.getInstance());
            mAdapter = new PlaylistAdapter(getActivity().getLayoutInflater());
            mAdapter.setOnItemClickListener(this);
            mAdapter.addHeaderView(inflater.inflate(R.layout.play_list_header,null));
            recyclerView.setAdapter(mAdapter);
            mPresenter = new PlaylistPresenter(this);
        }
        return view;
    }

    @Override
    public void showItems(List<MusicBean> list) {
        if (null != mAdapter && null != list) {
            mAdapter.setDatas(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position>0) {
            MusicBean bean = mAdapter.getDataAtPosition(position);
            MusicListActivity.startMusicList(getContext(), bean.getName(),mPresenter.loadPlaylist((int) bean.getDuration()));
        }
    }
}
