package com.edu.movie.screen.commonView.movieItem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edu.movie.R
import com.edu.movie.data.model.MovieItem

class MoviesGridAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val movies = mutableListOf<MovieItem?>()
    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        movies[position]?.let {
            return TYPE_MOVIE_ITEM
        }
        return TYPE_PROGRESSBAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_MOVIE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie_grid_layout, parent, false)
                ItemMovieGridViewHolder(view)
            }
            else -> LoadItemGridViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemMovieGridViewHolder -> movies[position]?.let {
                holder.registerItemClickListener(onItemClickListener)
                holder.bindData(it)
            }
            is LoadItemGridViewHolder -> holder.binData()
        }
    }

    override fun getItemCount() = movies.size

    fun registerListMovies(movies: MutableList<MovieItem>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun registerOnItemClickListener(clickListener: (Int) -> Unit) {
        onItemClickListener = clickListener
        notifyDataSetChanged()
    }

    fun addMovies(movies: MutableList<MovieItem>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun addMoviesNull() {
        movies.add(null)
        notifyItemInserted(movies.size - 1)
    }

    fun removeMoviesLastItem() {
        movies.removeLastOrNull()
        notifyItemRemoved(movies.size - 1)
    }

    companion object {
        const val TYPE_PROGRESSBAR = 0
        const val TYPE_MOVIE_ITEM = 1
    }
}
