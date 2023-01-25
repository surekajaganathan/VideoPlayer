package com.silverorange.videoplayer

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.MimeTypes

class VideoPlayerActivity : AppCompatActivity(), View.OnClickListener {

    private val repository: VideoPlayerRepository = VideoPlayerRepository()
    private val viewModel: VideoPlayerViewModel by viewModels {
        VideoPlayerViewModel.getFactory(repository)
    }

    private var exoPlayer: ExoPlayer? = null
    private lateinit var playButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var playerView: StyledPlayerView
    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvDescription: TextView
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        initWidgets()
    }

    private fun initWidgets() {
        playerView = findViewById(R.id.player)
        preparePlayer()
        playButton = findViewById(R.id.exo_play)
        nextButton = findViewById(R.id.exo_next)
        previousButton = findViewById(R.id.exo_prev)
        tvTitle = findViewById(R.id.tv_title)
        tvAuthor = findViewById(R.id.tv_author)
        tvDescription = findViewById(R.id.tv_description)
        playButton.setOnClickListener(this)
        nextButton.setOnClickListener(this)
        previousButton.setOnClickListener(this)
        viewModel.getVideoList()

        viewModel.videoListLoaded.observe(this) {
            if (it)
                prepareMediaSource()
        }
    }

    private fun preparePlayer() {
        playerView.setShowFastForwardButton(false)
        playerView.setShowRewindButton(false)
        exoPlayer = ExoPlayer.Builder(this).build()
    }

    private fun prepareMediaSource() {
        val mediaList = ArrayList<MediaItem>()
        for (item in viewModel.videoList) {
            val mediaItem = MediaItem.Builder()
                .setUri(item.videoUrl)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build()
            mediaList.add(mediaItem)
        }

        exoPlayer!!.apply {
            setMediaItems(mediaList)
            //Start from the first video
            seekTo(0, 0L)
            prepare()
        }.also {
            playerView.player = it
        }
        setVideoText()
    }

    public override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    public override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        exoPlayer?.let {
            it.release()
            exoPlayer = null
            playerView.player = null
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.exo_play -> {
                if (exoPlayer?.isPlaying == true) {
                    pauseVideo()
                } else {
                    playVideo()
                }
            }
            R.id.exo_next -> nextVideo()
            R.id.exo_prev -> prevVideo()
        }
    }

    private fun playVideo() {
        playButton.setImageResource(R.drawable.pause)
        exoPlayer?.playWhenReady = true
        setVideoText()
    }

    private fun pauseVideo() {
        playButton.setImageResource(R.drawable.play)
        exoPlayer?.playWhenReady = false
    }

    //Position holds the index of the currently displayed video in the list
    //Set the position for next and prev videos accordingly
    private fun setPosition(isNext: Boolean) {
        val listSize = viewModel.videoList.size
        if (listSize == 0)
            position = 0
        else {
            if (isNext) {
                if (listSize - 1 == position)
                    position = 0
                else ++position

            } else {
                if (position != 0)
                    --position
            }
        }
    }

    private fun nextVideo() {
        setPosition(true)
        exoPlayer?.seekTo(position, 0L)
        playVideo()
    }

    private fun prevVideo() {
        setPosition(false)
        exoPlayer?.seekTo(position, 0L)
        playVideo()
    }

    private fun setVideoText() {
        if (position >= 0) {
            viewModel.videoList[position].let { item ->
                tvTitle.text = item.title
                tvAuthor.text = item.author.name
                tvDescription.text = item.description
            }
        }
    }

}