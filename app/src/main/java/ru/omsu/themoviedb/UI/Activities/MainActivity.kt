package ru.omsu.themoviedb.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.UI.Fragments.MoviesFragment
import ru.omsu.themoviedb.UI.Fragments.PersonsFragment
import ru.omsu.themoviedb.UI.Fragments.SearchFragment
import ru.omsu.themoviedb.UI.Fragments.TVFragment

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
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MoviesFragment()).commit()
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
    }
}