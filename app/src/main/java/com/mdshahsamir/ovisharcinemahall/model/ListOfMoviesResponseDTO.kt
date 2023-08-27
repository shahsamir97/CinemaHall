package com.mdshahsamir.ovisharcinemahall.model

import com.google.gson.annotations.SerializedName
import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO

data class ListOfMoviesResponseDTO(
    val page: Int,
    val results: List<MovieDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
