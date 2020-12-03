package com.edu.movie.data.model

data class VideoYoutube(val id: Int?, val keyYoutube: String?, val type: String?)

object VideoYoutubeEntry {
    const val LIST_VIDEOS = "results"
    const val ID = "id"
    const val KEY_YOUTUBE = "key"
    const val TYPE = "type"
}