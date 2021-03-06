package com.levup.simpleplayer.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntegerRes;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.levup.simpleplayer.BuildConfig;
import com.levup.simpleplayer.models.Song;
import com.levup.simpleplayer.repositories.PlayListRepository;
import com.levup.simpleplayer.views.MusicActivity;
import com.levup.simpleplayer.R;

import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class PlayBackService extends Service implements
        MediaPlayer.OnPreparedListener,
        MusicActivity.PlayBackInteraction {

    public static final String ACTION_PLAY = BuildConfig.APPLICATION_ID + ".action.PLAY";

    public static final String TAG = PlayBackService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 101;

    private final IBinder mBinder = new PlayBackBinder();

    private MediaPlayer mMediaPlayer = null;

    private boolean isPaused;

    private PublishSubject<Integer> mDurationSubject = PublishSubject.create();

    public static Intent newInstance(Context context) {
        return new Intent(context, PlayBackService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy()");
    }

    public PlayBackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();

        PendingIntent pi = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                new Intent(getApplicationContext(), MusicActivity.class),
                PendingIntent.FLAG_NO_CREATE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setContentIntent(pi);

        startForeground(NOTIFICATION_ID, builder.build());
    }

    public void playSongId(long songId) {
       // Song song = SongsRepository.getSongForID(this, songId);
        Uri contentUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                songId);
        try {
            if(mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, contentUri);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnCompletionListener(mediaPlayer ->
                    new PlayListRepository()
                    .getNextSongAfter(songId)
                    .subscribe(song -> playSongId(song.getId()), throwable -> {
                        Toast.makeText(
                                PlayBackService.this,
                                throwable.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean play() {
        try {
            if(mMediaPlayer != null && isPaused) {
                mMediaPlayer.start();
                isPaused = false;

                timer.scheduleAtFixedRate(new DurationTimerTask(), 0, 1000);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public Observable<Integer> gerDurationObservable() {
        return mDurationSubject;
    }

    @Override
    public void onUserSeek(int progress) {
        try {
            if(mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                int seekPosition = (mMediaPlayer.getDuration() / 100) * progress;
                mMediaPlayer.seekTo(seekPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                isPaused = true;
                timer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Timer timer = new Timer();

    private class DurationTimerTask extends TimerTask {

        @Override
        public void run() {
            int current = (mMediaPlayer.getCurrentPosition() * 100) / mMediaPlayer.getDuration();
            mDurationSubject.onNext(current);
        }

    }

    @Override
    public void play(long songId) {
        playSongId(songId);
    }

    public class PlayBackBinder extends Binder {
        public PlayBackService getService() {
            return PlayBackService.this;
        }
    }

}
