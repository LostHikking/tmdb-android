package io.github.losthikking.themoviedb.fragments.contentpagers

import androidx.fragment.app.Fragment
import io.github.losthikking.themoviedb.fragments.MoviePageFragment

class MovieContentPagerFragment : ContentPagerFragment() {
    override val tabs: Map<String, () -> Fragment> =
        mapOf(
            "Популярные" to { MoviePageFragment() },
            "Лучшие" to { MoviePageFragment() },
            "Премьеры" to { MoviePageFragment() },
            "Последние" to { MoviePageFragment() }
        )
}