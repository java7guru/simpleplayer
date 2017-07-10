package com.levup.simpleplayer.views.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levup.simpleplayer.R;
import com.levup.simpleplayer.models.Song;
import com.levup.simpleplayer.repositories.SongsRepository;

import java.util.List;

/**
 * Created by java on 18.01.2017.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListItemViewHolder> {

    private List<Song> mDataSource = null;

    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public void setDataSource(List<Song> songs) {
        mDataSource = songs;
        notifyDataSetChanged();
    }

    @Override
    public PlayListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.view_item_play_list_song, parent, false);
        return new PlayListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayListItemViewHolder holder, int position) {
        final Song song = mDataSource.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    class PlayListItemViewHolder extends RecyclerView.ViewHolder {

        private Song mSong;

        private ImageView mCoverImageView;
        private TextView mArtistTextView;
        private TextView mTitleTextView;
        private View mProgressView;


        public PlayListItemViewHolder(View itemView) {
            super(itemView);
            mCoverImageView = (ImageView) itemView.findViewById(R.id.ivCover);
            mArtistTextView = (TextView) itemView.findViewById(R.id.tvArtist);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tvSong);
            mProgressView = itemView.findViewById(R.id.vProgress);
        }

        private void bind(@NonNull Song song) {
            mSong = song;
            mArtistTextView.setText(song.getArtistName());
            mTitleTextView.setText(song.getTitle());
            String cover = SongsRepository.getAlbumCover(
                    itemView.getContext(),
                    song.getAlbumId());
            Glide
                    .with(itemView.getContext())
                    .load(cover)
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.GRAY))
                    .crossFade()
                    .into(mCoverImageView);
            mProgressView.setX(-mRecyclerView.getMeasuredWidth());
        }

        public Song getSong() {
            return mSong;
        }

    }

}
