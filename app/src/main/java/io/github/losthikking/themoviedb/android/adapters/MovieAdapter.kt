package io.github.losthikking.themoviedb.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import io.github.losthikking.themoviedb.databinding.CardItemMaterialBinding
import io.github.losthikking.themoviedb.android.enums.ItemType
import io.github.losthikking.themoviedb.android.fragments.MainViewPagerFragmentDirections
import io.github.losthikking.themoviedb.api.dto.ContentItem
import io.github.losthikking.themoviedb.api.dto.movie.Movie
import io.github.losthikking.themoviedb.api.dto.tvshow.TVShow


class MovieAdapter(diffCallback: DiffUtil.ItemCallback<Movie>) :
        PagingDataAdapter<Movie, RecyclerView.ViewHolder>(diffCallback) {


    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ContentViewHolder {
        return ContentViewHolder(CardItemMaterialBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contentItem = getItem(position)
        (holder as ContentViewHolder).bind(contentItem)
    }


    class ContentViewHolder(
            private val binding: CardItemMaterialBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.contentItem?.let { contentItem ->
                    navigateToContentItem(contentItem, it)
                }
            }
        }

        private fun navigateToContentItem(
                contentItem: ContentItem,
                view: View
        ) {
            val direction =
                    MainViewPagerFragmentDirections.actionHomeViewPagerFragmentToItemDetailFragment(
                            when (contentItem) {
                                is Movie -> ItemType.MOVIE
                                is TVShow -> ItemType.TVSHOW
                                else -> throw IllegalArgumentException("Can be movie or tvshow")
                            },
                            contentItem.id
                    )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Movie?) {
            binding.apply {
                contentItem = item
                executePendingBindings()
            }
        }
    }
}

object MovieComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
