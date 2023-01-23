package com.silverorange.videoplayer

import com.silverorange.videoplayer.network.ApiService
import com.silverorange.videoplayer.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class VideoPlayerRepository {

    suspend fun getVideoList(): List<VideoListData> {
        val response: Response<List<VideoListData>>
        withContext(Dispatchers.IO) {
            response = RetrofitBuilder.getRetrofit().create(ApiService::class.java).getVideos()
        }
        return if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            listOf<VideoListData>()
        }
    }
}