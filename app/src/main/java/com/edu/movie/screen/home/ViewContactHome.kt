package com.edu.movie.screen.home

import com.edu.movie.data.model.ItemMovieSlider
import com.edu.movie.data.model.MovieItem
import com.edu.movie.utils.TrendingMoviesType

interface ViewContactHome{
    interface View{
        fun getMovieSuccess(listMovies: MutableList<MovieItem>, trendingMoviesType: TrendingMoviesType)

        fun onError(exception: Exception?)

        fun getImageSliderSuccess(listMovies: MutableList<ItemMovieSlider>)
    }
}
