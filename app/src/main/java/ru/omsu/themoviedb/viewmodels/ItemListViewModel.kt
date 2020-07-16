package ru.omsu.themoviedb.viewmodels

import androidx.lifecycle.*
import ru.omsu.themoviedb.api.tmdb.dto.ContentItem
import ru.omsu.themoviedb.api.tmdb.dto.Page
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.repositories.TMDBRepository

class ItemListViewModel internal constructor(
        private val repository: TMDBRepository,
        private val itemType: ItemType
) : ViewModel() {
    private val queryLiveData = MutableLiveData<Int>()

    val repoResult: LiveData<Page<ContentItem>> = queryLiveData.switchMap { page ->
        liveData {
            val repos = when (itemType) {
                ItemType.MOVIE -> {
                    repository.getMoviePageStream(page).asLiveData()
                }
                ItemType.TVSHOW -> {
                    repository.getTVShowPageStream(page).asLiveData()
                }
                else -> throw IllegalArgumentException()
            }
            emitSource(repos)
        }
    }

    fun getContentPage(id: Int) {
        queryLiveData.postValue(id)
    }
}