package com.edu.movie.screen.search.filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.edu.movie.R
import com.edu.movie.data.model.Genres
import kotlinx.android.synthetic.main.item_spinner_search.view.*

class FilterAdapter<T> : BaseAdapter() {
    private val list = mutableListOf<T>()

    override fun getCount() = list.size

    override fun getItem(position: Int): T = list[position]

    override fun getItemId(position: Int): Long = when (val item = list[position]) {
        is Genres -> item.id?.toLong() ?: ID_DEFAULT
        is Int -> position.toLong()
        else -> ID_DEFAULT
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView
            ?: LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_spinner_search, parent, false)
        val viewHolder = ItemSpinnerHolder(view)
        when (val item = list[position]) {
            is Genres -> viewHolder.bindDataGenres(item)
            is Int -> viewHolder.bindDataRate(item)
        }
        return view
    }

    fun registerData(data: MutableList<T>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    companion object {
        private const val ID_DEFAULT = 0L
    }
}

class ItemSpinnerHolder(private val itemView: View) {
    fun bindDataGenres(genres: Genres) {
        itemView.apply {
            textViewContentSearch.text = genres.name
        }
    }

    fun bindDataRate(rateValue: Int) {
        itemView.apply {
            textViewContentSearch.text = rateValue.toString()
        }
    }
}
