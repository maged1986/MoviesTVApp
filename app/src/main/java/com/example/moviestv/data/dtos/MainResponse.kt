package com.example.moviestv.data.dtos

import com.squareup.moshi.Json

data class MainResponse(
    @field:Json(name = "page")  val page: Int,
    @field:Json(name = "results")val results: List<ResultXX>,
    @field:Json(name = "total_pages")val total_pages: Int,
    @field:Json(name = "total_results")val total_results: Int
)