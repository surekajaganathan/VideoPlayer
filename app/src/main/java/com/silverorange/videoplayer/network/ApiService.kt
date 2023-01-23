package com.silverorange.videoplayer.network

import com.silverorange.videoplayer.VideoListData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/videos")
    suspend fun getVideos(): Response<List<VideoListData>>
}