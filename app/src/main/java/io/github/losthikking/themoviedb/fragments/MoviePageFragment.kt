package io.github.losthikking.themoviedb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.github.losthikking.themoviedb.adapters.MovieAdapter
import io.github.losthikking.themoviedb.adapters.MovieComparator
import io.github.losthikking.themoviedb.databinding.FragmentContentBinding
import io.github.losthikking.themoviedb.viewmodels.MovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MoviePageFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModel()
    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        val adapter = MovieAdapter(MovieComparator)
        binding.itemsList.adapter = adapter
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
        return binding.root
    }
}