package io.github.losthikking.themoviedb.android.repositories


import io.github.losthikking.themoviedb.api.Service
import io.github.losthikking.themoviedb.api.dto.ContentItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
@FlowPreview
class TMDBRepository(
        private val service: Service
) {
    private val contentDetailResult = ConflatedBroadcastChannel<ContentItem>()

    private suspend fun requestMovie(id: Int) {
        contentDetailResult.offer(service.getMovieDetails(id))
    }

    private suspend fun requestTvShow(tvShowId: Int) {
        contentDetailResult.offer(service.getTvShowDetails(tvShowId))
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ContentItem> {
        requestMovie(movieId)
        return contentDetailResult.asFlow()
    }

    suspend fun getTVShowDetail(tvshowId: Int): Flow<ContentItem> {
        requestTvShow(tvshowId)
        return contentDetailResult.asFlow()
    }
}