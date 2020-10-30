package io.github.losthikking.themoviedb.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.losthikking.themoviedb.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    //    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        //  binding.searchBoxTextInputLayout.on
        return binding.root
    }
}
