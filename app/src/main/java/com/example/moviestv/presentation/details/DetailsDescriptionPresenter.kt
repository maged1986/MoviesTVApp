package com.example.moviestv.presentation.details

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.example.moviestv.domain.models.MovieRow

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: AbstractDetailsDescriptionPresenter.ViewHolder,
        item: Any
    ) {
        val movie = item as MovieRow

        viewHolder.title.text = movie.movieName
        viewHolder.subtitle.text = movie.release_date
        viewHolder.body.text = movie.overview
    }
}