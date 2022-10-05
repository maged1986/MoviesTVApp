package com.example.moviestv.data.reomteApi

import com.example.moviestv.data.dtos.MainResponse
import com.example.moviestv.data.dtos.MovieDetails
import com.example.moviestv.data.dtos.MovieResponse
import com.example.moviestv.data.dtos.VideoResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesTvApi {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") api_key:String,
        @Query("page") page: Int
    ): Observable<MainResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") api_key:String,
        @Query("page") page: Int
    ): Observable<MainResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") api_key:String,
        @Query("page") page: Int
    ): Observable<MainResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Long,
        @Query("api_key") api_key:String,
    ): Single<MovieDetails>

    @GET("movie/{movie_id}/videos")
    fun getVideos(
        @Path("movie_id") id: Long,
        @Query("api_key") api_key:String,
    ): Flowable<VideoResponse>

}