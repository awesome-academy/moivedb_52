package com.edu.movie.screen.moviedetails

import com.edu.movie.data.model.MovieDetails
import com.edu.movie.data.source.remote.OnFetchDataJsonListener
import com.edu.movie.data.source.repository.MovieRepository
import com.edu.movie.screen.base.BasePresenter

class MovieDetailsPresenter(private val repository: MovieRepository) :
    BasePresenter<MovieDetailsContact.View> {
    private var view: MovieDetailsContact.View? = null

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun setView(view: MovieDetailsContact.View?) {
        this.view = view
    }

    fun getMovieDetails(id: Int) {
        repository.getMovieDetails(id, object : OnFetchDataJsonListener<MovieDetails> {
            override fun onSuccess(data: MovieDetails) {
                view?.getMovieDetailsSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }

        })
    }
}