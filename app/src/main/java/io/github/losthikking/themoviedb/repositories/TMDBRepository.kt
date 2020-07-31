package io.github.losthikking.themoviedb.repositories

import io.github.losthikking.themoviedb.api.tmdb.TMDBService
import io.github.losthikking.themoviedb.api.tmdb.dto.ContentItem
import io.github.losthikking.themoviedb.api.tmdb.dto.Genre
import io.github.losthikking.themoviedb.api.tmdb.dto.Page
import io.github.losthikking.themoviedb.api.tmdb.dto.movie.Movie
import io.github.losthikking.themoviedb.api.tmdb.dto.tvshow.TVShow
import io.github.losthikking.themoviedb.room.GenreDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
@FlowPreview
class TMDBRepository constructor(
    private val service: TMDBService,
    private val genresDao: GenreDao
) {
    private val contentMoviePageResult = ConflatedBroadcastChannel<Page<Movie>>()
    private val contentTvShowPageResult = ConflatedBroadcastChannel<Page<TVShow>>()
    private val contentDetailResult = ConflatedBroadcastChannel<ContentItem>()
    private val genresResult = ConflatedBroadcastChannel<List<Genre>>()

    suspend fun getGenres(): Flow<List<Genre>> {
        val genresFromDB = genresDao.getAll()
        if (genresFromDB.isEmpty()) {
            requestMovieGenres()
            requestTvGenres()
//            genresResult.openSubscription().onReceive.
//            genresDao.insertAll()
        }
        return genresResult.asFlow()
    }

    private suspend fun requestTvGenres() {
        genresResult.offer(service.getTvGenres())
    }

    private suspend fun requestMovieGenres() {
        genresResult.offer(service.getMovieGenres())
    }

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