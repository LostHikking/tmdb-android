package ru.omsu.themoviedb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.adapters.MovieAdapter

class MoviesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.list)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        val movieAdapter = MovieAdapter(context!!)
        recyclerView.adapter = movieAdapter
        movieAdapter.getMovies()
    }
}