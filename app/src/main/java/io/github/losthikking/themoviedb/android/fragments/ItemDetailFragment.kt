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
//            fab.setOnClickListener {
//                val contentItem = itemDetailViewModel.contentItem.value
//                val type = when (contentItem) {
//                    is Movie -> "movie"
//                    is TVShow -> "tv"
//                    else -> IllegalArgumentException("Wrong type")
//                }
//                val id = contentItem?.id
//                val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
//                    .setText(getString(R.string.tmdb_url_to_item, type, id))
//                    .setType("text/plain")
//                    .createChooserIntent()
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
//                startActivity(shareIntent)
//            }
        }
        return binding.root
    }
}
