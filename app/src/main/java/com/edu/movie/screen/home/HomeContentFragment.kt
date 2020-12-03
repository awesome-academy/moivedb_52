package com.edu.movie.screen.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.edu.movie.R
import com.edu.movie.data.model.ItemMovieSlider
import com.edu.movie.data.model.MovieItem
import com.edu.movie.data.source.repository.MovieRepository
import com.edu.movie.screen.commonView.movieItem.adapter.MoviesHorizontalAdapter
import com.edu.movie.screen.moviedetails.MovieDetailsFragment
import com.edu.movie.screen.sliderView.adapter.SliderViewPagerAdapter
import com.edu.movie.utils.TrendingMoviesType
import com.edu.movie.utils.addFragment
import kotlinx.android.synthetic.main.fragment_home_content.*

class HomeContentFragment : Fragment(), ViewContactHome.View {

    private val popularAdapter by lazy { MoviesHorizontalAdapter() }
    private val upComingAdapter by lazy { MoviesHorizontalAdapter() }
    private val nowPlayingAdapter by lazy { MoviesHorizontalAdapter() }
    private val topRateAdapter by lazy { MoviesHorizontalAdapter() }
    private var moviesSlider = mutableListOf<ItemMovieSlider>()
    private var getCurrentItem = 0
    private var isChecked = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initData()
    }

    override fun getMovieSuccess(
        listMovies: MutableList<MovieItem>,
        trendingMoviesType: TrendingMoviesType
    ) {
        when (trendingMoviesType) {
            TrendingMoviesType.POPULAR -> popularAdapter.registerData(listMovies)
            TrendingMoviesType.NOW_PLAYING -> nowPlayingAdapter.registerData(listMovies)
            TrendingMoviesType.UP_COMING -> upComingAdapter.registerData(listMovies)
            TrendingMoviesType.TOP_RATED -> topRateAdapter.registerData(listMovies)
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_LONG).show()
    }

    override fun getImageSliderSuccess(listMovies: MutableList<ItemMovieSlider>) {
        moviesSlider = listMovies
        applySliderViewPage(viewPagerMovie)
    }

    private fun initRecyclerView() {
        applyRecyclerView(recyclerViewPopular, popularAdapter)
        applyRecyclerView(recyclerViewUpcoming, upComingAdapter)
        applyRecyclerView(recyclerViewNowPlaying, nowPlayingAdapter)
        applyRecyclerView(recyclerViewRate, topRateAdapter)
        initAdapter()
    }

    private fun applyRecyclerView(
        recyclerView: RecyclerView,
        adapterMoviesHorizontalAdapter: MoviesHorizontalAdapter
    ) {
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = adapterMoviesHorizontalAdapter
        }
    }

    private fun initAdapter() {
        onClickListenerMoviesHorizontal(popularAdapter)
        onClickListenerMoviesHorizontal(nowPlayingAdapter)
        onClickListenerMoviesHorizontal(upComingAdapter)
        onClickListenerMoviesHorizontal(topRateAdapter)
    }

    private fun initData() {
        HomePresenter(MovieRepository.instance).apply {
            setView(this@HomeContentFragment)
            onStart()
        }
    }

    @SuppressLint("WrongConstant")
    private fun applySliderViewPage(applyViewPageSlider: ViewPager2) {
        applyViewPageSlider.apply {
            adapter = SliderViewPagerAdapter(moviesSlider, applyViewPageSlider).apply {
                registerOnItemClickListener {
                    (activity as AppCompatActivity).addFragment(
                        MovieDetailsFragment.newInstance(it),
                        R.id.container
                    )
                }
            }
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
            getChildAt(GET_ITEM_START).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val handlerSlider = Looper.myLooper()?.let { Handler(it) }
                    super.onPageSelected(position)
                    handlerSlider?.postDelayed({
                        if (getCurrentItem == moviesSlider.size - ITEM_NUMBER) {
                            isChecked = false
                        }
                        if (getCurrentItem == GET_ITEM_START) {
                            isChecked = true
                        }
                        getCurrentItem =
                            if (isChecked) getCurrentItem + ITEM_NUMBER else getCurrentItem - ITEM_NUMBER
                        viewPagerMovie?.currentItem = getCurrentItem
                    }, DELAY_NEXT_ITEM)
                }
            })
        }
    }

    private fun onClickListenerMoviesHorizontal(moviesHorizontalAdapter: MoviesHorizontalAdapter) {
        moviesHorizontalAdapter.registerOnItemClickListener {
            (activity as AppCompatActivity).addFragment(
                MovieDetailsFragment.newInstance(it),
                R.id.container
            )
        }
    }

    companion object {
        fun newInstance() = HomeContentFragment()
        private const val OFF_SCREEN_PAGE_LIMIT = 3
        private const val DELAY_NEXT_ITEM = 2000L
        private const val GET_ITEM_START = 0
        private const val ITEM_NUMBER = 1
    }
}
