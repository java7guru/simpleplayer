package com.levup.simpleplayer.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.levup.simpleplayer.R;
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
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_main_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final Song song = mDataSource.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        private Song mSong;

        private ImageView mCoverImageView;
        private TextView mArtistTextView;
        private TextView mTitleTextView;

        public SongViewHolder(View itemView) {
            super(itemView);
            mCoverImageView = (ImageView) itemView.findViewById(R.id.coverImageView);
            mArtistTextView = (TextView) itemView.findViewById(R.id.artistTextView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        }

        private void bind(@NonNull Song song) {
            mSong = song;
            mArtistTextView.setText(song.artistName);
            mTitleTextView.setText(song.title);
            mCoverImageView.setImageDrawable(new ColorDrawable(Color.GRAY));
        }

        public Song getSong() {
            return mSong;
        }
    }
}