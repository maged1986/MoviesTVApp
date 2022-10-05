package com.example.moviestv.domain.models

data class MovieRow(
    val movieName:String?= null,
    val moviePosterUrl: String?= null,
    val id: Int=0,
    val release_date: String?= null,
    val overview: String?= null
):java.io.Serializable
