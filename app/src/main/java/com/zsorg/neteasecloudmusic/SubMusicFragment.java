package com.zsorg.neteasecloudmusic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubMusicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubMusicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MUSIC_TYPE = "MUSIC_TYPE";
    public static final int TYPE_SINGLE = 0;
    public static final int TYPE_SINGER = 1;
    public static final int TYPE_ALBUM = 2;
    public static final int TYPE_FOLDER = 3;

    private int mMusicType;


    private BaseAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.rv_sub_music)
    RecyclerView mRecyclerView;

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
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_music, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.addItemDecoration(LineItemDecorator.getInstance());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        switch (mMusicType) {
            case TYPE_SINGLE:
                mAdapter = new MusicSingleAdapter(getActivity().getLayoutInflater());
                break;
            case TYPE_SINGER:
                break;
            case TYPE_ALBUM:
                break;
            case TYPE_FOLDER:
                break;
        }
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
