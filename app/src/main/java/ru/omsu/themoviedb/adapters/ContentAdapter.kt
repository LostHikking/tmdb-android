package ru.omsu.themoviedb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.themoviedb.api.tmdb.dto.ContentItem
import ru.omsu.themoviedb.api.tmdb.dto.movie.Movie
import ru.omsu.themoviedb.api.tmdb.dto.tvshow.TVShow
import ru.omsu.themoviedb.databinding.CardItemBinding
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.fragments.HomeViewPagerFragmentDirections


class ContentAdapter : ListAdapter<ContentItem, RecyclerView.ViewHolder>(ContentItemDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(CardItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contentItem = getItem(position)
        (holder as ContentViewHolder).bind(contentItem)
    }


    class ContentViewHolder(
            private val binding: CardItemBinding
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
                    HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToItemDetailFragment(
                            when (contentItem) {
                                is Movie -> ItemType.MOVIE
                                is TVShow -> ItemType.TVSHOW
                                else -> throw IllegalArgumentException("Can be movie or tvshow")
                            },
                            contentItem.id
                    )
            view.findNavController().navigate(direction)
        }

        fun bind(item: ContentItem) {
            binding.apply {
                contentItem = item
                executePendingBindings()
            }
        }
    }
}

private class ContentItemDiffCallback : DiffUtil.ItemCallback<ContentItem>() {
    override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
        return oldItem.id == newItem.id
    }
}
