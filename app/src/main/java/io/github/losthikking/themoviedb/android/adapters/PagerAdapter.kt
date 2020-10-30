package io.github.losthikking.themoviedb.android.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

const val MOVIE_PAGE_INDEX = 0
const val TVSHOWS_PAGE_INDEX = 1

class PagerAdapter(fragment: Fragment, tabs: List<() -> Fragment>) :
    FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> =
        tabs.mapIndexed { index, fragment ->
            index to fragment
        }.toMap()

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
