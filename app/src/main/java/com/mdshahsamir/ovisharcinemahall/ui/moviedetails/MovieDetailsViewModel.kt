package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.Movie
import com.mdshahsamir.ovisharcinemahall.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MovieDetailsViewModel(
    movieId: Int,
    var isAddedToWishlist: Boolean,
    repo: MovieDetailsRepository
) : BaseViewModel() {

    val movieDetailsFlow: Flow<MovieDetailsUiState> =
        repo.fetchMovieDetails(movieId).map {
            MovieDetailsUiState.Success(it.first, it.second)
        }.catch { e ->
            showMessage.value = "Something went wrong"
        }.stateIn(
            initialValue = MovieDetailsUiState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )
}

sealed class MovieDetailsUiState {

    object Loading : MovieDetailsUiState()
    data class Success(val movieDetails: MovieDetails, val recommendedMovies: List<Movie>) :
        MovieDetailsUiState()
}
