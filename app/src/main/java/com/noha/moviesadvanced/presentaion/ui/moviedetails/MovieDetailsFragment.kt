package com.noha.moviesadvanced.presentaion.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.noha.moviesadvanced.data.source.repository.moviesRepository
import com.noha.moviesadvanced.databinding.MovieDetailsFragmentBinding
import com.noha.moviesadvanced.presentaion.util.showErrorSnackBar

class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModel.Factory(args.movie, moviesRepository)
    }

    private lateinit var mBinding: MovieDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        mBinding.viewModel = viewModel
        mBinding.imageOneUrl = args.imageOne
        mBinding.imageTwoUrl = args.imageTwo
        mBinding.lifecycleOwner = this

        viewModel.error.observe(viewLifecycleOwner, {
            showErrorSnackBar(mBinding.root, it)
        })
        return mBinding.root
    }
}