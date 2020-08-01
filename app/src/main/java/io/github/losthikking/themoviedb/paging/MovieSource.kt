package io.github.losthikking.themoviedb.paging

import androidx.paging.PagingSource
import io.github.losthikking.themoviedb.api.tmdb.TMDBService
import io.github.losthikking.themoviedb.api.tmdb.dto.movie.Movie
import io.github.losthikking.themoviedb.room.TMDBDatabase

class MovieSource(
        private val tmdbService: TMDBService,
        private val tmdbDatabase: TMDBDatabase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val genreDao = tmdbDatabase.genreDao()
            if (genreDao.getAll().isEmpty()) {
                val genres = tmdbService.getMovieGenres()
                genreDao.insertAll(genres)
            }
            val response = tmdbService.getPopularMovies(page = nextPageNumber)
            val data = response.results
                    .map {
                        it.copy(genres = genreDao
                                .loadAllByIds(it.genresIds.toIntArray()))
                    }
            val nextPage = if (response.page + 1 < response.totalPages) {
                response.page + 1
            } else {
                null
            }
            LoadResult.Page(data, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}