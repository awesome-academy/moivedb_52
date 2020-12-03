package com.edu.movie.data.model

data class Cast(val id: Int?, val name: String?, val imageUrl: String?)

object CastEntry {
    const val ID = "id"
    const val NAME = "name"
    const val IMAGE_URL = "profile_path"
}