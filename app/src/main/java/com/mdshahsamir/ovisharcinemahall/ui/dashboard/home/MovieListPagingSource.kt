package com.mdshahsamir.ovisharcinemahall.ui.dashboard.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.network.MovieAPIService
import retrofit2.HttpException

class MovieListPagingSource(private val movieAPIService: MovieAPIService) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: STARTING_INDEX
            val response = movieAPIService.fetchTopRatedMovies(page = position)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (response.results.isEmpty()) null else position + 1
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_INDEX = 1
        const val PAGE_SIZE = 10
    }
}
