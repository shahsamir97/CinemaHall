package com.mdshahsamir.ovisharcinemahall.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mdshahsamir.ovisharcinemahall.database.MovieDao
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.ui.dashboard.home.MovieListPagingSource
import kotlinx.coroutines.flow.Flow

interface SharedRepository {

    fun fetchWishListFromDB(): LiveData<List<Movie>>

    fun fetchTopRatedMovies(): Flow<PagingData<Movie>>

    fun insertMovieToDB(movie: Movie)

    fun deleteMovieFromDB(movie: Movie)
}

class SharedRepositoryIml(
    private val movieAPIService: MovieAPIService,
    private val movieDao: MovieDao
) : SharedRepository {

    override fun fetchTopRatedMovies(): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = MovieListPagingSource.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieListPagingSource(movieAPIService) }
        ).flow
    }

    override fun fetchWishListFromDB(): LiveData<List<Movie>> = movieDao.getAllMovie()

    override fun insertMovieToDB(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    override fun deleteMovieFromDB(movie: Movie) {
        movieDao.deleteMovie(movie)
    }
}