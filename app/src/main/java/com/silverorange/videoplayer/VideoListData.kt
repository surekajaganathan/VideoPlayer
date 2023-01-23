package com.silverorange.videoplayer

import com.google.gson.annotations.SerializedName

data class VideoListData(
    var id: String,
    var title: String,
    @SerializedName("fullURL")
    var videoUrl: String,
    var description: String,
    var author: VideoAuthorData
)
