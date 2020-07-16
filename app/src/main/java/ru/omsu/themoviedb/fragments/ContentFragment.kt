package ru.omsu.themoviedb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.omsu.themoviedb.adapters.ContentAdapter
import ru.omsu.themoviedb.databinding.FragmentContentBinding
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.viewmodels.ItemListViewModel


class ContentFragment(private val itemType: ItemType) : Fragment() {
    val viewModel: ItemListViewModel by viewModel { parametersOf(itemType) }
    private var currentPage = 1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContentBinding.inflate(inflater, container, false)
        val adapter = ContentAdapter()
        binding.itemsList.adapter = adapter
        subscribeUi(adapter)
        binding.itemsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.requestNextPage()
                }
            }
        })
        return binding.root
    }


    private fun subscribeUi(adapter: ContentAdapter) {
        viewModel.requestNextPage()
        viewModel.repoResult.observe(viewLifecycleOwner) { result ->
            adapter.submitList(adapter.currentList + result.results)
        }
    }

    private fun ItemListViewModel.requestNextPage() {
        getContentPage(currentPage).also { currentPage += 1 }
    }
}