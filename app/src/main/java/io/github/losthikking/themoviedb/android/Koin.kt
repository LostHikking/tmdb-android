package io.github.losthikking.themoviedb.android

import io.github.losthikking.themoviedb.android.repositories.TMDBRepository
import io.github.losthikking.themoviedb.android.viewmodels.ItemDetailViewModel
import io.github.losthikking.themoviedb.android.viewmodels.MovieViewModel
import io.github.losthikking.themoviedb.api.Service
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val tmdbModule = module {
    viewModel { MovieViewModel(get()) }
//    viewModel { SearchViewModel(get(), get()) }
    viewModel { params -> ItemDetailViewModel(get(), params[0], params[1]) }
    single { TMDBRepository(get()) }
    single { Service(API_KEY, Locale.getDefault().toLanguageTag(), Locale.getDefault().country) }
}
