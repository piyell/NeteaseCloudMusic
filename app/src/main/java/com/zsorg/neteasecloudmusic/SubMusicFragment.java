package com.zsorg.neteasecloudmusic;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsorg.neteasecloudmusic.activities.MusicListActivity;
import com.zsorg.neteasecloudmusic.models.beans.MusicBean;
import com.zsorg.neteasecloudmusic.presenters.MusicAlbumAdapter;
import com.zsorg.neteasecloudmusic.presenters.MusicFolderAdapter;
import com.zsorg.neteasecloudmusic.presenters.MusicSingerAdapter;
import com.zsorg.neteasecloudmusic.presenters.MusicSingleAdapter;
import com.zsorg.neteasecloudmusic.presenters.SubMusicPresenter;
import com.zsorg.neteasecloudmusic.views.ISubMusicView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zsorg.neteasecloudmusic.CONST.MUSIC_TYPE;
import static com.zsorg.neteasecloudmusic.CONST.TYPE_ALBUM;
import static com.zsorg.neteasecloudmusic.CONST.TYPE_FOLDER;
import static com.zsorg.neteasecloudmusic.CONST.TYPE_SINGER;
import static com.zsorg.neteasecloudmusic.CONST.TYPE_SINGLE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubMusicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubMusicFragment extends Fragment implements ISubMusicView, OnItemCLickListener {

    private int mMusicType;


    private BaseAdapter mAdapter;

    @BindView(R.id.rv_sub_music)
    RecyclerView mRecyclerView;
    private SubMusicPresenter mPresenter;

    public SubMusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param musicType 加载单曲，歌手，专辑，或文件夹.
     * @return A new instance of fragment SubMusicFragment.
     */
    public static SubMusicFragment newInstance(int musicType) {
        SubMusicFragment fragment = new SubMusicFragment();
        Bundle args = new Bundle();
        args.putInt(MUSIC_TYPE, musicType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMusicType = getArguments().getInt(MUSIC_TYPE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null!=mPresenter) {
            mPresenter.requestList(mMusicType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_music, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.addItemDecoration(LineItemDecorator.getInstance());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter = new SubMusicPresenter(this);

        switch (mMusicType) {
            case TYPE_SINGLE:
                mAdapter = new MusicSingleAdapter(getActivity().getLayoutInflater());
                break;
            case TYPE_SINGER:
                mAdapter = new MusicSingerAdapter(getActivity().getLayoutInflater());
                break;
            case TYPE_ALBUM:
                mAdapter = new MusicAlbumAdapter(getActivity().getLayoutInflater());
                break;
            case TYPE_FOLDER:
                mAdapter = new MusicFolderAdapter(getActivity().getLayoutInflater());
                break;
        }
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        mPresenter.requestList(mMusicType);

        return view;
    }

    @Override
    public void showItems(List<MusicBean> list) {
        mAdapter.setDatas(list);
    }

    @Override
    public void onItemClick(View view, int position) {
        ArrayList<MusicBean> list;
        switch (mMusicType) {
            case TYPE_SINGLE:
                if (position>=0) {
                    MusicPlayerService.startActionSet(getContext(),mAdapter.getDataList(),position>0?position-1:0);
                    MusicPlayerService.startActionPlay(getContext(),true);
                }
                break;
            case TYPE_SINGER:
                String singer = mAdapter.getDataAtPosition(position).getSinger();
                list = (ArrayList<MusicBean>) mPresenter.getMusicBeanList(singer);
                MusicListActivity.startMusicList(getContext(),singer,list);
                break;
            case TYPE_ALBUM:
                String album = mAdapter.getDataAtPosition(position).getAlbum();
                list = (ArrayList<MusicBean>) mPresenter.getMusicBeanList(album);
                MusicListActivity.startMusicList(getContext(),album,list);
                break;
            case TYPE_FOLDER:
                MusicBean bean = mAdapter.getDataAtPosition(position);
                String folder = bean.getName();
                list = (ArrayList<MusicBean>) mPresenter.getMusicBeanList(bean.getSinger());
                MusicListActivity.startMusicList(getContext(),bean.getName(),list);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
