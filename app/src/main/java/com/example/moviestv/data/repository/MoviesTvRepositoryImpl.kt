package com.example.moviestv.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.moviestv.data.dtos.MainResponse
import com.example.moviestv.data.dtos.MovieDetails
import com.example.moviestv.data.dtos.MovieResponse
import com.example.moviestv.data.dtos.VideoResponse
import com.example.moviestv.data.localDB.entities.MovieItem
import com.example.moviestv.di.RetrofitModule
import com.example.moviestv.di.RoomDbModule
import com.example.moviestv.domain.repository.MoviesTvRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MoviesTvRepositoryImpl @Inject constructor(
   // private val dao: MoviesTvDao,
  //  private val api: MoviesTvApi
): MoviesTvRepository {
    override fun insertMovie(movieItem: MovieItem?) {
      RoomDbModule.provideMoviesTvDao(RoomDbModule.provideMoviesTvDb(Application())).insertMovie(movieItem)
    }

    override fun deleteMovie(id: Int?) {
        RoomDbModule.provideMoviesTvDao(RoomDbModule.provideMoviesTvDb(Application())).deleteMovie(id)
    }

    override fun deleteAll() {
        RoomDbModule.provideMoviesTvDao(RoomDbModule.provideMoviesTvDb(Application())).deleteAll()
    }

    override fun getMovies(): LiveData<List<MovieItem?>?>? {
     return   RoomDbModule.provideMoviesTvDao(RoomDbModule.provideMoviesTvDb(Application())).getMovies()
    }

    override fun getPopularMovie(api_key: String, page: Int): Observable<MainResponse> {
       return RetrofitModule.provideMoviesTvApi(RetrofitModule.provideRetrofit()).getPopularMovie(api_key, page)
    }

    override fun getNowPlayingMovie(
        api_key: String,
        page: Int
    ): Observable<MainResponse> {
        return RetrofitModule.provideMoviesTvApi(RetrofitModule.provideRetrofit()).getNowPlayingMovie(api_key, page)
    }

    override fun getTopRatedMovie(api_key: String, page: Int): Observable<MainResponse> {
       return RetrofitModule.provideMoviesTvApi(RetrofitModule.provideRetrofit()).getTopRatedMovie(api_key, page)
    }

    override fun getMovieDetails(id: Long, api_key: String): Single<MovieDetails> {
       return RetrofitModule.provideMoviesTvApi(RetrofitModule.provideRetrofit()).getMovieDetails(id, api_key)
    }

    override fun getVideos(id: Long, api_key: String): Flowable<VideoResponse> {
      return RetrofitModule.provideMoviesTvApi(RetrofitModule.provideRetrofit()).getVideos(id, api_key)
    }
}