package com.karel.movieapp.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.karel.movieapp.R
import com.karel.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IViewMainActivity {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        binding.moviesView.layoutManager = LinearLayoutManager(this)
        binding.moviesView.adapter = MovieAdapter()

        observeViewModel()

        viewModel.getMoviesBySearchTerm("Dragon")
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this, Observer { value ->
            renderMoviesView(value)
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

    fun addMovies() {

    }

    override fun renderMoviesView(movies: List<MovieSearchViewModel>) {
        (binding.moviesView.adapter as? MovieAdapter)?.addItems(movies)
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

    fun renderMoviesView(movies: List<MovieSearchViewModel>)

    fun renderErrorView(error: String?)

    fun hideErrorView()

    fun renderLoadingView()

    fun hideLoadingView()
}
