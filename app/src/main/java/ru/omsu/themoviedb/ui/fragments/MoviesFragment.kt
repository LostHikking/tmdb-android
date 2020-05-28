package ru.omsu.themoviedb.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.ui.adapters.MovieAdapter


class MoviesFragment : Fragment() {
	private var mSearch: MenuItem? = null
	private var movieAdapter: MovieAdapter? = null
	private var mSearchView: SearchView? = null
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_list, container, false)
		movieAdapter = MovieAdapter(context!!)
		initRecyclerView(view)
		setHasOptionsMenu(true)
		return view
	}
	
	private fun initRecyclerView(view: View) {
		val recyclerView: RecyclerView = view.findViewById(R.id.list)
		recyclerView.setHasFixedSize(true)
		recyclerView.setItemViewCacheSize(20)
		val mLayoutManager = LinearLayoutManager(context)
		recyclerView.layoutManager = mLayoutManager
		recyclerView.adapter = movieAdapter
		val spinner = activity?.findViewById<Spinner>(R.id.spinner_list_type)
		ArrayAdapter.createFromResource(activity?.baseContext!!, R.array.list_type_movies, R.layout.simple_spinner_item)
				.also { adapter ->
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
					spinner!!.adapter = adapter
				}
		spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}
			
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				when (position) {
					0 -> {
						movieAdapter?.clearAndSetPopular()
					}
					1 -> {
						movieAdapter?.clearAndSetTopRated()
					}
					2 -> {
						movieAdapter?.clearAndSetUpcoming()
					}
				}
			}
		}
	}
	
	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.menu_main, menu)
		mSearch = menu.findItem(R.id.action_search)
		mSearchView = mSearch!!.actionView as SearchView
		mSearchView!!.queryHint = "Поиск по фильмам"
		mSearch!!.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
			override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
				return true
			}
			
			override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
				movieAdapter?.clearAndSetPopular()
				return true
			}
		})
		mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				movieAdapter!!.clearAndSearch(query)
				return true
			}
			
			override fun onQueryTextChange(newText: String?): Boolean {
				return false
			}
			
		})
		super.onCreateOptionsMenu(menu, inflater)
	}
	
	override fun onPause() {
		mSearch!!.collapseActionView()
		super.onPause()
	}
}