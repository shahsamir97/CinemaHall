package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    fun fetchTopRatedMovies(): Flow<PagingData<Movie>>

    fun insertMovieToDB(movie: Movie)

    fun deleteMovieFromDB(movie: Movie)
}

class MovieListRepositoryImpl(
    private val movieAPIService: MovieAPIService,
    private val movieDao: MovieDao
) : MovieListRepository {

    override fun fetchTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieListPagingSource.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieListPagingSource(movieAPIService) }
        ).flow
    }

    override fun insertMovieToDB(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    override fun deleteMovieFromDB(movie: Movie) {
        movieDao.deleteMovie(movie)
    }
}
