package com.edu.movie.screen.search.filter

import com.edu.movie.data.model.Genres
import com.edu.movie.data.source.remote.OnFetchDataJsonListener
import com.edu.movie.data.source.repository.MovieRepository
import com.edu.movie.screen.base.BasePresenter
import com.edu.movie.screen.search.ViewContact

class FilterPresenter(private val repository: MovieRepository) :
    BasePresenter<ViewContact.FilterView> {

    private var view: ViewContact.FilterView? = null

    override fun onStart() {
        getGenres()
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: ViewContact.FilterView?) {
        this.view = view
    }

    private fun getGenres() {
        repository.getListGenres(object : OnFetchDataJsonListener<List<Genres>> {
            override fun onSuccess(data: List<Genres>) {
                view?.loadGenresOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }
}
