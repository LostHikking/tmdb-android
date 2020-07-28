package io.github.losthikking.themoviedb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.losthikking.themoviedb.api.tmdb.dto.ContentItem
import io.github.losthikking.themoviedb.api.tmdb.dto.movie.Movie
import io.github.losthikking.themoviedb.api.tmdb.dto.tvshow.TVShow
import io.github.losthikking.themoviedb.databinding.CardItemMaterialBinding
import io.github.losthikking.themoviedb.enums.ItemType
import io.github.losthikking.themoviedb.fragments.MainViewPagerFragmentDirections


class ContentAdapter :
    ListAdapter<ContentItem, RecyclerView.ViewHolder>(ContentItemDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(
            CardItemMaterialBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
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
