package com.mdshahsamir.ovisharcinemahall.util

import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO
import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDetailsDTO
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetails

fun MovieDTO.toMovie() : Movie {
    return Movie(
        id = this.id ?: 1,
        title = this.title ?: "",
        adult = this.adult ?: false,
        overview = this.overview ?: "",
        originalTitle = this.originalTitle ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate ?: ""
    )
}

fun MovieDetailsDTO.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = this.id ?: 1,
        originalTitle = this.originalTitle ?: "",
        releaseDate = this.releaseDate ?: "",
        adult = this.adult ?: false,
        backdropPath = this.backdropPath ?: "",
        genres = this.genres ?: listOf(),
        overview = this.overview ?: "",
        posterPath = this.posterPath ?: "",
        voteAverage = this.voteAverage ?: 0.0
    )
}

