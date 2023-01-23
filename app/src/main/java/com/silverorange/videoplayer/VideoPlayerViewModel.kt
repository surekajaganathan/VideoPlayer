package com.silverorange.videoplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class VideoPlayerViewModel(private val videoPlayerRepository: VideoPlayerRepository) : ViewModel() {


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
                val videoList = videoPlayerRepository.getVideoList()
                val len = videoList.size
            }catch (exception: IOException){
               //Handle error case
            }

        }

    }
}