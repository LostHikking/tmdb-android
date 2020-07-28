package io.github.losthikking.themoviedb

import io.github.losthikking.themoviedb.api.tmdb.TMDBService
import io.github.losthikking.themoviedb.repositories.TMDBRepository
import io.github.losthikking.themoviedb.viewmodels.ItemDetailViewModel
import io.github.losthikking.themoviedb.viewmodels.ItemListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tmdbModule = module {
    viewModel { ItemListViewModel(get()) }
    viewModel { params -> ItemDetailViewModel(get(), params[0], params[1]) }
    single { TMDBRepository(get()) }
    single { TMDBService.create() }
}
