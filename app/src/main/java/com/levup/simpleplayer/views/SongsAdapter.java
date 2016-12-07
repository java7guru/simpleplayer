package com.levup.simpleplayer.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.levup.simpleplayer.models.Song;

import java.util.List;

/**
 * Created by java on 07.12.2016.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    private List<Song> mDataSource = null;

    public void setDataSource(List<Song> dataSource) {
        mDataSource = dataSource;
        notifyDataSetChanged();
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        public SongViewHolder(View itemView) {
            super(itemView);
        }
    }
}
