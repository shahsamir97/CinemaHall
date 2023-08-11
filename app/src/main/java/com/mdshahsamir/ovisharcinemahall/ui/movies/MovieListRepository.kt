package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    fun fetchTopRatedMovies():  Flow<PagingData<Movie>>
}

class MovieListRepositoryImpl(private val movieAPIService: MovieAPIService): MovieListRepository {

    override fun fetchTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieListPagingSource.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieListPagingSource(movieAPIService) }
        ).flow
    }
}
