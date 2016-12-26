package com.levup.simpleplayer.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levup.simpleplayer.R;
import com.levup.simpleplayer.models.Song;
import com.levup.simpleplayer.presenters.SongsPresenter;
import com.levup.simpleplayer.views.SongsAdapter;
import com.levup.simpleplayer.views.SongsView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author fddfdf
 *
 */
public class SongsFragment extends Fragment implements SongsView {

    private SongsPresenter mPresenter = new SongsPresenter();

    private RecyclerView mRecyclerView = null;
    private SongsAdapter mSongsAdapter = new SongsAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.onAttachToView(this);
        mPresenter.loadAllSongs();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onAllSongsLoaded(List<Song> songList) {
        mSongsAdapter.setDataSource(songList);
        mSongsAdapter.setOnItemClickListener(view ->{

        });
        mRecyclerView.setAdapter(mSongsAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onDetach();
    }
}
