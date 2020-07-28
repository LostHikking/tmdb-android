package io.github.losthikking.themoviedb.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.losthikking.themoviedb.fragments.MoviePageFragment
import io.github.losthikking.themoviedb.fragments.TvShowFragment

const val MOVIE_PAGE_INDEX = 0
const val TVSHOWS_PAGE_INDEX = 1

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
            MOVIE_PAGE_INDEX to { MoviePageFragment() },
            TVSHOWS_PAGE_INDEX to { TvShowFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}