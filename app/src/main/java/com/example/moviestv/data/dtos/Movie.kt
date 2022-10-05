package com.example.moviestv.data.dtos


data class Movie(
    val id: Long,
   // @SerializedName("poster_path")
    val posterPath: String,
   // @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
)
