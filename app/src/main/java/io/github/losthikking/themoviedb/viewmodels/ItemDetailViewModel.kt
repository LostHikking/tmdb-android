package io.github.losthikking.themoviedb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import io.github.losthikking.themoviedb.api.tmdb.dto.ContentItem
import io.github.losthikking.themoviedb.enums.ItemType
import io.github.losthikking.themoviedb.repositories.TMDBRepository

class ItemDetailViewModel constructor(
        private val repository: TMDBRepository,
        private val itemId: Int,
        private val itemType: ItemType
) : ViewModel() {

    val contentItem: LiveData<ContentItem> =
            liveData {
                val repos = when (itemType) {
                    ItemType.MOVIE -> repository.getMovieDetail(itemId).asLiveData()
                    ItemType.TVSHOW -> repository.getTVShowDetail(itemId).asLiveData()
                    else -> throw IllegalArgumentException("Can be MOVIE or TVSHOW")
                }
                emitSource(repos)
            }

}