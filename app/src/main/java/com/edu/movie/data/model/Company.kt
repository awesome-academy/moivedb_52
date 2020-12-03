package com.edu.movie.data.model

data class Company(val id: Int?, val name: String?, val logoUrl: String?)

object CompanyEntry {
    const val ID = "id"
    const val NAME = "name"
    const val LOGO_URL = "logo_path"
}
