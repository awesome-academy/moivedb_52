package com.edu.movie.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres(val id: Int?, val name: String?) : Parcelable

object GenresEntry {
    const val ID = "id"
    const val NAME = "name"
    const val LIST_GENRES = "genres"
    const val LIST_GENRES_SEARCH = "genre_ids"
}
