package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel
import com.mdshahsamir.ovisharcinemahall.model.Movie
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieListViewModel(private val repository: MovieListRepository) : BaseViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    fun loadData() {
        viewModelScope.launch {
            try {
                val movies = repository.fetchTopRatedMovies()
                _movieList.value = movies
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}