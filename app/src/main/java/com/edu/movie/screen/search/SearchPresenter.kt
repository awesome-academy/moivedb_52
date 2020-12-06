package com.edu.movie.screen.search

import com.edu.movie.data.model.MovieDetails
import com.edu.movie.data.model.MovieItem
import com.edu.movie.data.source.remote.OnFetchDataJsonListener
import com.edu.movie.data.source.repository.MovieRepository

class SearchPresenter(private val repository: MovieRepository) :
    ViewContact.PresenterSearch {

    private var view: ViewContact.SearchView? = null

    override fun onStart() {}

    override fun onStop() {
        view = null
    }

    override fun setView(view: ViewContact.SearchView?) {
        this.view = view
    }

    override fun getMovies(
        content: String,
        idGenres: Int?,
        topRate: Double,
        page: Int,
    ) {
        repository.getMoviesBySearch(
            content,
            page,
            object : OnFetchDataJsonListener<List<MovieDetails>> {
                override fun onSuccess(data: List<MovieDetails>) {
                    var movies = data.filter { movie -> movie.rate?.let { it >= topRate } ?: false }
                    idGenres?.let { id ->
                        if (id != 0)
                            movies = movies.filter { movie ->
                                !(movie.genres?.filter { it.id == id }.isNullOrEmpty())
                            }
                        view?.loadMoviesOnSuccess(mapListMovieDetailsToListMovieItem(movies))
                    }
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            })
    }

    private fun mapListMovieDetailsToListMovieItem(listMovieDetails: List<MovieDetails>): List<MovieItem> {
        val movies = mutableListOf<MovieItem>()
        listMovieDetails.forEach { movies.add(mapMovieDetailsToMovieItem(it)) }
        return movies.toList()
    }

    private fun mapMovieDetailsToMovieItem(movie: MovieDetails) = movie.run {
        MovieItem(movie.id, movie.title, movie.imagePosterUrl, movie.rate)
    }
}
