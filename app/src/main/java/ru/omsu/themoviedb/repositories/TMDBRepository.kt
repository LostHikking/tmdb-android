package ru.omsu.themoviedb.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.omsu.themoviedb.api.tmdb.TMDBService
import ru.omsu.themoviedb.api.tmdb.dto.ContentItem
import ru.omsu.themoviedb.api.tmdb.dto.Page
import ru.omsu.themoviedb.api.tmdb.dto.movie.Movie
import ru.omsu.themoviedb.api.tmdb.dto.tvshow.TVShow

@ExperimentalCoroutinesApi
@FlowPreview
class TMDBRepository constructor(private val service: TMDBService) {


    private val contentMoviePageResult = ConflatedBroadcastChannel<Page<Movie>>()
    private val contentTvShowPageResult = ConflatedBroadcastChannel<Page<TVShow>>()
    private val contentDetailResult = ConflatedBroadcastChannel<ContentItem>()

    suspend fun getMoviePageStream(page: Int): Flow<Page<Movie>> {
        requestMoviePage(page)
        return contentMoviePageResult.asFlow()
    }

    suspend fun getTVShowPageStream(page: Int): Flow<Page<TVShow>> {
        requestTVShowPage(page)
        return contentTvShowPageResult.asFlow()
    }

    private suspend fun requestMoviePage(page: Int) {
        contentMoviePageResult.offer(service.getPopularMovies(page = page))
    }

    private suspend fun requestTVShowPage(page: Int) {
        contentTvShowPageResult.offer(service.getPopularTV(page = page))
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