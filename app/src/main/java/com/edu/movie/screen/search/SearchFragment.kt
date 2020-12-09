package com.edu.movie.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.movie.R
import com.edu.movie.data.model.Genres
import com.edu.movie.data.model.MovieItem
import com.edu.movie.data.source.repository.MovieRepository
import com.edu.movie.screen.commonView.movieItem.adapter.MoviesGridAdapter
import com.edu.movie.screen.moviedetails.MovieDetailsFragment
import com.edu.movie.screen.search.filter.FilterFragment
import com.edu.movie.utils.Constant
import com.edu.movie.utils.addFragment
import com.edu.movie.utils.showIconLoadMore
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.recyclerview_gridlayout.*

class SearchFragment : Fragment(), ViewContact.SearchView {

    private val adapterMovies by lazy { MoviesGridAdapter() }
    private var page = Constant.DEFAULT_PAGE
    private var isLoading = false
    private lateinit var genre: Genres
    private var rateValue: Double = Constant.NUMBER_0.toDouble()
    private var contentSearch: String? = null
    private var filterFragment: FilterFragment? = null
    private val presenterSearch: ViewContact.PresenterSearch by lazy {
        SearchPresenter(MovieRepository.instance).apply { setView(this@SearchFragment) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genre = Genres(Constant.NUMBER_0, getString(R.string.all))
        backPage()
        initSearchBar()
        initFilterSearch()
        initRecyclerview()
        reloadMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterSearch.onStop()
    }

    override fun onListenerAcceptFilter(rateValue: Double, genre: Genres) {
        page = Constant.DEFAULT_PAGE
        this.genre = genre
        this.rateValue = rateValue
        contentSearch?.let {
            presenterSearch.getMovies(it, genre.id, rateValue, page)
        }
        recyclerViewMoviesGrid.scrollToPosition(Constant.NUMBER_0)
        filterFragment?.dismiss()
    }

    override fun loadMoviesOnSuccess(movies: List<MovieItem>) {
        if (page > 1) adapterMovies.removeMoviesLastItem()
        if (!movies.isNullOrEmpty())
            if (page == 1) {
                adapterMovies.registerListMovies(movies.toMutableList())
                recyclerViewMoviesGrid.scrollToPosition(Constant.NUMBER_0)
            } else {
                adapterMovies.addMovies(movies.toMutableList())
                isLoading = false
            }
        if (movies.isNullOrEmpty() && page == 1) {
            adapterMovies.registerListMovies(movies.toMutableList())
        }
        swipeRefreshData.isRefreshing = false
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    private fun backPage() {
        btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun initFilterSearch() {
        btnFilterSearch.setOnClickListener {
            fragmentManager?.let {
                filterFragment =
                    FilterFragment.newInstance((rateValue * Constant.NUMBER_10).toInt(), genre)
                        .apply {
                            setView(this@SearchFragment)
                            show(it, null)
                        }
            }
        }
    }

    private fun initSearchBar() {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                btnFilterSearch.visibility = View.VISIBLE
                contentSearch = query
                query?.let {
                    page = Constant.DEFAULT_PAGE
                    genre = Genres(Constant.NUMBER_0, getString(R.string.all))
                    rateValue = Constant.NUMBER_0.toDouble()
                    presenterSearch.getMovies(it, genre.id, rateValue, page)
                }
                searchBar.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchBar.requestFocus()
    }

    private fun initRecyclerview() {
        recyclerViewMoviesGrid.apply {
            showIconLoadMore(adapterMovies.apply {
                registerOnItemClickListener {
                    addFragment(MovieDetailsFragment.newInstance(it), R.id.container)
                }
            })
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val gridLayoutManager = (layoutManager as GridLayoutManager)
                    val totalItemCount = gridLayoutManager.itemCount
                    val lastVisibleItem = gridLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (!isLoading && totalItemCount <= lastVisibleItem + Constant.VISIBLE_THRESHOLD) {
                        loadMoreData()
                        isLoading = true
                    }
                }
            })
        }
    }

    private fun loadMoreData() {
        if (!contentSearch.isNullOrBlank())
            recyclerViewMoviesGrid.post {
                adapterMovies.addMoviesNull()
                contentSearch?.let { presenterSearch.getMovies(it, genre.id, rateValue, ++page) }
            }
        else {
            isLoading = false
        }
    }

    private fun reloadMovies() {
        swipeRefreshData.setOnRefreshListener {
            if (!contentSearch.isNullOrBlank())
                page = Constant.DEFAULT_PAGE
            contentSearch?.let { presenterSearch.getMovies(it, genre.id, rateValue, page) }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
