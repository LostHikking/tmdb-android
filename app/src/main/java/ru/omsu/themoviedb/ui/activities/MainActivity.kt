package ru.omsu.themoviedb.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.ui.fragments.MoviesFragment
import ru.omsu.themoviedb.ui.fragments.PersonsFragment
import ru.omsu.themoviedb.ui.fragments.SearchFragment
import ru.omsu.themoviedb.ui.fragments.TVFragment


class MainActivity : AppCompatActivity() {
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_movies -> selectedFragment = MoviesFragment()
            R.id.nav_person -> selectedFragment = PersonsFragment()
            R.id.nav_tv -> selectedFragment = TVFragment()
            R.id.nav_search -> selectedFragment = SearchFragment()
        }
        assert(selectedFragment != null)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                selectedFragment!!).commit()
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
        if (savedInstanceState == null) supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TVFragment()).commit()
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
    }
    
    
}