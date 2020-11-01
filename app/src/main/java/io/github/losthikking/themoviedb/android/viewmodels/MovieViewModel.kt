package io.github.losthikking.themoviedb.android.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import io.github.losthikking.themoviedb.android.repositories.TMDBRepository

class MovieViewModel @ViewModelInject constructor(
    repository: TMDBRepository,
) : ViewModel() {
    val flow = repository
        .getPopularMoviesStream()
        .cachedIn(viewModelScope)
}
