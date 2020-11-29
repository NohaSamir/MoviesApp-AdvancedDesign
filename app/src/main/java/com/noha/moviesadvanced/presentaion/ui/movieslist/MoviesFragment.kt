package com.noha.moviesadvanced.presentaion.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.noha.moviesadvanced.R
import com.noha.moviesadvanced.data.source.repository.moviesRepository
import com.noha.moviesadvanced.databinding.MoviesFragmentBinding
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.presentaion.adapter.MovieAdapter
import com.noha.moviesadvanced.presentaion.util.CenterZoomLayoutManager
import com.noha.moviesadvanced.presentaion.util.showErrorSnackBar


/* ToDo: FUNCTIONS SHOULD DO ONE THING. THEY SHOULD DO IT WELL. THEY SHOULD DO IT ONLY.*/

class MoviesFragment : Fragment(), MovieAdapter.Interaction {

    private lateinit var mainBinding: MoviesFragmentBinding
    private lateinit var layoutManager: CenterZoomLayoutManager
    private var lastVisibleItemWhiteBoarder: ConstraintLayout? = null

    private val viewModel: MoviesViewModel by viewModels {
        MoviesViewModel.Factory(moviesRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Bind view using data binding
        mainBinding = MoviesFragmentBinding.inflate(inflater, container, false)
        mainBinding.viewModel = viewModel
        mainBinding.lifecycleOwner = this

        setUpRecyclerView()

        //Bind Movie List
        viewModel.error.observe(viewLifecycleOwner, {
            showErrorSnackBar(mainBinding.root, it)
            viewModel.onErrorMsgDisplay()
        })

        return mainBinding.root
    }

    private fun setUpRecyclerView() {

        //Setup recyclerView
        context?.let {
            layoutManager = CenterZoomLayoutManager(it)
            mainBinding.recyclerView.layoutManager = layoutManager
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(mainBinding.recyclerView)

        val adapter = MovieAdapter(interaction = this)
        mainBinding.recyclerView.adapter = adapter

        mainBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visiblePosition: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (visiblePosition > -1) {
                    val visibleView: View? = layoutManager.findViewByPosition(visiblePosition)

                    visibleView?.let {
                        viewModel.onCurrentMovieChanged(visiblePosition)
                        changeTransparentOfFocusedItem(visibleView)
                    }
                }
            }
        })
    }

    private fun changeTransparentOfFocusedItem(view: View) {

        val whiteImageView = view.findViewById<ConstraintLayout>(R.id.containerLayout)
        whiteImageView.setBackgroundResource(R.drawable.round_rectangle_white)
        lastVisibleItemWhiteBoarder?.setBackgroundResource(R.drawable.round_rectangle_white_transparent)

        lastVisibleItemWhiteBoarder = whiteImageView
    }

    override fun onItemSelected(
        movie: Movie,
        previousMoviePoster: String,
        nextMoviePoster: String
    ) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
            movie,
            previousMoviePoster,
            nextMoviePoster
        )
        findNavController().navigate(action)
    }
}