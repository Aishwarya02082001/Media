package com.example.mediahmi.activity;

import android.content.ComponentName;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mediahmi.R;
import com.example.mediahmi.service.MyMusicService;

public class MainActivity extends AppCompatActivity {

    private Button mPlayBtn;
    private Button mPauseBtn;
    private Button mStopBtn;
    private Button mSkipBtn;

    private MediaBrowserCompat mediaBrowserCompat;
    private MediaControllerCompat mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.i("MediaApp","MainActivity called in MediaApp");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mPlayBtn = (Button) findViewById(R.id.play);
        mPauseBtn = (Button) findViewById(R.id.pause);
        mStopBtn = (Button) findViewById(R.id.stop);
        mSkipBtn = (Button) findViewById(R.id.skip);

        mediaBrowserCompat = new MediaBrowserCompat(this,new ComponentName(this,MyMusicService.class),connectionCallback,null);
    }


    @Override
    protected void onStart(){
        super.onStart();
        mediaBrowserCompat.connect();
    }

    public void setUpButtons() {
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaController != null) {
                    mediaController.getTransportControls().play();


                } else {
                    Log.w("MediaApp", "MediaController is not connected yet.");
                }

            }
        });

        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaController != null) {
                    mediaController.getTransportControls().pause();
                } else {
                    Log.w("MediaApp", "MediaController is not connected yet.");
                }
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaController != null) {
                    mediaController.getTransportControls().stop();
                } else {
                    Log.w("MediaApp", "MediaController is not connected yet.");
                }
            }
        });


        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaController != null) {
                    mediaController.getTransportControls().skipToNext();
                } else {
                    Log.w("MediaApp", "MediaController is not connected yet.");
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaController != null) {
            MediaControllerCompat.getMediaController(this).unregisterCallback(controllerCallback);
            mediaController = null;
        }
        mediaBrowserCompat.disconnect();
    }



    private final MediaBrowserCompat.ConnectionCallback connectionCallback = new MediaBrowserCompat.ConnectionCallback() {


        @Override
        public void onConnected() {
            Log.i("MediaApp", "MediaBrowser connected");
            MediaSessionCompat.Token token = mediaBrowserCompat.getSessionToken();
            mediaController = new MediaControllerCompat(MainActivity.this, token);
            MediaControllerCompat.setMediaController(MainActivity.this, mediaController);
            mediaController.registerCallback(controllerCallback);
            setUpButtons();
        }

    };

    private final MediaControllerCompat.Callback controllerCallback =
            new MediaControllerCompat.Callback() {
                // You can listen for playback state or metadata changes here
            };

}