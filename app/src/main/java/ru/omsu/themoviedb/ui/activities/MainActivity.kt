package ru.omsu.themoviedb.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.omsu.themoviedb.BuildConfig
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.ui.fragments.*
import java.lang.IllegalArgumentException


class MainActivity : AppCompatActivity() {
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment: Fragment = when (item.itemId) {
            R.id.nav_movies -> MoviesFragment()
            R.id.nav_rec -> RecommendationsFragment()
            R.id.nav_tv -> TVFragment()
            else -> throw IllegalArgumentException()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_logo_tmdb)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MoviesFragment()).commit()
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
    }
    
    
}