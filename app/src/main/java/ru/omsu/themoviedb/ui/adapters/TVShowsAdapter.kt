package ru.omsu.themoviedb.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.omsu.themoviedb.*
import ru.omsu.themoviedb.enums.ItemType
import ru.omsu.themoviedb.enums.RequestTypeTVShows
import ru.omsu.themoviedb.metadata.tmdb.TMDBService
import ru.omsu.themoviedb.metadata.tmdb.tvshow.TVShow
import ru.omsu.themoviedb.ui.GlideApp
import ru.omsu.themoviedb.ui.activities.ItemInfoActivity
import ru.omsu.themoviedb.ui.adapters.TVShowsAdapter.TVShowViewHolder
import java.util.*


class TVShowsAdapter(private val context: Context? = null) : RecyclerView.Adapter<TVShowViewHolder>() {
	
	private var page = 1
	private var query = ""
	private var category = RequestTypeTVShows.POPULAR
	private var maxPage = 10000
	private val tmdbService = TMDBService.create()
	private val tvShowList = Collections.synchronizedList(ArrayList<TVShow>())
	
	override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
		if ((tvShowList.size - position <= 5) && (page <= maxPage)) {
			getTVShows()
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
	
	fun clearAndSetLatest() {
		clearItems()
		category = RequestTypeTVShows.LATEST
		resetPage()
		getTVShows()
	}
	
	fun setItems(tvShows: Collection<TVShow>) {
		tvShowList.addAll(tvShows)
		notifyDataSetChanged()
	}
	
	fun clearAndSearch(query: String?) {
		this.query = query!!
		clearItems()
		category = RequestTypeTVShows.SEARCH
		resetPage()
		getTVShows()
	}
	
	private fun clearItems() {
		tvShowList.clear()
		notifyDataSetChanged()
	}
	
	fun getTVShows() {
		
		when (category) {
			RequestTypeTVShows.POPULAR -> {
				tmdbService.getPopularTV(API_KEY, Locale.getDefault().toString().replace("_", "-"), page)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ result ->
							tvShowList.addAll(result.results!!)
							maxPage = result.total_pages!!
							notifyDataSetChanged()
						}, { error ->
							error.printStackTrace()
						})
			}
			RequestTypeTVShows.TOP_RATED -> {
				tmdbService.getTopRatedTV(API_KEY, Locale.getDefault().toString().replace("_", "-"), page)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ result ->
							tvShowList.addAll(result.results!!)
							maxPage = result.total_pages!!
							notifyDataSetChanged()
						}, { error ->
							error.printStackTrace()
						})
			}
			RequestTypeTVShows.LATEST -> {
				tmdbService.getLatestTV(API_KEY, Locale.getDefault().toString().replace("_", "-"), page)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ result ->
							tvShowList.addAll(result.results!!)
							maxPage = result.total_pages!!
							notifyDataSetChanged()
						}, { error ->
							error.printStackTrace()
						})
			}
			RequestTypeTVShows.SEARCH -> {
				tmdbService.getSearchTVShows(API_KEY, Locale.getDefault().toString().replace("_", "-"), query, page)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe({ result ->
							if (result.total_results != 0) {
								tvShowList.addAll(result.results!!)
								maxPage = result.total_pages!!
								notifyDataSetChanged()
							} else
								Toast.makeText(context, "Ничего не найдено", Toast.LENGTH_SHORT).show()
						}, { error ->
							error.printStackTrace()
						})
			}
		}
		page++
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
			if (tvShow.poster_path != null) GlideApp.with(posterView.context).load(URL_TMDB_BASE + POSTER_SIZE_W500 + tvShow.poster_path)
					.diskCacheStrategy(DiskCacheStrategy.NONE)
					.skipMemoryCache(true)
					.override(Target.SIZE_ORIGINAL)
					.transition(DrawableTransitionOptions.withCrossFade())
					.into(posterView)
			else
				GlideApp.with(posterView.context).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_POSTER)).fitCenter().into(posterView)
			if (tvShow.backdrop_path != null) GlideApp.with(backgroundView.context).load(URL_TMDB_BASE + BACKDROP_SIZE_W780 + tvShow.backdrop_path)
					.diskCacheStrategy(DiskCacheStrategy.NONE)
					.override(Target.SIZE_ORIGINAL)
					.skipMemoryCache(true)
					.transition(DrawableTransitionOptions.withCrossFade())
					.fitCenter()
					.into(backgroundView)
			posterView.setOnClickListener {
				val intent = Intent(context, ItemInfoActivity::class.java)
				intent.putExtra("ITEM_ID", tvShow.id)
				intent.putExtra("ITEM_TYPE", ItemType.TVSHOW)
				context?.startActivity(intent)
			}
			backgroundView.setOnClickListener {
				val intent = Intent(context, ItemInfoActivity::class.java)
				intent.putExtra("ITEM_ID", tvShow.id)
				intent.putExtra("ITEM_TYPE", ItemType.TVSHOW)
				context?.startActivity(intent)
			}
		}
		
	}
}