package io.github.losthikking.themoviedb.viewmodels

import androidx.lifecycle.*
import io.github.losthikking.themoviedb.api.tmdb.dto.Page
import io.github.losthikking.themoviedb.api.tmdb.dto.movie.Movie
import io.github.losthikking.themoviedb.api.tmdb.dto.tvshow.TVShow
import io.github.losthikking.themoviedb.repositories.TMDBRepository

class ItemListViewModel internal constructor(
    private val repository: TMDBRepository
) : ViewModel() {
    private val queryMoviePageLiveData = MutableLiveData<Int>()
    private val queryTvShowPageLiveData = MutableLiveData<Int>()

    val moviePageResult: LiveData<Page<Movie>> = queryMoviePageLiveData.switchMap { page ->
        liveData {
            val repos = repository.getMoviePageStream(page).asLiveData()
            emitSource(repos)
        }
    }

    val tvShowPageResult: LiveData<Page<TVShow>> = queryTvShowPageLiveData.switchMap { page ->
        liveData {
            val repos = repository.getTVShowPageStream(page).asLiveData()
            emitSource(repos)
        }
    }

    fun getMoviePage(id: Int) {
        queryMoviePageLiveData.postValue(id)
    }

    fun getTvShowPage(id: Int) {
        queryTvShowPageLiveData.postValue(id)
    }
}