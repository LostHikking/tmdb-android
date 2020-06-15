package ru.omsu.themoviedb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.recommendation.UserDTO
import ru.omsu.themoviedb.ui.adapters.MovieAdapter

class RecommendationsFragment(val userDTO: UserDTO) : Fragment() {
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        movieAdapter = MovieAdapter(requireContext())
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
        ArrayAdapter.createFromResource(activity?.baseContext!!, R.array.list_type_recommendations, R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner!!.adapter = adapter
                }
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        movieAdapter.clearAndSetRecommendations(userDTO.recommendations)
                    }
                    1 -> {
                        movieAdapter.clearAndSetScores(userDTO.scores)
                    }
                }
            }
        }
    }
}