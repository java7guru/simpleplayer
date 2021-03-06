package com.levup.simpleplayer.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levup.simpleplayer.R;
import com.levup.simpleplayer.models.PlayListModel;
import com.levup.simpleplayer.models.Song;
import com.levup.simpleplayer.presenters.PlayListPresenter;
import com.levup.simpleplayer.repositories.PlayListRepository;
import com.levup.simpleplayer.views.PlayListView;
import com.levup.simpleplayer.views.adapters.PlayListAdapter;

import java.util.List;

import rx.Single;
import rx.SingleSubscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListsFragment extends Fragment implements PlayListView {

    private RecyclerView mRecyclerView;

    private PlayListPresenter mPresenter = new PlayListPresenter();

    public static PlayListsFragment newInstance() {
        Bundle args = new Bundle();
        PlayListsFragment fragment = new PlayListsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onAttachToView(this);
        mPresenter.loadPlayList();
    }

    @Override
    public void onPlayListLoaded(List<Song> songs) {
        PlayListAdapter adapter = new PlayListAdapter();
        adapter.setDataSource(songs);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDetach();
    }
}
