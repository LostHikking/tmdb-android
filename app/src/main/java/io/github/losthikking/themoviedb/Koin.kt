package io.github.losthikking.themoviedb

import androidx.room.Room
import io.github.losthikking.themoviedb.api.tmdb.TMDBService
import io.github.losthikking.themoviedb.repositories.TMDBRepository
import io.github.losthikking.themoviedb.room.TMDBDatabase
import io.github.losthikking.themoviedb.viewmodels.ItemDetailViewModel
import io.github.losthikking.themoviedb.viewmodels.ItemListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tmdbModule = module {
    viewModel { ItemListViewModel(get()) }
    viewModel { params -> ItemDetailViewModel(get(), params[0], params[1]) }
    single { get<TMDBDatabase>().genreDao() }
    single { TMDBRepository(get(), get()) }
    single { TMDBService.create() }
    single { Room.databaseBuilder(get(), TMDBDatabase::class.java, "tmdb_database").build() }
}
