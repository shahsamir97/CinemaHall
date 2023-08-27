package com.mdshahsamir.ovisharcinemahall.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListPagingSource
import com.mdshahsamir.ovisharcinemahall.util.toMovieDBEntity
import com.mdshahsamir.ovisharcinemahall.util.toMovieDTO
import kotlinx.coroutines.flow.Flow

interface SharedRepository {

    fun fetchWishListFromDB(): LiveData<List<MovieDTO>>

    fun fetchTopRatedMovies(): Flow<PagingData<MovieDTO>>

    fun insertMovieToDB(movie: MovieDTO)

    fun deleteMovieFromDB(movie: MovieDTO)
}

class SharedRepositoryIml(
    private val movieAPIService: MovieAPIService,
    private val movieDao: MovieDao
) : SharedRepository {

    override fun fetchTopRatedMovies(): Flow<PagingData<MovieDTO>> {

        return Pager(
            config = PagingConfig(
                pageSize = MovieListPagingSource.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieListPagingSource(movieAPIService) }
        ).flow
    }

    override fun fetchWishListFromDB(): LiveData<List<MovieDTO>> = movieDao.getAllMovie().map { movies ->
        movies.map {
            it.toMovieDTO()
        }
    }

    override fun insertMovieToDB(movie: MovieDTO) {
        movieDao.insertMovie(movie.toMovieDBEntity())
    }

    override fun deleteMovieFromDB(movie: MovieDTO) {
        movieDao.deleteMovie(movie.toMovieDBEntity())
    }
}