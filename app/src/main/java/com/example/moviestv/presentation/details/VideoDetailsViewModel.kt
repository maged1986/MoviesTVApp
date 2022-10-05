package com.example.moviestv.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviestv.data.dtos.ResultXX
import com.example.moviestv.data.dtos.VideoResponse
import com.example.moviestv.di.RetrofitModule
import com.example.moviestv.domain.repository.MoviesTvRepository
import com.example.moviestv.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(val repository: MoviesTvRepository):ViewModel() {

    private val compositeDisposable= CompositeDisposable()
    private val TAG = "VideoDetailsViewModel"
    private val _TrailMovieURL= MutableLiveData<String>()
    val TrailMovieURL: LiveData<String>
        get() = _TrailMovieURL

     fun getVideos(id: Long) {
         compositeDisposable.add( repository.getVideos(id,Constants.API_KEY,)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             . subscribe({
                 _TrailMovieURL.value=it?.results?.get(0)?.key
                 Log.d(TAG, "getTopRatedMovie:${it.toString()}" )

             },{
                 Log.d(TAG, "getTopRatedMovie:${it.message}" )
             })
         )
    }
}