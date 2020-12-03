package com.edu.movie.screen.home

import com.edu.movie.data.model.ItemMovieSlider
import com.edu.movie.data.model.MovieItem
import com.edu.movie.data.source.remote.OnFetchDataJsonListener
import com.edu.movie.data.source.repository.MovieRepository
import com.edu.movie.screen.base.BasePresenter
import com.edu.movie.utils.Constant
import com.edu.movie.utils.TrendingMoviesType

class HomePresenter(private val repository: MovieRepository) : BasePresenter<ViewContactHome.View> {

    private var view: ViewContactHome.View? = null

    override fun onStart() {
        for (i in 0..3) {
            getMovie(TrendingMoviesType.values()[i])
        }
        getSliderMovie()
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: ViewContactHome.View?) {
        this.view = view
    }

    private fun getMovie(type: TrendingMoviesType) {
        repository.getListMovieTrending(
            Constant.DEFAULT_PAGE,
            type,
            object : OnFetchDataJsonListener<MutableList<MovieItem>> {
                override fun onSuccess(data: MutableList<MovieItem>) {
                    view?.getMovieSuccess(data, type)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            })
    }

    private fun getSliderMovie() {
        repository.getListMovieSlider(object :
            OnFetchDataJsonListener<MutableList<ItemMovieSlider>> {
            override fun onSuccess(data: MutableList<ItemMovieSlider>) {
                view?.getImageSliderSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }
}
