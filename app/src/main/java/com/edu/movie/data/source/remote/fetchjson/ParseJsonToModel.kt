package com.edu.movie.data.source.remote.fetchjson

import com.edu.movie.data.model.*
import com.edu.movie.utils.TypeModel
import org.json.JSONObject

@Suppress("UNCHECKED_CAST")
class ParseJsonToModel {

    @Throws(Exception::class)
    fun parseJsonToMovieItem(jsonObject: JSONObject?): MovieItem? {
        jsonObject?.apply {
            return MovieItem(
                getInt(MovieEntry.ID),
                getString(MovieEntry.TITLE),
                getString(MovieEntry.IMAGE_URL),
                getDouble(MovieEntry.RATE)
            )
        }
        return null
    }

    @Throws(Exception::class)
    fun parseJsonToMovieItemSlider(jsonObject: JSONObject?): ItemMovieSlider? {
        jsonObject?.apply {
            return ItemMovieSlider(
                getInt(ItemMovieSliderEntry.ID),
                getString(ItemMovieSliderEntry.IMAGE_URL)
            )
        }
        return null
    }

    @Throws(Exception::class)
    fun parseJsonToMovieDetails(jsonObject: JSONObject?): MovieDetails? {
        val parseJsonData = ParseDataWithJson()
        jsonObject?.apply {
            val nameCountries = getJSONArray(MoviesDetailsEntry.PRODUCTION_COUNTRY)
            val countryProduction =
                if (nameCountries.length() > 0) nameCountries.getJSONObject(0)
                    .getString(MoviesDetailsEntry.COUNTRY_NAME) else null
            val genres = parseJsonData.parseJson(
                getJSONArray(MoviesDetailsEntry.LIST_GENRES).toString(),
                TypeModel.GENRES
            ) as MutableList<Genres>
            val companies = parseJsonData.parseJson(
                getJSONArray(MoviesDetailsEntry.LIST_COMPANIES).toString(),
                TypeModel.COMPANY
            ) as MutableList<Company>
            return MovieDetails(
                getInt(MoviesDetailsEntry.ID),
                getString(MoviesDetailsEntry.TITLE),
                getString(MoviesDetailsEntry.IMAGE_URL),
                getDouble(MoviesDetailsEntry.RATE),
                countryProduction,
                genres,
                companies
            )
        }
        return null
    }

    @Throws(Exception::class)
    fun parseJsonToGenres(jsonObject: JSONObject?): Genres? {
        jsonObject?.apply {
            return Genres(
                getInt(GenresEntry.ID),
                getString(GenresEntry.NAME)
            )
        }
        return null
    }

    @Throws(Exception::class)
    fun parseJsonToCompany(jsonObject: JSONObject?): Company? {
        jsonObject?.apply {
            return Company(
                getInt(CompanyEntry.ID),
                getString(CompanyEntry.NAME),
                getString(CompanyEntry.LOGO_URL)
            )
        }
        return null
    }
}
