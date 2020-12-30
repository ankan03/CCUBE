package com.studgenie.app.ui.main.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.studgenie.app.R
import kotlinx.android.synthetic.main.fragment_video.view.*

class VideoFragment : Fragment() {
    //    private val videoURI: Uri = Uri.parse("https://s3.amazonaws.com/_bc_dml/example-content/sintel_dash/sintel_vod.mpd")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_video, container, false)
//        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(requireContext()).build()
//        //Binding the player to the view
//        rootView.player_view.player = player
//
//        val dataSourceFactory = DefaultHttpDataSourceFactory(
//            Util.getUserAgent(requireContext(), "VideoPlayer")
//        )
//        val mediaSource =
//            DashMediaSource.Factory(dataSourceFactory).createMediaSource(videoURI)
//        player.prepare(mediaSource)
//        player.playWhenReady = true
        return rootView
    }
}