package ru.omsu.themoviedb

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target

@BindingAdapter("imagePosterFromUrl")
fun bindPosterFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrBlank())
        Glide.with(view.context)
                .load(URL_TMDB_BASE + POSTER_SIZE_W500 + imageUrl)
                .override(Target.SIZE_ORIGINAL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    else
        view.setImageResource(R.drawable.placeholder_poster)
}

@BindingAdapter("imageBackdropFromUrl")
fun bindBackdropFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrBlank())
        Glide.with(view.context).load(URL_TMDB_BASE + BACKDROP_SIZE_W780 + imageUrl)
                .override(Target.SIZE_ORIGINAL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(view)
}