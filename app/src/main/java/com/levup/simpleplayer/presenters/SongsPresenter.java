package com.levup.simpleplayer.presenters;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.levup.simpleplayer.models.Song;
import com.levup.simpleplayer.views.SongsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by java on 05.12.2016.
 */

public class SongsPresenter {

    private SongsView mView = null;

    public void onAttachToView(@NonNull SongsView songsView) {
        mView = songsView;
    }

    public void loadAllSongs() {
        new AsyncTask<Void, Void, List<Song>>() {

            @Override
            protected List<Song> doInBackground(Void... voids) {
                try {
                    return SongLoader.getAllSongs(mView.getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new ArrayList<Song>();
            }

            @Override
            protected void onPostExecute(List<Song> songs) {
                super.onPostExecute(songs);
                if(mView == null) return;
                mView.onAllSongsLoaded(songs);
            }
        }.execute();
    }

    public void onDetach() {
        mView = null;
    }

}
