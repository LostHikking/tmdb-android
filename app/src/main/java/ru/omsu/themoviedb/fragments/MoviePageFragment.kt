package ru.omsu.themoviedb.fragments

import androidx.lifecycle.observe
import ru.omsu.themoviedb.adapters.ContentAdapter
import ru.omsu.themoviedb.viewmodels.ItemListViewModel

class MoviePageFragment : ContentFragment() {

    override fun subscribeUi(adapter: ContentAdapter) {
        viewModel.requestNextPage()
        viewModel.moviePageResult.observe(viewLifecycleOwner) { result ->
            adapter.submitList(adapter.currentList + result.results)
        }
    }

    override fun ItemListViewModel.requestNextPage() {
        getMoviePage(currentPage).also { currentPage += 1 }
    }
}