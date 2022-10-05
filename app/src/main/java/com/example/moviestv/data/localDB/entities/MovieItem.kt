package com.example.moviestv.data.localDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "movie_items")
data class MovieItem(
    val adult: Boolean,
//    @SerializedName("backdrop_path")
    val backdropPath: String,
    val budget: Int,
    val homepage: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
  //  @SerializedName("imdb_id")
    val imdbId: String,
  //  @SerializedName("original_language")
    val originalLanguage: String,
  //  @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
  //  @SerializedName("poster_path")
    val posterPath: String,
  //  @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
  //  @SerializedName("vote_average")
    val voteAverage: Double,
  //  @SerializedName("vote_count")
    val voteCount: Int,
)
