package ru.omsu.themoviedb.ui.activities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
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
import ru.omsu.themoviedb.Settings.POSTER_SIZE_W300
import ru.omsu.themoviedb.Settings.PROFILE_SIZE_ORIGINAL
import ru.omsu.themoviedb.Settings.PROFILE_SIZE_W185
import ru.omsu.themoviedb.Settings.URL_TMDB_BASE
import ru.omsu.themoviedb.Settings.URL_YOUTUBE_VIDEO
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.metadata.tmdb.TMDBService
import java.util.*


class ItemInfoActivity : AppCompatActivity() {
    private val tmdbService: TMDBService = TMDBService.create()
    private val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val itemType = intent.getSerializableExtra("ITEM_TYPE")
        val itemID = intent.getIntExtra("ITEM_ID", 0)
        when (itemType) {
            ItemType.MOVIE -> loadMovieOnUI(itemID)
            ItemType.TVSHOW -> loadTVShowOnUI(itemID)
            ItemType.PERSON -> loadPersonOnUI(itemID)
        }
    }

    private fun openItem(context: Context, item_ID: Int, itemType: ItemType) {
        val intent = Intent(context, ItemInfoActivity::class.java)
        intent.putExtra("ITEM_TYPE", itemType)
        intent.putExtra("ITEM_ID", item_ID)
        context.startActivity(intent)
    }

    private fun loadPersonOnUI(personID: Int) {
        tmdbService
                .getPersonDetails(personID, API_KEY, Locale.getDefault().toString().replace("_", "-"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    title_name.text = result.name
                    overview.text = result.biography
                    info_line.text = result.known_for_department
                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_ORIGINAL + result.profile_path)
                            .fitCenter()
                            .into(item_poster)
                }, { error -> error.printStackTrace() })
        play_trailer_button.visibility = View.GONE

    }

    private fun loadTVShowOnUI(tvShow_ID: Int) {
        tmdbService
                .getTVshowDetails(tvShow_ID, API_KEY, Locale.getDefault().toString().replace("_", "-"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    title_name.text = result.name
                    overview.text = result.overview
                    info_line.text = result.genres?.get(0)?.name + " | " + result.origin_country?.get(0) + " | " + result.first_air_date
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
                    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val seasonLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                    seasonLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.season)
                    for (i in 0 until result.seasons?.size!!) {
                        val seasonView = inflater.inflate(R.layout.card_item_small, seasonLayout.findViewById(R.id.list_inner_layout), false)
                        seasonView.findViewById<TextView>(R.id.title).text = result.seasons[i]?.name
                        seasonView.findViewById<TextView>(R.id.release_date).text = result.seasons[i]?.air_date
                        Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_W300 + result.seasons[i]?.poster_path).into(seasonView.findViewById(R.id.poster))
                        seasonView.setOnClickListener { openItem(this, result.seasons[i]?.id!!, ItemType.SEASON) }
                        seasonLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(seasonView)
                    }
                    item_info_layout.addView(seasonLayout)
                },
                        { error -> error.printStackTrace() })
        loadCastCrewOnUI(tvShow_ID, ItemType.TVSHOW)
    }


    private fun loadCastCrewOnUI(item_ID: Int, itemType: ItemType) {
        when (itemType) {
            ItemType.TVSHOW -> tmdbService
                    .getCreditsTVShow(item_ID, API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ credits ->
                        if (credits.cast?.size != 0) {
                            val castLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                            for (i in 0 until credits.cast?.size!!) {
                                val castView = inflater.inflate(R.layout.card_person, castLayout.findViewById(R.id.list_inner_layout), false)
                                castView.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.cast)
                                item_info_layout.addView(castLayout)
                                castView.findViewById<TextView>(R.id.name).text = credits.cast[i]?.name
                                castView.findViewById<TextView>(R.id.role).text = credits.cast[i]?.character
                                if (credits.cast[i]?.profile_path == null)
                                    Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(castView.findViewById(R.id.person_image))
                                else
                                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.cast[i]?.profile_path).fitCenter().into(castView.findViewById(R.id.person_image))
                                castView.setOnClickListener { openItem(this, credits.cast[i]?.id!!, ItemType.PERSON) }
                                castLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(castView)
                            }
                            item_info_layout.addView(castLayout)
                        }
                        if (credits.crew?.size != 0) {
                            val crewLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                            crewLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.crew)
                            for (i in 0 until credits.crew?.size!!) {
                                val crewView = inflater.inflate(R.layout.card_person, crewLayout.findViewById(R.id.list_inner_layout), false)
                                crewLayout.findViewById<TextView>(R.id.name).text = credits.crew[i]?.name
                                crewLayout.findViewById<TextView>(R.id.role).text = credits.crew[i]?.job
                                if (credits.crew[i]?.profile_path == null)
                                    Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(crewView.findViewById(R.id.person_image))
                                else
                                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.crew[i]?.profile_path).fitCenter().into(crewView.findViewById(R.id.person_image))
                                crewView.setOnClickListener { openItem(this, credits.crew[i]?.id!!, ItemType.PERSON) }
                                crewLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(crewView)
                            }
                            item_info_layout.addView(crewLayout)
                        }
                    }, { error ->
                        error.printStackTrace()
                    })
            ItemType.MOVIE -> tmdbService
                    .getCreditsMovie(item_ID, API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ credits ->
                        if (credits.cast?.size != 0) {
                            val castLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                            castLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.cast)
                            for (i in 0 until credits.cast?.size!!) {
                                val castView = inflater.inflate(R.layout.card_person, castLayout.findViewById(R.id.list_inner_layout), false)
                                castView.findViewById<TextView>(R.id.name).text = credits.cast[i]?.name
                                castView.findViewById<TextView>(R.id.role).text = credits.cast[i]?.character
                                if (credits.cast[i]?.profile_path == null)
                                    Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(castView.findViewById(R.id.person_image))
                                else
                                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.cast[i]?.profile_path).fitCenter().into(castView.findViewById(R.id.person_image))
                                castView.setOnClickListener { openItem(this, credits.cast[i]?.id!!, ItemType.PERSON) }
                                castLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(castView)
                            }
                            item_info_layout.addView(castLayout)
                        }
                        if (credits.crew?.size != 0) {
                            val crewLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                            crewLayout.findViewById<TextView>(R.id.list_head).text = resources.getText(R.string.crew)
                            for (i in 0 until credits.crew?.size!!) {
                                val crewView = inflater.inflate(R.layout.card_person, crewLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                                crewView.findViewById<TextView>(R.id.name).text = credits.crew[i]?.name
                                crewView.findViewById<TextView>(R.id.role).text = credits.crew[i]?.job
                                if (credits.crew[i]?.profile_path == null)
                                    Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(crewView.findViewById(R.id.person_image))
                                else
                                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.crew[i]?.profile_path).fitCenter().into(crewView.findViewById(R.id.person_image))
                                crewView.setOnClickListener { openItem(this, credits.crew[i]?.id!!, ItemType.PERSON) }
                                crewLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(crewView)
                            }
                            item_info_layout.addView(crewLayout)
                        }
                    }, { error ->
                        error.printStackTrace()
                    })
        }

    }

    private fun loadMovieOnUI(movie_ID: Int) {
        tmdbService
                .getMovieDetails(movie_ID, API_KEY, Locale.getDefault().toString().replace("_", "-"))
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
                            if (result.belongs_to_collection?.id != null)
                                tmdbService
                                        .getCollectionDetails(result.belongs_to_collection.id, API_KEY, Locale.getDefault().toString().replace("_", "-"))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe({ result ->
                                            val collectionLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                                            collectionLayout.findViewById<TextView>(R.id.list_head).text = resources.getText(R.string.collection)
                                            for (i in 0 until result.parts?.size!!) {
                                                val collectionView = inflater.inflate(R.layout.card_item_small, collectionLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                                                collectionView.findViewById<TextView>(R.id.title).text = result.parts[i]?.title
                                                collectionView.findViewById<TextView>(R.id.release_date).text = result.parts[i]?.release_date
                                                Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_W300 + result.parts[i]?.poster_path).into(collectionView.findViewById(R.id.poster))
                                                collectionView.setOnClickListener { openItem(this, result.parts[i]?.id!!, ItemType.MOVIE) }
                                                collectionLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(collectionView)
                                            }
                                            item_info_layout.addView(collectionLayout)
                                        }, { error ->
                                            error.printStackTrace()
                                        })
                        },
                        { error ->
                            error.printStackTrace()
                        })
        tmdbService
                .getVideosMovie(movie_ID, API_KEY, Locale.getDefault().toString().replace("_", "-"))
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
        loadCastCrewOnUI(movie_ID, ItemType.MOVIE)
    }
}