package com.edu.movie.screen.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.movie.R
import com.edu.movie.data.model.MovieItem
import com.edu.movie.data.source.repository.MovieRepository
import com.edu.movie.screen.commonView.movieItem.adapter.MoviesGridAdapter
import com.edu.movie.screen.moviedetails.MovieDetailsFragment
import com.edu.movie.utils.Constant
import com.edu.movie.utils.TrendingMoviesType
import com.edu.movie.utils.addFragment
import kotlinx.android.synthetic.main.recyclerview_gridlayout.*

class MoviesTrendingFragment : Fragment(), ViewContactTrending.View {

    private var trendingType: TrendingMoviesType? = null
    private val adapterTrendingMovies by lazy { MoviesGridAdapter() }
    private val presenterTrending by lazy { TrendingPresenter(MovieRepository.instance) }
    private var page = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trendingType = it.getSerializable(ARGUMENT_TRENDING) as TrendingMoviesType?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onItemRecyclerViewClickListener()
        initRecyclerView()
        initPresenter()
        initSwipeRefresh()
    }

    override fun getMovieSuccess(listMovies: MutableList<MovieItem>) {
        if (page == 1) {
            adapterTrendingMovies.registerListMovies(listMovies)
            swipeRefreshData.isRefreshing = false
        } else {
            adapterTrendingMovies.removeMoviesLastItem()
            adapterTrendingMovies.addMovies(listMovies)
            isLoading = false
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        recyclerViewMoviesGrid.apply {
            setHasFixedSize(true)
            adapter = adapterTrendingMovies
            val gridLayoutManager = (layoutManager as GridLayoutManager)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterTrendingMovies.getItemViewType(position)) {
                        MoviesGridAdapter.TYPE_PROGRESSBAR -> gridLayoutManager.spanCount
                        MoviesGridAdapter.TYPE_MOVIE_ITEM -> MoviesGridAdapter.TYPE_MOVIE_ITEM
                        else -> DO_SOME_THING
                    }
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = gridLayoutManager.itemCount
                    val lastVisibleItem = gridLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (!isLoading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                        loadMoreData()
                        isLoading = true
                    }
                }
            })
        }
    }

    private fun initPresenter() {
        presenterTrending.apply {
            setView(this@MoviesTrendingFragment)
            trendingType?.let { getListMovie(Constant.DEFAULT_PAGE, it) }
        }
    }

    private fun initSwipeRefresh() {
        swipeRefreshData.setOnRefreshListener {
            page = Constant.DEFAULT_PAGE
            trendingType?.let { presenterTrending.getListMovie(page, it) }
        }
    }

    private fun loadMoreData() {
        trendingType?.let {
            recyclerViewMoviesGrid.post {
                adapterTrendingMovies.addMoviesNull()
                page++
                presenterTrending.getListMovie(page, it)
            }
        }
    }

    private fun onItemRecyclerViewClickListener() {
        adapterTrendingMovies.registerOnItemClickListener {
            (activity as AppCompatActivity).addFragment(
                MovieDetailsFragment.newInstance(it),
                R.id.container
            )
        }
    }

    companion object {
        private const val ARGUMENT_TRENDING = "ARGUMENT_TRENDING"
        private const val DO_SOME_THING = -1
        private const val VISIBLE_THRESHOLD = 2

        @JvmStatic
        fun newInstance(param: TrendingMoviesType) = MoviesTrendingFragment().apply {
            arguments = bundleOf(ARGUMENT_TRENDING to param)
        }
    }
}
