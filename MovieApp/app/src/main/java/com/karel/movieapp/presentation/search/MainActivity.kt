package com.karel.movieapp.presentation.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karel.movieapp.databinding.ActivityMainBinding
import com.karel.movieapp.presentation.detail.MovieDetailFragment

class MainActivity : AppCompatActivity(), IViewMainActivity {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeView()
    }

    private fun initializeView() {
        initializeSearchView()
        initializeMoviesView()
        observeViewModel()

        //viewModel.getMoviesBySearchTerm("Dragon")
    }

    private fun initializeMoviesView() {
        binding.moviesView.layoutManager = LinearLayoutManager(this)
        binding.moviesView.adapter = MovieAdapter { id ->
            MovieDetailFragment.show(supportFragmentManager, id)
        }
        binding.moviesView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    val currentPosition = layoutManager.findLastVisibleItemPosition()
                    viewModel.onScroll(currentPosition)
                }

            }
        })
    }

    private fun initializeSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getMoviesBySearchTerm(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this, Observer { value ->
            addMoviesToView(value)
        })

        viewModel.error.observe(this, Observer { value ->
            if (value.isNotEmpty()) {
                renderErrorView(value)
            } else {
                hideErrorView()
            }
        })

        viewModel.loading.observe(this, Observer { value ->
            if (value) {
                renderLoadingView()
            } else {
                hideLoadingView()
            }
        })
    }

    override fun addMoviesToView(movies: List<MovieSearchViewModel>) {
        (binding.moviesView.adapter as? MovieAdapter)?.addItems(movies)
    }

    override fun clearMoviesFromView() {
    }

    override fun renderErrorView(error: String?) {
    }

    override fun hideErrorView() {
    }

    override fun renderLoadingView() {
    }

    override fun hideLoadingView() {
    }
}

interface IViewMainActivity {

    fun addMoviesToView(movies: List<MovieSearchViewModel>)

    fun renderErrorView(error: String?)

    fun hideErrorView()

    fun renderLoadingView()

    fun hideLoadingView()

    fun clearMoviesFromView()
}
