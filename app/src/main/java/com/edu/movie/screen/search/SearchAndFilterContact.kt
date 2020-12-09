package com.edu.movie.screen.search

import com.edu.movie.data.model.Genres
import com.edu.movie.data.model.MovieItem
import com.edu.movie.screen.base.BasePresenter

interface ViewContact {
    interface SearchView {
        fun onListenerAcceptFilter(rateValue: Double, genre: Genres)
        fun loadMoviesOnSuccess(movies: List<MovieItem>)
        fun onError(exception: Exception?)
    }

    interface FilterView {
        fun setView(view: SearchView)
        fun loadGenresOnSuccess(genres: List<Genres>)
        fun onError(exception: Exception?)
    }

    interface PresenterSearch : BasePresenter<SearchView> {
        fun getMovies(
            content: String,
            idGenres: Int?,
            topRate: Double,
            page: Int,
        )
    }
}
