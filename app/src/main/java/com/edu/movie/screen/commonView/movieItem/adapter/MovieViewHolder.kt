package com.edu.movie.screen.commonView.movieItem.adapter

import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edu.movie.R
import com.edu.movie.data.model.MovieItem
import com.edu.movie.utils.Constant
import com.edu.movie.utils.LoadImageFromUrl
import kotlinx.android.synthetic.main.item_movie_horizontal.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun bindData(movies: MovieItem) {
        itemView.apply {
            movies.rate?.let {
                textRate.text =
                    itemView.resources.getString(R.string.percent, (it * 10).toInt().toString())
            }
            textMovieName.text = movies.title
            LoadImageFromUrl { imageMoviesItem.setImageBitmap(it) }.executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,
                Constant.BASE_URL_IMAGE + movies.imageUrl
            )
        }
        itemView.setOnClickListener {
            movies.id?.let { it -> onItemClickListener?.invoke(it) }
        }
    }

    fun registerItemClickListener(onItemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }
}
