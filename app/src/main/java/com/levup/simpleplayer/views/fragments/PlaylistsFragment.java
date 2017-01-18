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
import com.levup.simpleplayer.presenters.PlayListRepository;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListsFragment extends Fragment {

    private RecyclerView mRecyclerView;

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

        PlayListRepository repository = new PlayListRepository();
        Single<PlayListModel> single = repository.loadPlayList();
        single.subscribe(new SingleSubscriber<PlayListModel>() {
            @Override
            public void onSuccess(PlayListModel value) {


            }

            @Override
            public void onError(Throwable error) {

            }
        });


    }
}
