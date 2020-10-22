package io.github.losthikking.themoviedb.android

import androidx.room.Room
import io.github.losthikking.themoviedb.android.api.tmdb.TMDBService
import io.github.losthikking.themoviedb.android.repositories.TMDBRepository
import io.github.losthikking.themoviedb.android.room.TMDBDatabase
import io.github.losthikking.themoviedb.android.viewmodels.ItemDetailViewModel
import io.github.losthikking.themoviedb.android.viewmodels.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tmdbModule = module {
    viewModel { MovieViewModel(get()) }
//    viewModel { SearchViewModel(get(), get()) }
    viewModel { params -> ItemDetailViewModel(get(), params[0], params[1]) }
    single { get<TMDBDatabase>().searchQueryDao() }
    single { TMDBRepository(get()) }
    single { TMDBService.create() }
    single { Room.databaseBuilder(get(), TMDBDatabase::class.java, "tmdb_database").build() }
}
