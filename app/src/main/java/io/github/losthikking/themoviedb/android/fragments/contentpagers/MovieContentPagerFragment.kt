package io.github.losthikking.themoviedb.android.fragments.contentpagers

import androidx.fragment.app.Fragment
import io.github.losthikking.themoviedb.android.fragments.MoviePageFragment

class MovieContentPagerFragment : ContentPagerFragment() {
    override val tabs: Map<String, () -> Fragment> =
        mapOf(
            "Популярные" to { MoviePageFragment() },
            "Лучшие" to { MoviePageFragment() },
            "Премьеры" to { MoviePageFragment() },
            "Последние" to { MoviePageFragment() }
        )
}
