package com.edu.movie.data.source.repository

import com.edu.movie.data.model.MovieDetails
import com.edu.movie.data.model.MovieItem
import com.edu.movie.data.source.MovieDataSource
import com.edu.movie.data.source.local.MovieLocalDataSource
import com.edu.movie.data.source.remote.MovieRemoteDataSource
import com.edu.movie.data.source.remote.OnFetchDataJsonListener
import com.edu.movie.utils.TrendingMoviesType
import com.edu.movie.utils.TypeEndPointMovieDetails

class MovieRepository private constructor(
    private val local: MovieDataSource.Local,
    private val remote: MovieDataSource.Remote
) {

    private object Holder {
        val INSTANCE = MovieRepository(
            local = MovieLocalDataSource.instance,
            remote = MovieRemoteDataSource.instance
        )
    }

    fun getListMovieTrending(
        page: Int,
        trendingType: TrendingMoviesType,
        listener: OnFetchDataJsonListener<MutableList<MovieItem>>,
    ) {
        remote.getDataTrending(page, trendingType, listener)
    }

    fun getListMovieSlider(listener: OnFetchDataJsonListener<MutableList<MovieItem>>) {
        remote.getDataSlider(listener)
    }

    fun getMovieDetails(id: Int, listener: OnFetchDataJsonListener<MovieDetails>) {
        remote.getMovieDetails(id, TypeEndPointMovieDetails.MOVIE_DETAILS, listener)
    }

    companion object {
        val instance: MovieRepository by lazy { Holder.INSTANCE }
    }
}
