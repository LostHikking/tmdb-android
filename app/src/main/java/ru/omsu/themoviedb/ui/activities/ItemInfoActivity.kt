package ru.omsu.themoviedb.ui.activities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_item.*
import ru.omsu.themoviedb.*
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.metadata.tmdb.TMDBService
import java.util.*


class ItemInfoActivity : AppCompatActivity() {
    private val tmdbService: TMDBService = TMDBService.create()
    private var lang: String = Locale.getDefault().toString().replace("_", "-")

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemType = intent.getSerializableExtra("ITEM_TYPE")
        val itemID = intent.getIntExtra("ITEM_ID", 0)
        when (itemType) {
            ItemType.MOVIE -> loadMovieOnUI(itemID, inflater)
            ItemType.TVSHOW -> loadTVShowOnUI(itemID, inflater)
            ItemType.PERSON -> loadPersonOnUI(itemID, inflater)
            ItemType.SEASON -> loadSeasonOnUI(itemID, intent.getIntExtra("SEASON_ID", 0), inflater)
            ItemType.EPISODE -> loadEpisodeOnUI(itemID, intent.getIntExtra("SEASON_ID", 0), intent.getIntExtra("EPISODE_ID", 0), inflater)
        }
    }


    private fun loadEpisodeOnUI(itemID: Int, seasonID: Int, episode_ID: Int, inflater: LayoutInflater) {
        tmdbService
                .getEpisodeDetails(itemID, seasonID, episode_ID, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ episode ->
                    title_name.text = episode.name
                    info_line.text = episode.air_date
                    overview.text = episode.overview
                    (item_poster.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "16:9"
                    if (episode.still_path != null)
                        Glide.with(this).load(URL_TMDB_BASE + STILL_SIZE_ORIGINAL + episode.still_path)
                                .fitCenter()
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(item_poster)
                    else
                        Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_STILL)).fitCenter().into(item_poster)
                    tmdbService
                            .getSeasonDetails(itemID, episode.season_number!!, API_KEY, lang)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ season ->
                                val episodesLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                                episodesLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.episodes_list_other)
                                for (i in season.episodes.indices) {
                                    val episodeView = inflater.inflate(R.layout.card_episode_small, episodesLayout.findViewById(R.id.list_inner_layout), false)
                                    episodeView.findViewById<TextView>(R.id.title).text = season.episodes[i].name
                                    episodeView.findViewById<TextView>(R.id.release_date).text = season.episodes[i].air_date
                                    Glide.with(this).load(URL_TMDB_BASE + STILL_SIZE_W300 + season.episodes[i].still_path)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(episodeView.findViewById(R.id.poster))
                                    episodeView.setOnClickListener { openItem(this, itemID, ItemType.EPISODE, seasonID, season.episodes[i].episode_number) }
                                    episodesLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(episodeView)
                                }
                                item_info_layout.addView(episodesLayout)
                            },
                                    { error -> error.printStackTrace() })
                    val castLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                    castLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.cast)
                    for (i in episode.guest_stars.indices) {
                        val castView = inflater.inflate(R.layout.card_person, castLayout.findViewById(R.id.list_inner_layout), false)
                        item_info_layout.addView(castLayout)
                        castView.findViewById<TextView>(R.id.name).text = episode.guest_stars[i].name
                        castView.findViewById<TextView>(R.id.role).text = episode.guest_stars[i].character
                        if (episode.guest_stars[i].profile_path == null)
                            Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter()
                                    .into(castView.findViewById(R.id.person_image))
                        else
                            Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + episode.guest_stars[i].profile_path).fitCenter()
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(castView.findViewById(R.id.person_image))
                        castView.setOnClickListener { openItem(this, episode.guest_stars[i].id!!, ItemType.PERSON) }
                        castLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(castView)
                    }
                    item_info_layout.addView(castLayout)
                    val crewLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                    crewLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.cast)
                    for (i in episode.guest_stars.indices) {
                        val castView = inflater.inflate(R.layout.card_person, castLayout.findViewById(R.id.list_inner_layout), false)
                        item_info_layout.addView(castLayout)
                        castView.findViewById<TextView>(R.id.name).text = episode.crew[i].name
                        castView.findViewById<TextView>(R.id.role).text = episode.crew[i].job
                        if (episode.crew[i].profile_path == null)
                            Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(castView.findViewById(R.id.person_image))
                        else
                            Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + episode.crew[i].profile_path).fitCenter()
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(castView.findViewById(R.id.person_image))
                        castView.setOnClickListener { openItem(this, episode.crew[i].id, ItemType.PERSON) }
                        castLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(castView)
                    }
                    item_info_layout.addView(crewLayout)
                },
                        { error -> error.printStackTrace() })
    }

    private fun loadSeasonOnUI(itemID: Int, seasonNumber: Int, inflater: LayoutInflater) {
        tmdbService
                .getSeasonDetails(itemID, seasonNumber, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ season ->
                    title_name.text = season.name
                    info_line.text = season.air_date
                    if (season.poster_path != null) {
                        Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + season.poster_path)
                                .fitCenter()
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(item_poster)
                        Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + season.poster_path)
                                .apply(RequestOptions.bitmapTransform(BlurTransformation(40, 10))).into(object : CustomTarget<Drawable>() {
                                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                        resource.alpha = 70
                                        item_layout.background = resource
                                    }
            
                                    override fun onLoadCleared(placeholder: Drawable?) {
                                    }
                                })
                    } else
                        Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_POSTER)).fitCenter().into(item_poster)
                    val episodesLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                    episodesLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.episodes_list)
                    for (i in season.episodes.indices) {
                        val episodeView = inflater.inflate(R.layout.card_episode_small, episodesLayout.findViewById(R.id.list_inner_layout), false)
                        episodeView.findViewById<TextView>(R.id.title).text = season.episodes[i].name
                        episodeView.findViewById<TextView>(R.id.release_date).text = season.episodes[i].air_date
                        if (season.episodes[i].still_path == null)
                            Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_POSTER)).fitCenter()
                                    .into(episodeView.findViewById(R.id.poster))
                        else
                            Glide.with(this).load(URL_TMDB_BASE + STILL_SIZE_W300 + season.episodes[i].still_path)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(episodeView.findViewById(R.id.poster))
                        episodeView.setOnClickListener { openItem(this, itemID, ItemType.EPISODE, seasonNumber, season.episodes[i].episode_number) }
                        episodesLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(episodeView)
                    }
                    item_info_layout.addView(episodesLayout)
                },
                        { error -> error.printStackTrace() })
    }

    private fun openItem(context: Context, item_ID: Int, itemType: ItemType, season_ID: Int? = null, episode_ID: Int? = null) {
        val intent = Intent(context, ItemInfoActivity::class.java)
        intent.putExtra("ITEM_TYPE", itemType)
        intent.putExtra("ITEM_ID", item_ID)
        intent.putExtra("SEASON_ID", season_ID)
        intent.putExtra("EPISODE_ID", episode_ID)
        context.startActivity(intent)
    }

    private fun loadPersonOnUI(personID: Int, inflater: LayoutInflater) {
        tmdbService
                .getPersonDetails(personID, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ person ->
                    title_name.text = person.name
                    overview.text = person.biography
                    info_line.text = person.known_for_department
                    Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_ORIGINAL + person.profile_path)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .fitCenter()
                            .into(item_poster)

                }, { error -> error.printStackTrace() })
        play_trailer_button.visibility = View.GONE

    }

    private fun loadTVShowOnUI(tvShow_ID: Int, inflater: LayoutInflater) {
        tmdbService
                .getTVshowDetails(tvShow_ID, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tvShow ->
                    title_name.text = tvShow.name
                    overview.text = tvShow.overview
                    info_line.text = tvShow.genres?.get(0)?.name + " | " + tvShow.origin_country[0] + " | " + tvShow.first_air_date
                    item_rating.text = HtmlCompat.fromHtml("<b>" + tvShow.vote_average + "</b>/10", HtmlCompat.FROM_HTML_MODE_LEGACY)
                    Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + tvShow.poster_path)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .fitCenter()
                            .into(item_poster)
                    Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + tvShow.poster_path)
                            .apply(RequestOptions.bitmapTransform(BlurTransformation(40, 10)))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(object : CustomTarget<Drawable>() {
                                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                    resource.alpha = 70
                                    item_layout.background = resource
                                }
            
                                override fun onLoadCleared(placeholder: Drawable?) {
                                }
                            })
                    val seasonLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                    seasonLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.season)
                    for (i in tvShow.seasons.indices) {
                        val seasonView = inflater.inflate(R.layout.card_item_small, seasonLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                        seasonView.findViewById<TextView>(R.id.title).text = tvShow.seasons[i].name
                        seasonView.findViewById<TextView>(R.id.release_date).text = tvShow.seasons[i].air_date
                        if (tvShow.seasons[i].poster_path != null)
                            Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_W300 + tvShow.seasons[i].poster_path)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(seasonView.findViewById(R.id.poster))
                        else
                            Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_POSTER)).fitCenter()
                                    .into(seasonView.findViewById(R.id.poster))
                        seasonView.setOnClickListener { openItem(this, tvShow.id!!, ItemType.SEASON, tvShow.seasons[i].season_number!!) }
                        seasonLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(seasonView)
                    }
                    item_info_layout.addView(seasonLayout)
                    tmdbService
                            .getVideosTVShow(tvShow_ID, API_KEY, lang)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { videos ->
                                        if (videos.results[0].site == "YouTube")
                                            play_trailer_button.setOnClickListener {
                                                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.results[0].key))
                                                val webIntent = Intent(Intent.ACTION_VIEW,
                                                        Uri.parse(URL_YOUTUBE_VIDEO + videos.results[0].key))
                                                try {
                                                    startActivity(appIntent)
                                                } catch (ex: ActivityNotFoundException) {
                                                    startActivity(webIntent)
                                                }
                                            }
                                        play_trailer_button.visibility = View.VISIBLE
                                    },
                                    { error ->
                                        error.printStackTrace()
                                    }
                            )
                },
                        { error -> error.printStackTrace() })
        loadCastCrewOnUI(tvShow_ID, ItemType.TVSHOW, inflater)
    }


    private fun loadCastCrewOnUI(item_ID: Int, itemType: ItemType, inflater: LayoutInflater) {
        when (itemType) {
            ItemType.TVSHOW -> {
                tmdbService
                        .getCreditsTVShow(item_ID, API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ credits ->
                            if (credits.cast.isNotEmpty()) {
                                val castLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                                castLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.cast)
                                for (i in credits.cast.indices) {
                                    val castView = inflater.inflate(R.layout.card_person, castLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                                    castView.findViewById<TextView>(R.id.name).text = credits.cast[i].name
                                    castView.findViewById<TextView>(R.id.role).text = credits.cast[i].character
                                    if (credits.cast[i].profile_path == null)
                                        Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(castView.findViewById(R.id.person_image))
                                    else
                                        Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.cast[i].profile_path).fitCenter()
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(castView.findViewById(R.id.person_image))
                                    castView.setOnClickListener { openItem(this, credits.cast[i].id!!, ItemType.PERSON) }
                                    castLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(castView)
                                }
                                item_info_layout.addView(castLayout)
                            }
                            if (credits.crew.isNotEmpty()) {
                                val crewLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                                crewLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.crew)
                                for (i in credits.crew.indices) {
                                    val crewView = inflater.inflate(R.layout.card_person, crewLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                                    crewView.findViewById<TextView>(R.id.name).text = credits.crew[i].name
                                    crewView.findViewById<TextView>(R.id.role).text = credits.crew[i].job
                                    if (credits.crew[i].profile_path == null)
                                        Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(crewView.findViewById(R.id.person_image))
                                    else
                                        Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.crew[i].profile_path).fitCenter()
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(crewView.findViewById(R.id.person_image))
                                    crewView.setOnClickListener { openItem(this, credits.crew[i].id, ItemType.PERSON) }
                                    crewLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(crewView)
                                }
                                item_info_layout.addView(crewLayout)
                            }
                        }, { error ->
                            error.printStackTrace()
                        })
            }
            ItemType.MOVIE -> {
                tmdbService
                        .getCreditsMovie(item_ID, API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ credits ->
                            if (credits.cast.isNotEmpty()) {
                                val castLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                                castLayout.findViewById<TextView>(R.id.list_head).text = resources.getString(R.string.cast)
                                for (i in credits.cast.indices) {
                                    val castView = inflater.inflate(R.layout.card_person, castLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                                    castView.findViewById<TextView>(R.id.name).text = credits.cast[i].name
                                    castView.findViewById<TextView>(R.id.role).text = credits.cast[i].character
                                    if (credits.cast[i].profile_path == null)
                                        Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(castView.findViewById(R.id.person_image))
                                    else
                                        Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.cast[i].profile_path).fitCenter()
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(castView.findViewById(R.id.person_image))
                                    castView.setOnClickListener { openItem(this, credits.cast[i].id!!, ItemType.PERSON) }
                                    castLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(castView)
                                }
                                item_info_layout.addView(castLayout)
                            }
                            if (credits.crew.isNotEmpty()) {
                                val crewLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                                crewLayout.findViewById<TextView>(R.id.list_head).text = resources.getText(R.string.crew)
                                for (i in credits.crew.indices) {
                                    val crewView = inflater.inflate(R.layout.card_person, crewLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                                    crewView.findViewById<TextView>(R.id.name).text = credits.crew[i].name
                                    crewView.findViewById<TextView>(R.id.role).text = credits.crew[i].job
                                    if (credits.crew[i].profile_path == null)
                                        Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter()
                                                .into(crewView.findViewById(R.id.person_image))
                                    else
                                        Glide.with(this).load(URL_TMDB_BASE + PROFILE_SIZE_W185 + credits.crew[i].profile_path).fitCenter()
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(crewView.findViewById(R.id.person_image))
                                    crewView.setOnClickListener { openItem(this, credits.crew[i].id, ItemType.PERSON) }
                                    crewLayout.findViewById<LinearLayout>(R.id.list_inner_layout).addView(crewView)
                                }
                                item_info_layout.addView(crewLayout)
                            }
                        }, { error ->
                            error.printStackTrace()
                        })
            }
        }

    }

    private fun loadMovieOnUI(movie_ID: Int, inflater: LayoutInflater) {
        tmdbService
                .getMovieDetails(movie_ID, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { movie ->
                            title_name.text = movie.title
                            overview.text = movie.overview
                            info_line.text = movie.genres[0].name + " | " + movie.production_countries[0].iso_3166_1 + " | " + movie.release_date
                            item_rating.text = HtmlCompat.fromHtml("<b>" + movie.vote_average + "</b>/10", HtmlCompat.FROM_HTML_MODE_LEGACY)
                            if (movie.poster_path != null) {
                                Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + movie.poster_path)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .fitCenter()
                                        .into(item_poster)
                                Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_ORIGINAL + movie.poster_path)
                                        .apply(RequestOptions.bitmapTransform(BlurTransformation(40, 10)))
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(object : CustomTarget<Drawable>() {
                                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                                resource.alpha = 70
                                                item_layout.background = resource
                                            }
            
                                            override fun onLoadCleared(placeholder: Drawable?) {
                                            }
                                        })
                            } else
                                Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_POSTER)).fitCenter().into(item_poster)
                            if (movie.belongs_to_collection?.id != null)
                                tmdbService
                                        .getCollectionDetails(movie.belongs_to_collection.id, API_KEY, lang)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe({ collection ->
                                            val collectionLayout: LinearLayout = inflater.inflate(R.layout.list, item_info_layout, false) as LinearLayout
                                            collectionLayout.findViewById<TextView>(R.id.list_head).text = resources.getText(R.string.collection)
                                            for (i in collection.parts.indices) {
                                                val collectionView = inflater.inflate(R.layout.card_item_small, collectionLayout.findViewById(R.id.list_inner_layout), false) as LinearLayout
                                                collectionView.findViewById<TextView>(R.id.title).text = collection.parts[i].title
                                                collectionView.findViewById<TextView>(R.id.release_date).text = collection.parts[i].release_date
                                                if (collection.parts[i].poster_path == null)
                                                    Glide.with(this).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_POSTER)).fitCenter()
                                                            .into(collectionView.findViewById(R.id.poster))
                                                else
                                                    Glide.with(this).load(URL_TMDB_BASE + POSTER_SIZE_W300 + collection.parts[i].poster_path)
                                                            .transition(DrawableTransitionOptions.withCrossFade())
                                                            .into(collectionView.findViewById(R.id.poster))
                                                collectionView.setOnClickListener { openItem(this, collection.parts[i].id, ItemType.MOVIE) }
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
                .getVideosMovie(movie_ID, API_KEY, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { videos ->
                            if (videos.results[0].site == "YouTube")
                                play_trailer_button.setOnClickListener {
                                    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.results[0].key))
                                    val webIntent = Intent(Intent.ACTION_VIEW,
                                            Uri.parse(URL_YOUTUBE_VIDEO + videos.results[0].key))
                                    try {
                                        startActivity(appIntent)
                                    } catch (ex: ActivityNotFoundException) {
                                        startActivity(webIntent)
                                    }
                                }
                            play_trailer_button.visibility = View.VISIBLE
                        },
                        { error ->
                            error.printStackTrace()
                        }
                )
        loadCastCrewOnUI(movie_ID, ItemType.MOVIE, inflater)
    }
}