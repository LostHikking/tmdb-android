package ru.omsu.themoviedb.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.omsu.themoviedb.api.tmdb.TMDBService
import ru.omsu.themoviedb.api.tmdb.dto.ContentItem
import ru.omsu.themoviedb.api.tmdb.dto.Page

@ExperimentalCoroutinesApi
@FlowPreview
class TMDBRepository constructor(private val service: TMDBService) {


    private val contentListResult = ConflatedBroadcastChannel<Page<ContentItem>>()
    private val contentDetailResult = ConflatedBroadcastChannel<ContentItem>()

    suspend fun getMoviePageStream(page: Int): Flow<Page<ContentItem>> {
        requestMoviePage(page)
        return contentListResult.asFlow()
    }

    suspend fun getTVShowPageStream(page: Int): Flow<Page<ContentItem>> {
        requestTVShowPage(page)
        return contentListResult.asFlow()
    }

    private suspend fun requestMoviePage(page: Int) {
        contentListResult.offer(service.getPopularMovies(page = page) as Page<ContentItem>)
    }

    private suspend fun requestTVShowPage(page: Int) {
        contentListResult.offer(service.getPopularTV(page = page) as Page<ContentItem>)
    }

    private suspend fun requestMovie(id: Int) {
        contentDetailResult.offer(service.getMovieDetails(id))
    }

    private suspend fun requestTvShow(tvshowId: Int) {
        contentDetailResult.offer(service.getTVshowDetails(tvshowId))
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