package com.karel.movieapp.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.karel.movieapp.R
import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.database.MovieDatabase
import com.karel.movieapp.data.repository.MovieRepositoryImpl
import com.karel.movieapp.databinding.ActivityMovieListBinding
import com.karel.movieapp.domain.usecase.*
import com.karel.movieapp.presentation.detail.MovieDetailFragment


class MovieListActivity : AppCompatActivity(), IViewMainActivity {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
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
        (binding.movieListMovies.adapter as? MovieAdapter)?.addItems(
            movies,
            viewModel.isLastPage,
            viewModel.isEmptyState.value == true
        )
    }

    override fun renderErrorView(error: String?) {
        Snackbar.make(
            binding.movieListMovies,
            error ?: getString(R.string.default_error_message),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun renderEmptyState() {
        binding.movieListEmptyState.root.isVisible = true
        Log.d("movieListEmptyState", "renderEmptyState ${binding.movieListEmptyState.emptyListText.isVisible}")
    }

    override fun hideEmptyState() {
        binding.movieListEmptyState.root.isVisible = false
        Log.d("movieListEmptyState", "hideEmptyState ${binding.movieListEmptyState.emptyListText.isVisible}")
    }

    private fun initializeView() {
        initializeSearchView()
        initializeMoviesView()
        createViewModel()
        observeViewModel()
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

    private fun initializeMoviesView() {
        binding.movieListMovies.layoutManager = LinearLayoutManager(this)
        binding.movieListMovies.adapter = MovieAdapter { id ->
            MovieDetailFragment.show(supportFragmentManager, id)
        }
        binding.movieListMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            }
        })

        viewModel.isEmptyState.observe(this, Observer { value ->
            if (value) {
                renderEmptyState()
            } else {
                hideEmptyState()
            }
        })

        viewModel.searchTerm.observe(this, Observer { value ->
            binding.searchView.setQuery(value, false)
        })

        viewModel.scrollPosition.observe(this, Observer { value ->
            binding.movieListMovies.scrollToPosition(value)
        })
    }
}

