package io.github.losthikking.themoviedb.android.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.losthikking.themoviedb.android.paging.MovieSource
import io.github.losthikking.themoviedb.api.Service
import io.github.losthikking.themoviedb.api.dto.ContentItem
import io.github.losthikking.themoviedb.api.dto.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TMDBRepository @Inject constructor(
    private val service: Service
) {

    suspend fun getMovieDetail(movieId: Int): ContentItem {
        return service.getMovieDetails(movieId)
    }

    suspend fun getTvShowDetail(tvShowId: Int): ContentItem {
        return service.getTvShowDetails(tvShowId)
    }

    fun getPopularMoviesStream(): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize = 20)
        ) {
            MovieSource(service)
        }.flow
    }
}
