package ru.omsu.themoviedb.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.Settings.API_KEY
import ru.omsu.themoviedb.Settings.BACKDROP_SIZE_W780
import ru.omsu.themoviedb.Settings.POSTER_SIZE_W500
import ru.omsu.themoviedb.Settings.URL_TMDB_BASE
import ru.omsu.themoviedb.adapters.TVShowsAdapter.TVShowViewHolder
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.enums.RequestTypeTVShows
import ru.omsu.themoviedb.metadata.tmdb.TMDBService
import ru.omsu.themoviedb.metadata.tmdb.tvshow.TVShow
import ru.omsu.themoviedb.ui.activities.ItemInfoActivity
import java.util.*

class TVShowsAdapter(private val context: Context) : RecyclerView.Adapter<TVShowViewHolder>() {

    private var page = 1
    private var category = RequestTypeTVShows.POPULAR

    private val tvShowList = Collections.synchronizedList(ArrayList<TVShow>())

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        if (tvShowList.size - position <= 5) {
            getTVShows()
            page++
        }
        holder.bind(tvShowList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.card_item, parent, false)
        return TVShowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    private fun resetPage() {
        page = 1
    }

    fun clearAndSetPopular() {
        clearItems()
        category = RequestTypeTVShows.POPULAR
        resetPage()
        getTVShows()
    }

    fun clearAndSetTopRated() {
        clearItems()
        category = RequestTypeTVShows.TOP_RATED
        resetPage()
        getTVShows()
    }

    fun setItems(tvShows: Collection<TVShow>) {
        tvShowList.addAll(tvShows)
        notifyDataSetChanged()
    }

    private fun clearItems() {
        tvShowList.clear()
        notifyDataSetChanged()
    }

    fun getTVShows() {
        val tmdbService = TMDBService.create()
        tmdbService.getPopularTV(API_KEY, Locale.getDefault().toString().replace("_", "-"), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    tvShowList.addAll(result.results!!)
                    notifyDataSetChanged()
                }, { error ->
                    error.printStackTrace()
                })
    }

    inner class TVShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val posterView: ImageView = itemView.findViewById(R.id.poster)
        private val backgroundView: ImageView = itemView.findViewById(R.id.background)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val releaseDateView: TextView = itemView.findViewById(R.id.releaseDate)
        private val overviewView: TextView = itemView.findViewById(R.id.overview)
        private val ratingView: TextView = itemView.findViewById(R.id.rating)
        fun bind(tvShow: TVShow) {
            titleView.text = tvShow.name
            overviewView.text = tvShow.overview
            if (tvShow.vote_average != 0.0) ratingView.text = tvShow.vote_average!!.toString() else ratingView.text = "-"
            releaseDateView.text = tvShow.first_air_date
            if (tvShow.poster_path != null) Glide.with(itemView.context).load(URL_TMDB_BASE + POSTER_SIZE_W500 + tvShow.poster_path).fitCenter().into(posterView)
            if (tvShow.backdrop_path != null) Glide.with(itemView.context).load(URL_TMDB_BASE + BACKDROP_SIZE_W780 + tvShow.backdrop_path).fitCenter().into(backgroundView)
            posterView.setOnClickListener {
                val intent = Intent(context, ItemInfoActivity::class.java)
                intent.putExtra("ITEM_ID", tvShow.id)
                intent.putExtra("ITEM_TYPE", ItemType.TVSHOW)
                context.startActivity(intent)
            }
            backgroundView.setOnClickListener {
                val intent = Intent(context, ItemInfoActivity::class.java)
                intent.putExtra("ITEM_ID", tvShow.id)
                intent.putExtra("ITEM_TYPE", ItemType.TVSHOW)
                context.startActivity(intent)
            }
        }

    }
}