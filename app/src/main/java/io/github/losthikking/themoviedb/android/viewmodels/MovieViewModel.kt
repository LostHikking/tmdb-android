package io.github.losthikking.themoviedb.android.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.losthikking.themoviedb.android.paging.MovieSource
import io.github.losthikking.themoviedb.api.Service

class MovieViewModel @ViewModelInject constructor(
        private val service: Service
) : ViewModel() {
    val flow = Pager(
            PagingConfig(pageSize = 20)
    ) {
        MovieSource(service)
    }.flow
            .cachedIn(viewModelScope)
}