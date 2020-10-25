package io.github.losthikking.themoviedb.android.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import io.github.losthikking.themoviedb.android.enums.ItemType
import io.github.losthikking.themoviedb.android.repositories.TMDBRepository
import io.github.losthikking.themoviedb.api.dto.ContentItem

open class ItemDetailViewModel @ViewModelInject constructor(
        private val repository: TMDBRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemType = savedStateHandle.getLiveData<ItemType>("itemType").value!!
    private val itemId = savedStateHandle.getLiveData<Int>("itemId").value!!

    val contentItem: LiveData<ContentItem> =
            liveData {
                emitSource(when (itemType) {
                    ItemType.MOVIE -> repository.getMovieDetail(itemId).asLiveData()
                    ItemType.TVSHOW -> repository.getTVShowDetail(itemId).asLiveData()
                    else -> throw IllegalArgumentException("Can be MOVIE or TVSHOW")
                })
            }

}