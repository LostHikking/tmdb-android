package ru.omsu.themoviedb

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.omsu.themoviedb.api.tmdb.TMDBService
import ru.omsu.themoviedb.repositories.TMDBRepository
import ru.omsu.themoviedb.viewmodels.ItemDetailViewModel
import ru.omsu.themoviedb.viewmodels.ItemListViewModel

val tmdbModule = module {
    viewModel { ItemListViewModel(get()) }
    viewModel { params -> ItemDetailViewModel(get(), params[0], params[1]) }
    single { TMDBRepository(get()) }
    single { TMDBService.create() }
}
