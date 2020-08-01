package io.github.losthikking.themoviedb.repositories

import io.github.losthikking.themoviedb.api.tmdb.TMDBService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
@FlowPreview
class TMDBRepository(
        private val service: TMDBService
) {
    private val contentDetailResult = ConflatedBroadcastChannel<io.github.losthikking.themoviedb.api.tmdb.dto.ContentItem>()

    private suspend fun requestMovie(id: Int) {
        contentDetailResult.offer(service.getMovieDetails(id))
    }

    private suspend fun requestTvShow(tvshowId: Int) {
        contentDetailResult.offer(service.getTVshowDetails(tvshowId))
    }

    suspend fun getMovieDetail(movieId: Int): Flow<io.github.losthikking.themoviedb.api.tmdb.dto.ContentItem> {
        requestMovie(movieId)
        return contentDetailResult.asFlow()
    }

    suspend fun getTVShowDetail(tvshowId: Int): Flow<io.github.losthikking.themoviedb.api.tmdb.dto.ContentItem> {
        requestTvShow(tvshowId)
        return contentDetailResult.asFlow()
    }
}