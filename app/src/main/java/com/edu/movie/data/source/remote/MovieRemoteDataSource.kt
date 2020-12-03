package com.edu.movie.data.source.remote

import com.edu.movie.data.source.MovieDataSource
import com.edu.movie.data.source.remote.fetchjson.GetJsonFromUrl
import com.edu.movie.utils.Constant
import com.edu.movie.utils.TrendingMoviesType
import com.edu.movie.utils.TypeEndPointMovieDetails
import com.edu.movie.utils.TypeModel

class MovieRemoteDataSource : MovieDataSource.Remote {

    private val endPointParams = Constant.BASE_API_KEY + Constant.BASE_LANGUAGE

    private object Holder {
        val INSTANCE = MovieRemoteDataSource()
    }

    override fun <T> getDataTrending(
        page: Int,
        trendingType: TrendingMoviesType,
        listener: OnFetchDataJsonListener<T>
    ) {
        val stringUrl =
            Constant.BASE_URL + MEDIA_TYPE + trendingType.path + endPointParams + Constant.BASE_PAGE + page
        GetJsonFromUrl(TypeModel.MOVIE_ITEM_TRENDING, listener).execute(stringUrl)
    }

    override fun <T> getDataSlider(listener: OnFetchDataJsonListener<T>) {
        val stringUrl =
            Constant.BASE_URL + TRENDING_TOP + MEDIA_TYPE + TIME_WINDOW + endPointParams
        GetJsonFromUrl(TypeModel.MOVIE_ITEM_SLIDER, listener).execute(stringUrl)
    }

    override fun <T> getMovieDetails(
        idMovie: Int,
        typeEndPoint: TypeEndPointMovieDetails,
        listener: OnFetchDataJsonListener<T>
    ) {
        val stringUrl =
            Constant.BASE_URL + MEDIA_TYPE + idMovie + typeEndPoint.path + endPointParams
        when (typeEndPoint) {
            TypeEndPointMovieDetails.MOVIE_DETAILS -> GetJsonFromUrl(
                TypeModel.MOVIE_DETAILS,
                listener
            ).execute(stringUrl)
            TypeEndPointMovieDetails.CASTS -> GetJsonFromUrl(TypeModel.CAST, listener).execute(
                stringUrl
            )
            TypeEndPointMovieDetails.VIDEO_YOUTUBE -> GetJsonFromUrl(
                TypeModel.VIDEO_YOUTUBE,
                listener
            ).execute(stringUrl)
            TypeEndPointMovieDetails.RECOMMENDATIONS -> GetJsonFromUrl(
                TypeModel.MOVIE_ITEM_TRENDING,
                listener
            ).execute(stringUrl)
        }
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
        private const val TRENDING_TOP = "trending/"
        private const val MEDIA_TYPE = "movie/"
        private const val TIME_WINDOW = "day"
    }
}
