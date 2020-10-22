package io.github.losthikking.themoviedb.android

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.annotation.ExperimentalCoilApi
import coil.load
import coil.transition.CrossfadeTransition
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import io.github.losthikking.themoviedb.android.api.tmdb.dto.ContentItem
import io.github.losthikking.themoviedb.android.api.tmdb.dto.Genre

@ExperimentalCoilApi
@BindingAdapter("imagePosterFromUrl")
fun bindPosterFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrBlank())
        view
                .load(URL_TMDB_BASE + POSTER_SIZE_W500 + imageUrl) {
                    transition(CrossfadeTransition())
                }
}

@ExperimentalCoilApi
@BindingAdapter("imageBackdropFromUrl")
fun bindBackdropFromUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Log.i("backdrop ", imageUrl)
    }
    if (!imageUrl.isNullOrBlank()) {
        view
                .load(URL_TMDB_BASE + BACKDROP_SIZE_W780 + imageUrl) {
                    transition(CrossfadeTransition())
                }
    } else {
        view.visibility = View.GONE
    }
}