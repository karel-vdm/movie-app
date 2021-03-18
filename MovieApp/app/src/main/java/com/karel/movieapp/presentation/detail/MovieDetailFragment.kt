package com.karel.movieapp.presentation.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karel.movieapp.data.api.MovieService
import com.karel.movieapp.data.database.MovieDatabase
import com.karel.movieapp.data.repository.MovieRepositoryImpl
import com.karel.movieapp.databinding.MovieDetailBinding
import com.karel.movieapp.databinding.MovieDetailShimerBinding
import com.karel.movieapp.domain.usecase.UseCaseGetMovieById

class MovieDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: MovieDetailBinding
    private lateinit var shimmerBinding: MovieDetailShimerBinding

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        binding = MovieDetailBinding.inflate(layoutInflater, viewGroup, false)
        shimmerBinding = binding.shimmerInclude
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val database = MovieDatabase.getDatabase(context)
        val viewModelFactory = MovieDetailViewModelFactory(
            useCaseGetMovieById = UseCaseGetMovieById(
                MovieRepositoryImpl(
                    MovieService.create(),
                    database.movieDao()
                )
            )
        )

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailViewModel::class.java)

        val id = getMovieIdFromArgs()
        viewModel.getMovieById(id)
        viewModel.movie.observe(this, Observer { response ->
            renderMovieDetail(response)
        })
        viewModel.loading.observe(this, Observer { value ->
            shimmerBinding.root.isVisible = value
        })


    }

    override fun onStart() {
        super.onStart()
        val bottomSheetBehavior = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun renderMovieDetail(viewModel: MovieDetailItemViewModel) {
        val context = context
        if (context != null) {
            binding.contentGroup.isVisible = true
            binding.movieItemTitle.text = viewModel.title
            binding.movieItemYear.text = viewModel.year
            binding.movieItemAgeRestriction.text = viewModel.ageRestriction
            binding.movieItemRuntime.text = viewModel.runtime
            binding.movieItemRating.text = viewModel.rating
            binding.movieItemPlot.text = viewModel.plot

            Glide.with(context)
                .load(viewModel.poster)
                .fitCenter()
                .into(binding.movieItemPoster)
        }
    }

    private fun getMovieIdFromArgs(): String {
        return arguments?.getString(MOVIE_ID) ?: ""
    }

    companion object {

        private const val TAG = "MovieDetailFragment"
        private const val MOVIE_ID = "movie_id"

        fun show(fragmentManager: FragmentManager, id: String) {
            val barcodeResultFragment = MovieDetailFragment()
            barcodeResultFragment.arguments = Bundle().apply {
                putString(MOVIE_ID, id)
            }
            barcodeResultFragment.show(fragmentManager, TAG)
        }

        fun dismiss(fragmentManager: FragmentManager) {
            (fragmentManager.findFragmentByTag(TAG) as? MovieDetailFragment)?.dismiss()
        }
    }
}