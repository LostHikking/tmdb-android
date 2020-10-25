package io.github.losthikking.themoviedb.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.github.losthikking.themoviedb.android.adapters.MovieAdapter
import io.github.losthikking.themoviedb.android.adapters.MovieComparator
import io.github.losthikking.themoviedb.android.viewmodels.MovieViewModel
import io.github.losthikking.themoviedb.databinding.FragmentContentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviePageFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModels()
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