package com.example.moviestv.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviestv.domain.repository.MoviesTvRepository
import com.example.moviestv.presentation.main.MainViewModel

class VideoDetailsViewModelFactory (private val repo: MoviesTvRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoDetailsViewModel(repo) as T
    }
}