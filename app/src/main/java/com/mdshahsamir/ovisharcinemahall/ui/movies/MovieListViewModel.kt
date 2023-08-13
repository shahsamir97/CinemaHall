package com.mdshahsamir.ovisharcinemahall.ui.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mdshahsamir.ovisharcinemahall.base.BaseViewModel

class MovieListViewModel(repository: MovieListRepository) : BaseViewModel() {

    val movieList =  repository.fetchTopRatedMovies().cachedIn(viewModelScope)
}