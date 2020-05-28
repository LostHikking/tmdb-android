package ru.omsu.themoviedb.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.omsu.themoviedb.*
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.enums.RequestTypeMovies
import ru.omsu.themoviedb.metadata.tmdb.TMDBService
import ru.omsu.themoviedb.metadata.tmdb.movie.Movie
import ru.omsu.themoviedb.ui.GlideApp
import ru.omsu.themoviedb.ui.activities.ItemInfoActivity
import java.util.*


class MovieAdapter(private val context: Context? = null) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
	
	private var page = 1
	private var category = RequestTypeMovies.POPULAR
	private var query = ""
	private val tmdbService = TMDBService.create()
	private val movieList = Collections.synchronizedList(ArrayList<Movie>())
	private var maxPage = 10000
	
	
	override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
		if ((movieList.size - position <= 10) && (page <= maxPage)) {
			getMovies()
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
	
	
	private fun getMovies() {
		when (category) {
			RequestTypeMovies.POPULAR -> {
				tmdbService.getPopularMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), page, "RU")
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ moviePage ->
							movieList.addAll(moviePage.results)
							maxPage = moviePage.total_pages
							notifyDataSetChanged()
						}, { error ->
							error.printStackTrace()
						})
			}
			RequestTypeMovies.TOP_RATED -> {
				tmdbService.getTopRatedMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), page, "RU")
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ moviePage ->
							movieList.addAll(moviePage.results)
							maxPage = moviePage.total_pages
							notifyDataSetChanged()
						}, { error ->
							error.printStackTrace()
						})
			}
			RequestTypeMovies.UPCOMING -> {
				tmdbService.getUpcomingMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), page, "RU")
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ result ->
							movieList.addAll(result.results)
							maxPage = result.total_pages
							notifyDataSetChanged()
						}, { error ->
							error.printStackTrace()
						})
			}
			RequestTypeMovies.SEARCH -> {
				tmdbService.getSearchMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), query, true, page)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ result ->
							movieList.addAll(result.results)
							maxPage = result.total_pages
							notifyDataSetChanged()
						}, { error ->
							error.printStackTrace()
						})
			}
		}
		page++
	}
	
	fun clearAndSearch(query: String?) {
		this.query = query!!
		clearItems()
		category = RequestTypeMovies.SEARCH
		resetPage()
		getMovies()
	}
	
	
	inner class MovieViewHolder(itemView: View)
		: RecyclerView.ViewHolder(itemView) {
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
			if (movie.poster_path != null)
				GlideApp.with(posterView.context).load(URL_TMDB_BASE + POSTER_SIZE_W500 + movie.poster_path)
						.diskCacheStrategy(DiskCacheStrategy.NONE)
						.override(Target.SIZE_ORIGINAL)
						.skipMemoryCache(true)
						.transition(DrawableTransitionOptions.withCrossFade())
						.into(posterView)
			else
				GlideApp.with(posterView.context).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_POSTER)).into(posterView)
			if (movie.backdrop_path != null)
				GlideApp.with(backgroundView.context).load(URL_TMDB_BASE + BACKDROP_SIZE_W780 + movie.backdrop_path)
						.diskCacheStrategy(DiskCacheStrategy.NONE)
						.skipMemoryCache(true)
						.override(Target.SIZE_ORIGINAL)
						.transition(DrawableTransitionOptions.withCrossFade())
						.fitCenter()
						.into(backgroundView)
			posterView.setOnClickListener {
				val intent = Intent(context, ItemInfoActivity::class.java)
				intent.putExtra("ITEM_TYPE", ItemType.MOVIE)
				intent.putExtra("ITEM_ID", movie.id)
				context?.startActivity(intent)
			}
			backgroundView.setOnClickListener {
				val intent = Intent(context, ItemInfoActivity::class.java)
				intent.putExtra("ITEM_TYPE", ItemType.MOVIE)
				intent.putExtra("ITEM_ID", movie.id)
				context?.startActivity(intent)
			}
		}
	}
}
