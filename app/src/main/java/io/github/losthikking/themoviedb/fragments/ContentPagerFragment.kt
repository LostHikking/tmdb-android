package io.github.losthikking.themoviedb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import io.github.losthikking.themoviedb.R
import io.github.losthikking.themoviedb.adapters.MOVIE_PAGE_INDEX
import io.github.losthikking.themoviedb.adapters.PagerAdapter
import io.github.losthikking.themoviedb.adapters.TVSHOWS_PAGE_INDEX
import io.github.losthikking.themoviedb.databinding.FragmentListBinding

class ContentPagerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding
                .inflate(inflater, container, false)
        val tabLayout = binding.mainTabs
        val viewPager = binding.viewPager

        viewPager.adapter = PagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MOVIE_PAGE_INDEX -> R.drawable.ic_movie
            TVSHOWS_PAGE_INDEX -> R.drawable.ic_tvshow
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MOVIE_PAGE_INDEX -> getString(R.string.movies)
            TVSHOWS_PAGE_INDEX -> getString(R.string.tv_shows)
            else -> null
        }
    }
}