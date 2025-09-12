package com.example.mediahmi.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

public class MusicPlayerManager {
    private final Context context;
    private MediaPlayer mMediaPlayer;
    private final int[] musicResIds;
    private PlaybackStateListener playbackStateListener;
    private int currentIndex = 0;

    public interface PlaybackStateListener {
        void onPlaybackStateChanged(int state);
    }

    public MusicPlayerManager(Context context, int[] musicResIds) {
        this.context = context.getApplicationContext();
        this.musicResIds = musicResIds;
    }

    public void setPlaybacakStateListener(PlaybackStateListener listener) {
        this.playbackStateListener = listener;
    }

    public void playCurrentSong() {

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = MediaPlayer.create(context, musicResIds[currentIndex]);
        mMediaPlayer.setOnCompletionListener(mp -> skipToNext());
        mMediaPlayer.start();
        notifyStateChanged(PlaybackStateCompat.STATE_PLAYING);
        Log.i("MusicPlayerManager", "playCurrentSong is called");
    }

    public void pauseCurrentSong() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            notifyStateChanged(PlaybackStateCompat.STATE_PAUSED);
            Log.i("MusicPlayerManager", "pauseCurrentSong is called");
        }
    }

    public void stopCurrentSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            notifyStateChanged(PlaybackStateCompat.STATE_STOPPED);
            Log.i("MusicPlayerManager", "stopCurrentSong is called");
        }
    }

    public void skipToPrevious() {
        currentIndex = (currentIndex - 1 + musicResIds.length) % musicResIds.length;
        playCurrentSong();

        Log.i("MusicPlayerManager", "skipToPrevious is called");

    }

    public void skipToNext() {
        currentIndex = (currentIndex + 1) % musicResIds.length;
        playCurrentSong();
        Log.i("MusicPlayerManager", "skipToNext is called");

    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    private void notifyStateChanged(int state) {
        if (playbackStateListener != null) {
            playbackStateListener.onPlaybackStateChanged(state);
        }
    }
}
