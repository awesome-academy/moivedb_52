package com.edu.movie.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.edu.movie.R
import com.edu.movie.screen.favorite.FavoriteFragment
import com.edu.movie.screen.genres.GenresFragment
import com.edu.movie.screen.home.HomeContentFragment
import com.edu.movie.screen.main.homePage.ViewPagerContainerAdapter
import com.edu.movie.screen.trending.TrendingFragment
import com.edu.movie.screen.utils.MenuItem
import kotlinx.android.synthetic.main.fragment_home_page.*

class HomePageFragment private constructor() : Fragment() {

    private val fragments = mutableListOf<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListFragment()
        fragmentManager?.let {
            viewPageContainer.adapter =
                ViewPagerContainerAdapter(it, fragments)
            initBottomBar()
        }
    }

    private fun initListFragment() {
        fragments.add(MenuItem.HOME.ordinal, HomeContentFragment.newInstance())
        fragments.add(MenuItem.TRENDING.ordinal, TrendingFragment.newInstance())
        fragments.add(MenuItem.GENRES.ordinal, GenresFragment.newInstance())
        fragments.add(MenuItem.FAVORITE.ordinal, FavoriteFragment.newInstance())
    }

    private fun initBottomBar() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homePage -> {
                    textHeader.text = getText(R.string.movie)
                    viewPageContainer.currentItem = MenuItem.HOME.ordinal
                    true
                }
                R.id.trendingPage -> {
                    textHeader.text = getText(R.string.trending)
                    viewPageContainer.currentItem = MenuItem.TRENDING.ordinal
                    true
                }
                R.id.genresPage -> {
                    textHeader.text = getText(R.string.genres)
                    viewPageContainer.currentItem = MenuItem.GENRES.ordinal
                    true
                }
                R.id.favoritePage -> {
                    textHeader.text = getText(R.string.favorite)
                    viewPageContainer.currentItem = MenuItem.FAVORITE.ordinal
                    true
                }
                else -> false
            }
        }
        viewPageContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigation.menu.getItem(position).isChecked = true
                when (position) {
                    MenuItem.HOME.ordinal -> textHeader.text = getText((R.string.movie))
                    MenuItem.TRENDING.ordinal -> textHeader.text = getText((R.string.trending))
                    MenuItem.GENRES.ordinal -> textHeader.text = getText((R.string.genres))
                    MenuItem.FAVORITE.ordinal -> textHeader.text = getText((R.string.favorite))
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    companion object {
        fun newInstance() = HomePageFragment()
    }
}
