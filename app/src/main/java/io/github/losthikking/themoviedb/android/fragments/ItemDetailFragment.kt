package io.github.losthikking.themoviedb.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.losthikking.themoviedb.R
import io.github.losthikking.themoviedb.android.viewmodels.ItemDetailViewModel
import io.github.losthikking.themoviedb.databinding.FragmentDetailItemBinding

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private val itemDetailViewModel: ItemDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailItemBinding>(
            inflater, R.layout.fragment_detail_item, container, false
        ).apply {
            viewModel = itemDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}
