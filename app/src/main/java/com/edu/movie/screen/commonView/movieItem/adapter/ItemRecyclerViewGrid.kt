package com.edu.movie.screen.commonView.movieItem.adapter

import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edu.movie.R
import com.edu.movie.data.model.MovieItem
import com.edu.movie.utils.Constant
import com.edu.movie.utils.LoadImageFromUrl
import kotlinx.android.synthetic.main.item_load_more.view.*
import kotlinx.android.synthetic.main.item_movie_grid_layout.view.*

class ItemMovieGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun bindData(movieItem: MovieItem) {
        itemView.apply {
            textViewTitleMovieGrid.text = movieItem.title
            movieItem.rate?.let {
                textViewRateMovieGrid.text =
                    itemView.resources.getString(R.string.percent, (it * 10).toInt().toString())
            }
            imageViewMovieGrid.setImageDrawable(null)
            LoadImageFromUrl {
                imageViewMovieGrid.setImageBitmap(it)
            }.executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,
                Constant.BASE_URL_IMAGE + movieItem.imageUrl
            )
            movieItem.id?.let { id ->
                setOnClickListener { onItemClickListener?.invoke(id) }
            }
        }
    }

    fun registerItemClickListener(onItemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }
}

class LoadItemGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun binData() {
        itemView.progressBarLoadMore.isEnabled = true
    }
}
