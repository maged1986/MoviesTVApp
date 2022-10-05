package com.example.moviestv.domain.repository

import androidx.lifecycle.LiveData
import com.example.moviestv.data.dtos.MainResponse
import com.example.moviestv.data.dtos.MovieDetails
import com.example.moviestv.data.dtos.MovieResponse
import com.example.moviestv.data.dtos.VideoResponse
import com.example.moviestv.data.localDB.entities.MovieItem
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesTvRepository {
    fun insertMovie(movieItem: MovieItem?)

    fun deleteMovie(id: Int?)

    fun deleteAll()

    fun getMovies(): LiveData<List<MovieItem?>?>?

   fun getPopularMovie(api_key: String, page: Int): Observable<MainResponse>

    fun getNowPlayingMovie(api_key:String, page: Int): Observable<MainResponse>

    fun getTopRatedMovie(api_key:String, page: Int): Observable<MainResponse>

    fun getMovieDetails( id: Long,api_key:String): Single<MovieDetails>

    fun getVideos( id: Long,api_key:String): Flowable<VideoResponse>
}