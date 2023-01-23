package com.silverorange.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

class VideoPlayerActivity : AppCompatActivity() {

    private val repository: VideoPlayerRepository = VideoPlayerRepository()
    private val viewModel : VideoPlayerViewModel by viewModels {
        VideoPlayerViewModel.getFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        viewModel.getVideoList()
    }
}