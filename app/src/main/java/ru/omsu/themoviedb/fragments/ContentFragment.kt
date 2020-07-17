package ru.omsu.themoviedb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.viewmodel.ext.android.viewModel
import ru.omsu.themoviedb.adapters.ContentAdapter
import ru.omsu.themoviedb.databinding.FragmentContentBinding
import ru.omsu.themoviedb.viewmodels.ItemListViewModel


abstract class ContentFragment : Fragment() {
    val viewModel: ItemListViewModel by viewModel()
    protected var currentPage = 1

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


    protected abstract fun subscribeUi(adapter: ContentAdapter)

    protected abstract fun ItemListViewModel.requestNextPage()
}