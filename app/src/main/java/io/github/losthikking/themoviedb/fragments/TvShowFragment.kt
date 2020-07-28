package io.github.losthikking.themoviedb.fragments

import androidx.lifecycle.observe
import io.github.losthikking.themoviedb.adapters.ContentAdapter
import io.github.losthikking.themoviedb.viewmodels.ItemListViewModel

class TvShowFragment : ContentFragment() {
    override fun subscribeUi(adapter: ContentAdapter) {
        viewModel.requestNextPage()
        viewModel.tvShowPageResult.observe(viewLifecycleOwner) { result ->
            adapter.submitList(adapter.currentList + result.results)
        }
    }

    override fun ItemListViewModel.requestNextPage() {
        getTvShowPage(currentPage).also { currentPage += 1 }
    }

}