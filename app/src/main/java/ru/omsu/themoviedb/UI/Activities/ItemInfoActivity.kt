package ru.omsu.themoviedb.UI.Activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_item.*
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.Settings.API_KEY
import ru.omsu.themoviedb.Settings.PATH_IMAGE_PERSON_NO_PHOTO
import ru.omsu.themoviedb.Settings.POSTER_SIZE_ORIGINAL
import ru.omsu.themoviedb.Settings.PROFILE_SIZE_W185
import ru.omsu.themoviedb.Settings.URL_TMDB_BASE
import ru.omsu.themoviedb.Settings.URL_YOUTUBE_VIDEO
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.metadata.tmdb.TMDBService
import java.util.*


class ItemInfoActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val itemType = intent.getSerializableExtra("ITEM_TYPE")
        val itemID = intent.getIntExtra("ITEM_ID", 0)
        when (itemType) {
            ItemType.MOVIE -> {
                TMDBService
                        .create()
                        .getMovieDetails(itemID, API_KEY, "RU-ru")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    title_name.text = result.title
                                    overview.text = result.overview
                                    info_line.text = result.genres?.get(0)?.name + " | " + result.production_countries?.get(0)?.iso_3166_1 + " | " + result.release_date
                                    item_rating.text = Html.fromHtml("<b>" + result.vote_average + "</b>/10")
                                    Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + result.poster_path)
                                            .fitCenter()
                                            .into(item_poster)
                                    Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + result.poster_path)
                                            .apply(RequestOptions.bitmapTransform(BlurTransformation(40, 10))).into(object : SimpleTarget<Drawable>() {
                                                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                                    resource.alpha = 70
                                                    item_layout.background = resource
                                                }
                                            })
                                },
                                { error ->
                                    error.printStackTrace()
                                })
                TMDBService
                        .create()
                        .getVideos(itemID, API_KEY, Locale.getDefault().toString().replace("_", "-"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    if (result.results?.get(0)?.site.equals("YouTube"))
                                        play_trailer_button.setOnClickListener {
                                            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + result.results?.get(0)?.key))
                                            val webIntent = Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(URL_YOUTUBE_VIDEO + result.results?.get(0)?.key))
                                            try {
                                                startActivity(appIntent)
                                            } catch (ex: ActivityNotFoundException) {
                                                startActivity(webIntent)
                                            }
                                        }
                                },
                                { error ->
                                    error.printStackTrace()
                                }
                        )
                TMDBService
                        .create()
                        .getCredits(itemID, API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            for (i in 0 until it.cast?.size!!) {
                                val castView = LayoutInflater.from(this).inflate(R.layout.card_person, null)
                                castView.findViewById<TextView>(R.id.name).text = it.cast[i]?.name
                                castView.findViewById<TextView>(R.id.role).text = it.cast[i]?.character
                                if (it.cast[i]?.profile_path == null)
                                    Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(castView.findViewById(R.id.person_image))
                                else
                                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + it.cast[i]?.profile_path).fitCenter().into(castView.findViewById(R.id.person_image))
                                cast.addView(castView)
                            }
                            for (i in 0 until it.crew?.size!!) {
                                val crewView = LayoutInflater.from(this).inflate(R.layout.card_person, null)
                                crewView.findViewById<TextView>(R.id.name).text = it.crew[i]?.name
                                crewView.findViewById<TextView>(R.id.role).text = it.crew[i]?.job
                                if (it.crew[i]?.profile_path == null)
                                    Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(crewView.findViewById(R.id.person_image))
                                else
                                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + it.crew[i]?.profile_path).fitCenter().into(crewView.findViewById(R.id.person_image))
                                crew.addView(crewView)
                            }
                        }, { error ->
                            error.printStackTrace()
                        })
            }
        }
    }
}