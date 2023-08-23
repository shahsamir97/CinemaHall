package com.mdshahsamir.ovisharcinemahall.model

import com.google.gson.annotations.SerializedName

data class ListOfMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
