package com.edu.movie.screen.sliderView.adapter

import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edu.movie.data.model.ItemMovieSlider
import com.edu.movie.utils.Constant
import com.edu.movie.utils.LoadImageFromUrl
import kotlinx.android.synthetic.main.item_movie_slider.view.*

class SliderMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun bindData(movieSlider: ItemMovieSlider) {
        itemView.apply {
            LoadImageFromUrl { imageMovieSlider.setImageBitmap(it) }.executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR, Constant.BASE_URL_IMAGE + movieSlider.imageSliderUrl
            )
        }
        itemView.setOnClickListener {
            movieSlider.id?.let { it -> onItemClickListener?.invoke(it) }
        }
    }

    fun registerItemClickListener(onItemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }
}
