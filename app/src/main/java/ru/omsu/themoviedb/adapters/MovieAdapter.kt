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
import ru.omsu.themoviedb.UI.Activities.ItemInfoActivity
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.enums.RequestTypeMovies
import ru.omsu.themoviedb.metadata.tmdb.TMDBService
import ru.omsu.themoviedb.metadata.tmdb.movie.Movie
import java.util.*

class MovieAdapter(private val context: Context) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var page = 1
    var category = RequestTypeMovies.POPULAR
        private set

    private val movieList = Collections.synchronizedList(ArrayList<Movie>())

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (movieList.size - position <= 10) {
            getMovies()
            page++
        }
        holder.bind(movieList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.card_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun resetPage() {
        this.page = 1
    }

    fun clearAndSetPopular() {
        clearItems()
        category = RequestTypeMovies.POPULAR
        resetPage()
        getMovies()

    }

    fun clearAndSetUpcoming() {
        clearItems()
        category = RequestTypeMovies.UPCOMING
        resetPage()
        getMovies()
    }

    fun clearAndSetTopRated() {
        clearItems()
        category = RequestTypeMovies.TOP_RATED
        resetPage()
        getMovies()
    }

    fun setItems(movie: Collection<Movie>) {
        movieList.addAll(movie)
        notifyDataSetChanged()
    }

    private fun clearItems() {
        movieList.clear()
        notifyDataSetChanged()
    }

    fun getMovies() {
        val tmdbService = TMDBService.create()
        tmdbService.getPopularMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), page, "RU")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    movieList.addAll(result.results!!)
                    notifyDataSetChanged()
                }, { error ->
                    error.printStackTrace()
                })
    }


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val posterView: ImageView = itemView.findViewById(R.id.poster)
        private val backgroundView: ImageView = itemView.findViewById(R.id.background)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val releaseDateView: TextView = itemView.findViewById(R.id.releaseDate)
        private val overviewView: TextView = itemView.findViewById(R.id.overview)
        private val ratingView: TextView = itemView.findViewById(R.id.rating)

        fun bind(movie: Movie) {
            titleView.text = movie.title
            overviewView.text = movie.overview
            if (movie.vote_average != 0.0) {
                ratingView.text = movie.vote_average.toString()
            } else
                ratingView.text = "-"
            releaseDateView.text = movie.release_date
            Glide.with(itemView.context).load(URL_TMDB_BASE + POSTER_SIZE_W500 + movie.poster_path).fitCenter().dontTransform().into(posterView)
            Glide.with(itemView.context).load(URL_TMDB_BASE + BACKDROP_SIZE_W780 + movie.backdrop_path).fitCenter().dontTransform().into(backgroundView)
            posterView.setOnClickListener {
                val intent = Intent(context, ItemInfoActivity::class.java)
                intent.putExtra("ITEM_TYPE", ItemType.MOVIE)
                intent.putExtra("ITEM_ID", movie.id)
                context.startActivity(intent)
            }
            backgroundView.setOnClickListener {
                val intent = Intent(context, ItemInfoActivity::class.java)
                intent.putExtra("ITEM_TYPE", ItemType.MOVIE)
                intent.putExtra("ITEM_ID", movie.id)
                context.startActivity(intent)
            }
        }
    }
}
