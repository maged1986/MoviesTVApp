package com.example.moviestv.domain.mappers

import com.example.moviestv.data.dtos.ResultXX
import com.example.moviestv.domain.models.MovieRow


    fun ResultXX.toMovieRow(result: ResultXX): MovieRow {
       return MovieRow(movieName = result.title
            , moviePosterUrl = result.poster_path
            , id = result.id, release_date = result.release_date, overview = result.overview)
    }
