package com.studgenie.app.ui.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.studgenie.app.R;

public class JavaExoActivity extends AppCompatActivity {
    //Media URL
    private final Uri videoURI = Uri.parse("https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd");

    private Button buttonTrackSelector;
    private ImageView buttonMenu;
    private ImageView buttonSpeedControl;
    private ImageView buttonFullScreen;
    private TextView textSpeed;
    Boolean screenOrientationFlag = false;

    //ExoPlayer Declarations
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private DefaultTrackSelector defaultTrackSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_exo);


        playerView = (PlayerView) findViewById(R.id.player_view);
        buttonMenu = playerView.findViewById(R.id.button_menu);
//        buttonSpeedControl = playerView.findViewById(R.id.button_speed_control);
        buttonFullScreen = playerView.findViewById(R.id.button_fullscreen);
        textSpeed = playerView.findViewById(R.id.text_speed);

        textSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] qualityRange = {
                        "1x","1.5x"//"0.25x", "0.5x", "0.75x","1x", "1.25x", "1.5x", "2x"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(JavaExoActivity.this);
                builder.setTitle("Playback Speed");
                builder.setItems(qualityRange, new DialogInterface.OnClickListener() {@
                        Override
                public void onClick(DialogInterface dialog, int which) {
//                    if ("0.25x".equals(qualityRange[which])) {
//                        PlaybackParameters param = new PlaybackParameters(0.25f);
//                        simpleExoPlayer.setPlaybackParameters(param);
//                        textSpeed.setText("0.25x");
//                    }
//                    if ("0.5x".equals(qualityRange[which])) {
//                        PlaybackParameters param = new PlaybackParameters(0.5f);
//                        simpleExoPlayer.setPlaybackParameters(param);
//                        textSpeed.setText("0.5x");
//                    }
//                    else if ("0.75x".equals(qualityRange[which])) {
//                        PlaybackParameters param = new PlaybackParameters(0.75f);
//                        simpleExoPlayer.setPlaybackParameters(param);
//                        textSpeed.setText("0.75x");
//                    }
                    if ("1x".equals(qualityRange[which])) {
                        PlaybackParameters param = new PlaybackParameters(1.0f);
                        simpleExoPlayer.setPlaybackParameters(param);
                        textSpeed.setText("1x");
                    }
//                    else if ("1.25x".equals(qualityRange[which])) {
//                        PlaybackParameters param = new PlaybackParameters(1.25f);
//                        simpleExoPlayer.setPlaybackParameters(param);
//                        textSpeed.setText("1.25x");
//                    }
                    else if ("1.5x".equals(qualityRange[which])) {
                        PlaybackParameters param = new PlaybackParameters(1.5f);
                        simpleExoPlayer.setPlaybackParameters(param);
                        textSpeed.setText("1.5x");
                    }
//                    else if ("1.75x".equals(qualityRange[which])) {
//                        PlaybackParameters param = new PlaybackParameters(1.75f);
//                        simpleExoPlayer.setPlaybackParameters(param);
//                        textSpeed.setText("1.75x");
//                    }else if ("2x".equals(qualityRange[which])) {
//                        PlaybackParameters param = new PlaybackParameters(2.0f);
//                        simpleExoPlayer.setPlaybackParameters(param);
//                        textSpeed.setText("2x");
//                    }
                }
                });
                builder.show();
            }
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        buttonFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (screenOrientationFlag){
                    buttonFullScreen.setImageDrawable(getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    screenOrientationFlag=false;
                }else {
                    buttonFullScreen.setImageDrawable(getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    screenOrientationFlag = true;
                }
            }
        });
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.quality:
                        trackSelectionDialog();
                        break;
                    case R.id.download:
                        Toast.makeText(JavaExoActivity.this, "Download", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popup.show();
    }

    private void initializePlayer() {
        // Create a data source factory.
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "StudGenie"));
        // Create a DASH media source pointing to a DASH manifest uri.
        DashMediaSource dashMediaSource = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(videoURI);

        // Default Track Selector
        defaultTrackSelector = new DefaultTrackSelector(this);

        simpleExoPlayer = new SimpleExoPlayer.Builder(this)
                .setTrackSelector(defaultTrackSelector)
                .build();

        // Bind the player to the view.
        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.prepare(dashMediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void trackSelectionDialog() {
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo != null) {
            int rendererIndex = 0;
            int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
            boolean allowAdaptiveSelections =
                    rendererType == C.TRACK_TYPE_VIDEO
                            || (rendererType == C.TRACK_TYPE_AUDIO
                            && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
            TrackSelectionDialogBuilder build = new TrackSelectionDialogBuilder(JavaExoActivity.this, "Track Selector", defaultTrackSelector, rendererIndex);
            build.setAllowAdaptiveSelections(allowAdaptiveSelections);
            build.build().show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || simpleExoPlayer == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            simpleExoPlayer.release();
        }
    }
}

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checking the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            //First Hide other objects (listview or recyclerview), better hide them using Gone.
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) simpleExoPlayer.getLayoutParams();
//            params.width=params.MATCH_PARENT;
//            params.height=params.MATCH_PARENT;
//            simpleExoPlayer.setLayoutParams(params);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            //unhide your objects here.
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) simpleExoPlayer.getLayoutParams();
//            params.width=params.MATCH_PARENT;
//            params.height=600;
//            simpleExoPlayer.setLayoutParams(params);
//        }
//    }
//}