package com.edu.movie.screen.sliderView.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.edu.movie.R
import com.edu.movie.data.model.ItemMovieSlider

class SliderViewPagerAdapter(
    private val listSliderMovieSlider: MutableList<ItemMovieSlider>,
    viewPagerSliderMovie: ViewPager2?
) : RecyclerView.Adapter<SliderMovieViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderMovieViewHolder {
        return SliderMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_slider, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SliderMovieViewHolder, position: Int) {
        holder.apply {
            registerItemClickListener(onItemClickListener)
            bindData(listSliderMovieSlider[position])
        }
    }

    override fun getItemCount() = listSliderMovieSlider.size

    fun registerOnItemClickListener(clickListener: (Int) -> Unit) {
        onItemClickListener = clickListener
        notifyDataSetChanged()
    }
}
