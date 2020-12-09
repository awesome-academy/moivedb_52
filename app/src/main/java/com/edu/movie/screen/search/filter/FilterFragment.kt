package com.edu.movie.screen.search.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.edu.movie.R
import com.edu.movie.data.model.Genres
import com.edu.movie.data.source.repository.MovieRepository
import com.edu.movie.screen.base.BasePresenter
import com.edu.movie.screen.search.ViewContact
import com.edu.movie.screen.search.filter.adapter.FilterAdapter
import com.edu.movie.utils.Constant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : BottomSheetDialogFragment(), ViewContact.FilterView {

    private var view: ViewContact.SearchView? = null
    private val adapterTopRate by lazy { FilterAdapter<Int>() }
    private val adapterGenres by lazy { FilterAdapter<Genres>() }
    private val presenterFilter: BasePresenter<ViewContact.FilterView> by lazy {
        FilterPresenter(MovieRepository.instance)
    }
    private var genre: Genres? = null
    private var rateValue = Constant.NUMBER_10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            genre = it.getParcelable(ARG_GENRE)
            rateValue = it.getInt(ARG_RATE_VALUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater
            .inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initPresenter()
        setOnclickFilter()
    }

    override fun onDestroy() {
        super.onDestroy()
        view = null
        presenterFilter.onStop()
    }

    override fun setView(view: ViewContact.SearchView) {
        this.view = view
    }

    override fun loadGenresOnSuccess(genres: List<Genres>) {
        val listGenres = mutableListOf<Genres>()
        listGenres.add(Genres(Constant.NUMBER_0, getString(R.string.all)))
        listGenres.addAll(genres)
        adapterGenres.registerData(listGenres)
        spinnerGenres.setSelection(listGenres.indexOf(genre))
        spinnerTopRate.setSelection(listRateValue.indexOf(rateValue))
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    private fun initSpinner() {
        spinnerGenres.adapter = adapterGenres
        spinnerTopRate.adapter = adapterTopRate.apply {
            registerData(listRateValue)
        }
    }

    private fun initPresenter() {
        presenterFilter.apply {
            setView(this@FilterFragment)
            onStart()
        }
    }

    private fun setOnclickFilter() {
        btnApply.setOnClickListener {
            val rateValue = spinnerTopRate.selectedItem as Int
            val genre = spinnerGenres.run {
                selectedItem?.let { it as Genres }
            }
            genre?.let { it ->
                view?.onListenerAcceptFilter(
                    (rateValue / Constant.NUMBER_10).toDouble(),
                    it
                )
            }
        }
    }

    companion object {
        private val listRateValue: MutableList<Int> =
            (0..10).map { it * Constant.NUMBER_10 }.toMutableList()
        private const val ARG_GENRE = "ARG_GENRE"
        private const val ARG_RATE_VALUE = "ARG_RATE_VALUE"

        @JvmStatic
        fun newInstance(rateValue: Int, genre: Genres?) = FilterFragment().apply {
            arguments = bundleOf(ARG_RATE_VALUE to rateValue, ARG_GENRE to genre)
        }
    }
}
