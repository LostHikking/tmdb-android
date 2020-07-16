package ru.omsu.themoviedb.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.fragments.ContentFragment

const val MOVIE_PAGE_INDEX = 0
const val TVSHOWS_PAGE_INDEX = 1

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
            MOVIE_PAGE_INDEX to { ContentFragment(ItemType.MOVIE) },
            TVSHOWS_PAGE_INDEX to { ContentFragment(ItemType.TVSHOW) }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}