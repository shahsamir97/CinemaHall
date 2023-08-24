package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MovieDetailsViewModel(movieId: Int, repo: MovieDetailsRepository) :
    BaseViewModel() {

    val movieDetailsFlow: Flow<MovieDetailsUiState> =
        repo.fetchMovieDetails(movieId).map {
            MovieDetailsUiState.Success(it.first, it.second)
        }.catch {
            MovieDetailsUiState.Error(it)
        }.stateIn(
            initialValue = MovieDetailsUiState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )
    }

sealed class MovieDetailsUiState {

    object Loading : MovieDetailsUiState()
    data class Success(val movieDetails: MovieDetailsResponse, val recommendedMovies: List<Movie>) : MovieDetailsUiState()
    data class Error(val exception: Throwable) : MovieDetailsUiState()
}