package com.edu.movie.data.source.remote.fetchjson

import com.edu.movie.data.model.*
import com.edu.movie.utils.Constant
import com.edu.movie.utils.TypeModel
import org.json.JSONObject

@Suppress("UNCHECKED_CAST")
class ParseJsonToModel {

    @Throws(Exception::class)
    fun parseJsonToMovieItem(jsonObject: JSONObject?): MovieItem? =
        jsonObject?.run {
            MovieItem(
                getInt(MovieEntry.ID),
                getString(MovieEntry.TITLE),
                if (getString(MovieEntry.IMAGE_URL) == Constant.NULL_STRING) null
                else getString(MovieEntry.IMAGE_URL),
                getDouble(MovieEntry.RATE)
            )
        }

    @Throws(Exception::class)
    fun parseJsonToMovieItemSlider(jsonObject: JSONObject?): ItemMovieSlider? =
        jsonObject?.run {
            ItemMovieSlider(
                getInt(ItemMovieSliderEntry.ID),
                if (getString(ItemMovieSliderEntry.IMAGE_URL) == Constant.NULL_STRING) null
                else getString(ItemMovieSliderEntry.IMAGE_URL)
            )
        }

    @Throws(Exception::class)
    fun parseJsonToMovieDetails(jsonObject: JSONObject?): MovieDetails? =
        jsonObject?.run {
            val parseJsonData = ParseDataWithJson()
            val nameCountries = getJSONArray(MoviesDetailsEntry.PRODUCTION_COUNTRY)
            val countryProduction =
                if (nameCountries.length() > 0) nameCountries.getJSONObject(0)
                    .getString(MoviesDetailsEntry.COUNTRY_NAME) else null
            val genres = parseJsonData.parseJson(
                getJSONArray(MoviesDetailsEntry.LIST_GENRES).toString(),
                TypeModel.GENRES_DETAIL_MOVIE
            ) as MutableList<Genres>
            val companies = parseJsonData.parseJson(
                getJSONArray(MoviesDetailsEntry.LIST_COMPANIES).toString(),
                TypeModel.COMPANY
            ) as MutableList<Company>
            MovieDetails(
                getInt(MoviesDetailsEntry.ID),
                getString(MoviesDetailsEntry.TITLE),
                if (getString(MoviesDetailsEntry.IMAGE_URL) == Constant.NULL_STRING) null
                else getString(MoviesDetailsEntry.IMAGE_URL),
                if (getString(MoviesDetailsEntry.IMAGE_POSTER_URL) == Constant.NULL_STRING) null
                else getString(MoviesDetailsEntry.IMAGE_POSTER_URL),
                getDouble(MoviesDetailsEntry.RATE),
                countryProduction,
                getString(MoviesDetailsEntry.DESCRIPTION),
                genres,
                companies
            )
        }

    @Throws(Exception::class)
    fun parseJsonToVideosYoutube(jsonObject: JSONObject?): VideoYoutube? =
        jsonObject?.run {
            VideoYoutube(
                getString(VideoYoutubeEntry.ID),
                getString(VideoYoutubeEntry.KEY_YOUTUBE),
                getString(VideoYoutubeEntry.TYPE)
            )
        }

    @Throws(Exception::class)
    fun parseJsonToCast(jsonObject: JSONObject?): Cast? =
        jsonObject?.run {
            Cast(
                getInt(CastEntry.ID),
                getString(CastEntry.NAME),
                if (getString(CastEntry.IMAGE_URL) == Constant.NULL_STRING) null
                else getString(CastEntry.IMAGE_URL)
            )
        }

    @Throws(Exception::class)
    fun parseJsonToGenres(jsonObject: JSONObject?): Genres? =
        jsonObject?.run {
            Genres(
                getInt(GenresEntry.ID),
                getString(GenresEntry.NAME)
            )
        }

    @Throws(Exception::class)
    fun parseJsonToCompany(jsonObject: JSONObject?): Company? =
        jsonObject?.run {
            Company(
                getInt(CompanyEntry.ID),
                getString(CompanyEntry.NAME),
                if (getString(CompanyEntry.LOGO_URL) == Constant.NULL_STRING) null
                else getString(CompanyEntry.LOGO_URL)
            )
        }

    @Throws(Exception::class)
    fun parseJsonToMovieForSearch(jsonObject: JSONObject?): MovieDetails? =
        jsonObject?.run {
            val genresId =
                getJSONArray(GenresEntry.LIST_GENRES_SEARCH)
            val genres = mutableListOf<Genres>()
            for (i in 0 until genresId.length()) {
                genres.add(Genres(genresId[i].toString().toInt(), null))
            }
            MovieDetails(
                getInt(MoviesDetailsEntry.ID),
                getString(MoviesDetailsEntry.TITLE),
                if (getString(MoviesDetailsEntry.IMAGE_URL) == Constant.NULL_STRING) null
                else getString(MoviesDetailsEntry.IMAGE_URL),
                if (getString(MoviesDetailsEntry.IMAGE_POSTER_URL) == Constant.NULL_STRING) null
                else getString(MoviesDetailsEntry.IMAGE_POSTER_URL),
                getDouble(MoviesDetailsEntry.RATE),
                null,
                getString(MoviesDetailsEntry.DESCRIPTION),
                genres,
                null
            )
        }
}
