package com.silverorange.videoplayer

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.io.IOException

class VideoPlayerViewModel(private val videoPlayerRepository: VideoPlayerRepository) : ViewModel() {

    var videoList = ArrayList<VideoListData>()
    val videoListLoaded = MutableLiveData(false)

    companion object {

        fun getFactory(videoPlayerRepository: VideoPlayerRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return VideoPlayerViewModel(videoPlayerRepository) as T
                }
            }
    }

    fun getVideoList() {
        viewModelScope.launch {
            try {
                videoList = videoPlayerRepository.getVideoList() as ArrayList
                videoListLoaded.value = true
            } catch (exception: IOException) {
                //Handle error case
            }

        }

    }
}