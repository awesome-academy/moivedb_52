package com.edu.movie.screen.moviedetails

import com.edu.movie.data.model.MovieDetails

interface MovieDetailsContact {
    interface View {
        fun getMovieDetailsSuccess(movieDetails: MovieDetails)
        fun onError(exception: Exception?)
    }
}
