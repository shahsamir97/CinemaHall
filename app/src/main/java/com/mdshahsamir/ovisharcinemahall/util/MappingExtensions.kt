package com.mdshahsamir.ovisharcinemahall.util

import com.mdshahsamir.ovisharcinemahall.database.MovieDBEntity
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO

fun Movie.toMovieDTO() : MovieDTO {
    return MovieDTO(
        id = this.id,
        title = this.title,
        adult = this.adult,
        overview = this.overview,
        originalTitle = this.originalTitle,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}

fun MovieDTO.toMovieDBEntity(): MovieDBEntity {
    return MovieDBEntity(
        id = this.id,
        title = this.title,
        adult = this.adult,
        overview = this.overview,
        originalTitle = this.originalTitle,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}

fun MovieDBEntity.toMovieDTO(): MovieDTO {
    return MovieDTO(
        id = this.id,
        title = this.title,
        adult = this.adult,
        overview = this.overview,
        originalTitle = this.originalTitle,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate
    )
}