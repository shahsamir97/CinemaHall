package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mdshahsamir.ovisharcinemahall.model.dto.MovieDTO
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import com.mdshahsamir.ovisharcinemahall.util.toMovieDTO
import java.lang.Exception

class MovieListPagingSource(private val movieAPIService: MovieAPIService) :
    PagingSource<Int, MovieDTO>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDTO>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDTO> {
        return try {
            val position = params.key ?: STARTING_INDEX
            val response = movieAPIService.fetchTopRatedMovies(page = position)
            val movies = response.results.map { movie ->
                movie.toMovieDTO()
            }

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (response.results.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_INDEX = 1
        const val PAGE_SIZE = 10
    }
}
