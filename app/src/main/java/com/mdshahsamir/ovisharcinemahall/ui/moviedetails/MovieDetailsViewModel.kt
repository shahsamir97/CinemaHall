package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repo: MovieDetailsRepository) : BaseViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState>
        get() = _uiState

    fun loadMovieDetails(movieId: Int) {
        _uiState.value = MovieDetailsUiState.Loading
        viewModelScope.launch {
            val movieDetailsResult = repo.fetchMovieDetails(movieId)

            if (movieDetailsResult.isSuccess) {
                _uiState.value = MovieDetailsUiState.Success(movieDetailsResult.getOrNull())
            } else {
                _uiState.value =
                    MovieDetailsUiState.Error(movieDetailsResult.exceptionOrNull() ?: Exception())
            }
        }
    }
}

sealed class MovieDetailsUiState {

    object Loading : MovieDetailsUiState()
    data class Success(val movieDetails: MovieDetailsResponse?) : MovieDetailsUiState()
    data class Error(val exception: Throwable) : MovieDetailsUiState()
}
