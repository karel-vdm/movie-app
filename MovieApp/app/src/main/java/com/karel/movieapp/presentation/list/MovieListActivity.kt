package com.karel.movieapp.presentation.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.database.MovieDatabase
import com.karel.movieapp.data.repository.MovieRepositoryImpl
import com.karel.movieapp.databinding.ActivityMainBinding
import com.karel.movieapp.domain.usecase.*
import com.karel.movieapp.presentation.detail.MovieDetailFragment

private const val DEFAULT_ERROR_MESSAGE = "An error has occurred, please try again"

class MovieListActivity : AppCompatActivity(), IViewMainActivity {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeView()
        viewModel.onViewCreated()
    }

    override fun onPause() {
        viewModel.onPause()
        super.onPause()
    }

    override fun addMoviesToView(movies: List<MovieListItemViewModel>) {
        Log.d("scrolled", "isLastPage ${viewModel.isLastPage}")
        (binding.moviesView.adapter as? MovieAdapter)?.addItems(movies, viewModel.isLastPage)
    }

    override fun clearMoviesFromView() {
    }

    override fun renderErrorView(error: String?) {
        Snackbar.make(binding.moviesView, error ?: DEFAULT_ERROR_MESSAGE, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun hideErrorView() {
    }

    override fun renderLoadingView() {
    }

    override fun hideLoadingView() {
    }

    private fun initializeView() {
        initializeSearchView()
        initializeMoviesView()
        createViewModel()
        observeViewModel()
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
                    val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    viewModel.onScroll(currentPosition)
                }
            }
        })
    }

    private fun initializeSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getMovies(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun createViewModel() {
        val database = MovieDatabase.getDatabase(this)
        val repository = MovieRepositoryImpl(
            MovieService.create(),
            database.movieDao()
        )

        val viewModelFactory = MovieListViewModelFactory(
            useCaseGetMovies = UseCaseGetMovies(
                repository = repository
            ),
            useCaseCacheCurrentState = UseCaseCacheCurrentState(
                repository = repository
            ),
            useCaseClearSavedState = UseCaseClearSavedState(
                repository = repository
            ),
            useCaseGetSavedState = UseCaseGetSavedState(
                repository = repository
            ),
            useCaseGetMoviesFromCache = UseCaseGetMoviesFromCache(
                repository = repository
            )
        )

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieListViewModel::class.java)
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

        viewModel.searchTerm.observe(this, Observer { value ->
            binding.searchView.setQuery(value, false)
        })

        viewModel.scrollPosition.observe(this, Observer { value ->
            binding.moviesView.scrollToPosition(value)
        })
    }
}

