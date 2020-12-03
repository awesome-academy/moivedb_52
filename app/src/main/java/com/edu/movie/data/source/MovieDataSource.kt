package com.edu.movie.data.source

import com.edu.movie.data.source.remote.OnFetchDataJsonListener
import com.edu.movie.utils.TrendingMoviesType
import com.edu.movie.utils.TypeEndPointMovieDetails

interface MovieDataSource {
    /**
     *  Local
     */
    interface Local

    /**
     *  Remote
     */
    interface Remote {
        fun <T> getDataTrending(
            page: Int,
            trendingType: TrendingMoviesType,
            listener: OnFetchDataJsonListener<T>,
        )

        fun <T> getDataSlider(listener: OnFetchDataJsonListener<T>)

        fun <T> getMovieDetails(
            idMovie: Int,
            typeEndPoint: TypeEndPointMovieDetails,
            listener: OnFetchDataJsonListener<T>
        )

    }
}
