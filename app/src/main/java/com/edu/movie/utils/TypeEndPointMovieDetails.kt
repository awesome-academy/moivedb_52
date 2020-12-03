package com.edu.movie.utils

enum class TypeEndPointMovieDetails(val path: String) {
    MOVIE_DETAILS(""),
    VIDEO_YOUTUBE("videos"),
    CASTS("credits"),
    RECOMMENDATIONS("recommendations")
}