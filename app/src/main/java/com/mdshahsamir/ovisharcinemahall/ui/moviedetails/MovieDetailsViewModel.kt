package com.mdshahsamir.ovisharcinemahall.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.MovieDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieDetailsViewModel(private val repo: MovieDetailsRepository) : BaseViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState>
        get() = _uiState

    fun loadMovieDetails(movieId: Int) {
        _uiState.value = MovieDetailsUiState.Loading
        viewModelScope.launch {

            try {
                _uiState.value = MovieDetailsUiState.Success(repo.fetchMovieDetails(movieId))
            } catch (e: HttpException) {
                _uiState.value = MovieDetailsUiState.Error(e)
            }
        }
    }
}

sealed class MovieDetailsUiState {

     object Loading: MovieDetailsUiState()
    data class Success(val movieDetails: MovieDetailsResponse?): MovieDetailsUiState()
    data class Error(val exception: Throwable): MovieDetailsUiState()
}
