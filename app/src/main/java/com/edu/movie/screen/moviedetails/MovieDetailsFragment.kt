package com.edu.movie.screen.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.edu.movie.R
import com.edu.movie.data.model.MovieDetails
import com.edu.movie.data.source.repository.MovieRepository

class MovieDetailsFragment : Fragment(), MovieDetailsContact.View {
    private var idMovieDetails: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idMovieDetails = it.getInt(ARG_ID_MOVIE_DETAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
    }

    private fun initPresenter() {
        MovieDetailsPresenter(MovieRepository.instance).apply {
            setView(this@MovieDetailsFragment)
            idMovieDetails?.let { getMovieDetails(it) }
        }
    }

    companion object {
        private const val ARG_ID_MOVIE_DETAIL = "ARG_ID_MOVIE_DETAIL"

        @JvmStatic
        fun newInstance(id: Int) =
            MovieDetailsFragment().apply {
                arguments = bundleOf(ARG_ID_MOVIE_DETAIL to id)
            }
    }

    override fun getMovieDetailsSuccess(movieDetails: MovieDetails) {
        Toast.makeText(context, movieDetails.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }
}
