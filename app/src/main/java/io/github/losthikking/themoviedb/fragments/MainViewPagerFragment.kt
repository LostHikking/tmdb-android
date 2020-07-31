package io.github.losthikking.themoviedb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import io.github.losthikking.themoviedb.R
import io.github.losthikking.themoviedb.databinding.FragmentMainBinding
import io.github.losthikking.themoviedb.fragments.contentpagers.MovieContentPagerFragment
import io.github.losthikking.themoviedb.fragments.contentpagers.TvShowContentPagerFragment

class MainViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val bottomNavigationView = binding.bottomNavigation
        val fragmentManager = parentFragmentManager
        val menuButton = binding.menuButton
        val loginButton = binding.loginButton
        loginButton.setOnClickListener {
            findNavController().navigate(MainViewPagerFragmentDirections.actionHomeViewPagerFragmentToLoginFragment())
        }
        menuButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movies -> {
                    fragmentManager.changeFragment(MovieContentPagerFragment())
                }
                R.id.nav_tv -> {
                    fragmentManager.changeFragment(TvShowContentPagerFragment())
                }
                else -> throw IllegalArgumentException()
            }
        }

        return binding.root
    }

    private fun FragmentManager.changeFragment(fragment: Fragment): Boolean {
        beginTransaction().replace(R.id.fragment_content_container, fragment).commit()
        return true
    }
}