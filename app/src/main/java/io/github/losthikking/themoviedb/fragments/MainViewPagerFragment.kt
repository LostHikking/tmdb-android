package io.github.losthikking.themoviedb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.github.losthikking.themoviedb.R
import io.github.losthikking.themoviedb.databinding.FragmentMainBinding

class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val bottomNavigationView = binding.bottomNavigation
        val fragmentManager = parentFragmentManager
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movies -> {
                    fragmentManager.changeFragment(ContentPagerFragment())
                }
                R.id.nav_tv -> {
                    fragmentManager.changeFragment(ContentPagerFragment())
                }
                else -> throw IllegalArgumentException()
            }
        }

        return binding.root
    }

    private fun FragmentManager.changeFragment(fragment: Fragment): Boolean {
        this.beginTransaction().replace(R.id.fragment_content_container, fragment).commit()
        return true
    }
}