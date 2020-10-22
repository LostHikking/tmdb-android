package io.github.losthikking.themoviedb.android.fragments.contentpagers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import io.github.losthikking.themoviedb.android.adapters.PagerAdapter
import io.github.losthikking.themoviedb.databinding.FragmentListBinding

abstract class ContentPagerFragment : Fragment() {
    protected abstract val tabs: Map<String, () -> Fragment>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding
            .inflate(inflater, container, false)
        val tabLayout = binding.mainTabs
        val viewPager = binding.viewPager

        viewPager.adapter = PagerAdapter(this, tabs.values.toList())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return tabs.keys.toList()[position]
    }
}
