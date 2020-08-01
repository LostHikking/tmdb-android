package io.github.losthikking.themoviedb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.losthikking.themoviedb.api.tmdb.TMDBService
import io.github.losthikking.themoviedb.paging.MovieSource
import io.github.losthikking.themoviedb.room.TMDBDatabase
import org.koin.java.KoinJavaComponent.inject

class MovieViewModel internal constructor(
        private val service: TMDBService
) : ViewModel() {
    private val db by inject(TMDBDatabase::class.java)
    val flow = Pager(
            PagingConfig(pageSize = 20)
    ) {
        MovieSource(service, db)
    }.flow
            .cachedIn(viewModelScope)
}