package com.example.moviestv.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviestv.data.dtos.Movie
import com.example.moviestv.data.dtos.ResultXX
import com.example.moviestv.domain.repository.MoviesTvRepository
import com.example.moviestv.utils.Constants.API_KEY
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

@HiltAndroidApp
class MainViewModel @Inject constructor(
    private val repository: MoviesTvRepository
):ViewModel() {
    private  val TAG = "MainViewModel"
    private val compositeDisposable= CompositeDisposable()

    private val _TopRatingMovies= MutableLiveData<List<ResultXX>>()
    val TopRatingMovies: LiveData<List<ResultXX>>
        get() = _TopRatingMovies

    private val _NowPlayingMovies= MutableLiveData<List<ResultXX>>()
    val NowPlayingMovies: LiveData<List<ResultXX>>
        get() = _NowPlayingMovies

    private val _PopularMovies= MutableLiveData<List<ResultXX>>()
    val PopularMovies: LiveData<List<ResultXX>>
        get() = _PopularMovies
    init{
       getPopularMovie()
       getNowPlayingMovie()
        getTopRatedMovie()
    }
     fun getPopularMovie() {
        compositeDisposable.add( repository.getPopularMovie(API_KEY, 1)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            . subscribe({
                _PopularMovies.value=it.results
                Log.d(TAG, "getPopularMovie: ")
            },{
                Log.d(TAG, "getTopRatedMovie:${it.message}" )
            })
        )
    }
     fun getNowPlayingMovie(){
        compositeDisposable.add(repository.getNowPlayingMovie(API_KEY, 1)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            . subscribe({
            _NowPlayingMovies.value=it.results
        },{
            Log.d(TAG, "getTopRatedMovie:${it.message}" )
        }))
    }

     fun getTopRatedMovie() {
        compositeDisposable.add( repository.getTopRatedMovie(API_KEY, 1)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            . subscribe({
                Log.d(TAG, "getTopRatedMoviegff: ${it}")
               _TopRatingMovies.value=it.results
            },{
                Log.d(TAG, "getTopRatedMovie:${it.message}" )
            })
        )
    }

}