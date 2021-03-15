package com.karel.movieapp.presentation.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karel.movieapp.databinding.MovieDetailBinding

class MovieDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: MovieDetailBinding

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        binding = MovieDetailBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

        val id = getMovieIdFromArgs()
        viewModel.getMovieById(id)
        viewModel.movie.observe(this, Observer { response ->
            renderMovieDetail(response)
        })
    }

    private fun renderMovieDetail(viewModel: MovieViewModel) {
        val context = context
        if (context != null) {
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