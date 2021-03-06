package io.github.losthikking.themoviedb.android.paging

import androidx.paging.PagingSource
import io.github.losthikking.themoviedb.api.Service
import io.github.losthikking.themoviedb.api.dto.movie.Movie

class MovieSource(
    private val tmdbService: Service
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = tmdbService.getPopularMovies(page = nextPageNumber)
            val nextPage = if (response.page + 1 < response.totalPages) {
                response.page + 1
            } else {
                null
            }
            LoadResult.Page(response.results, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
