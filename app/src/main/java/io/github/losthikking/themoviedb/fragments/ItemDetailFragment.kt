package io.github.losthikking.themoviedb.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.losthikking.themoviedb.R
import io.github.losthikking.themoviedb.api.tmdb.dto.movie.Movie
import io.github.losthikking.themoviedb.api.tmdb.dto.tvshow.TVShow
import io.github.losthikking.themoviedb.databinding.FragmentDetailItemBinding
import io.github.losthikking.themoviedb.viewmodels.ItemDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ItemDetailFragment : Fragment() {

    private val args: ItemDetailFragmentArgs by navArgs()

    private val itemDetailViewModel: ItemDetailViewModel by viewModel { parametersOf(args.itemId, args.itemType) }

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
            fab.setOnClickListener {
                val contentItem = itemDetailViewModel.contentItem.value
                val type = when (contentItem) {
                    is Movie -> "movie"
                    is TVShow -> "tv"
                    else -> IllegalArgumentException("Wrong type")
                }
                val id = contentItem?.id
                val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                        .setText(getString(R.string.tmdb_url_to_item, type, id))
                        .setType("text/plain")
                        .createChooserIntent()
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                startActivity(shareIntent)
            }
        }
        return binding.root
    }


}