package io.github.losthikking.themoviedb.fragments.contentpagers

import androidx.fragment.app.Fragment
import io.github.losthikking.themoviedb.fragments.TvShowFragment

class TvShowContentPagerFragment : ContentPagerFragment() {
    override val tabs: Map<String, () -> Fragment> =
        mapOf(
            "Популярные" to { TvShowFragment() },
            "Лучшие" to { TvShowFragment() },
            "Последние" to { TvShowFragment() },
            "В эфире" to { TvShowFragment() },
            "Сегодня" to { TvShowFragment() }
        )
}