package com.noha.moviesadvanced.presentaion.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.noha.moviesadvanced.R
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper
import com.noha.moviesadvanced.data.source.repository.moviesRepository
import com.noha.moviesadvanced.databinding.ActivityMainBinding
import com.noha.moviesadvanced.databinding.ItemMovieBinding
import com.noha.moviesadvanced.domain.model.Movie
import com.noha.moviesadvanced.presentaion.adapter.MovieAdapter
import com.noha.moviesadvanced.presentaion.util.CenterZoomLayoutManager
import com.noha.moviesadvanced.presentaion.util.loadImage
import com.noha.moviesadvanced.presentaion.util.showErrorSnackBar


/* ToDo: FUNCTIONS SHOULD DO ONE THING. THEY SHOULD DO IT WELL. THEY SHOULD DO IT ONLY.*/

class MainActivity : AppCompatActivity(), MovieAdapter.Interaction {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var layoutManager: CenterZoomLayoutManager
    private lateinit var adapter: MovieAdapter
    private var lastVisibleItemWhiteBoarder: ConstraintLayout? = null
    private var lastSelectedItemBinding: ItemMovieBinding? = null

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(moviesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Bind view using data binding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Setup recyclerView
        layoutManager = CenterZoomLayoutManager(this)
        mainBinding.recyclerView.layoutManager = layoutManager

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(mainBinding.recyclerView)

        adapter = MovieAdapter(interaction = this)
        mainBinding.recyclerView.adapter = adapter

        //Bind Movie List
        viewModel.moviesResponse.observe(this, {
            if (it is ResponseWrapper.Success) {
                bindMovieList(it.value)
            } else {
                showErrorSnackBar(mainBinding.root, it)
            }
        })

        viewModel.selectedMovieDetails.observe(this, { movieDetailsResponse ->
            //if movie details still visible bind it's details
            lastSelectedItemBinding?.let {
                if (movieDetailsResponse is ResponseWrapper.Success) {
                    adapter.bindMissingData(movieDetailsResponse.value, lastSelectedItemBinding!!)
                } else {
                    showErrorSnackBar(mainBinding.root, movieDetailsResponse)
                }
            }
        })
    }

    private fun bindMovieList(movies: List<Movie>) {
        adapter.submitList(movies)
        mainBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visiblePosition: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (visiblePosition > -1) {
                    val visibleView: View? = layoutManager.findViewByPosition(visiblePosition)

                    visibleView?.let {
                        onFocusedItemChange(movies[visiblePosition], it)
                    }
                }
            }
        })
    }

    override fun onItemSelected(position: Int, movie: Movie, viewBinding: ItemMovieBinding) {

        //Get movie actors and director
        viewModel.getMovieDetails(movie)

        //Display movie details UI
        adapter.displayMovieDetails(viewBinding, true)

        //Disable recycler view on movie details appear to avoid bind in wrong view
        layoutManager.setScrollEnabled(false)

        lastSelectedItemBinding = viewBinding
    }

    override fun onBackPressed() {
        if (lastSelectedItemBinding != null) {
            adapter.displayMovieDetails(lastSelectedItemBinding!!, false)
            layoutManager.setScrollEnabled(true)
            lastSelectedItemBinding = null
            return
        }
        super.onBackPressed()
    }

    private fun onFocusedItemChange(movie: Movie, view: View) {
        //change card boarder color
        changeTransparentOfFocusedItem(view)

        //Change background image
        changeBackgroundImage(movie)

    }

    private fun changeTransparentOfFocusedItem(view: View) {

        val whiteImageView = view.findViewById<ConstraintLayout>(R.id.containerLayout)
        whiteImageView.setBackgroundResource(R.drawable.round_rectangle_white)
        lastVisibleItemWhiteBoarder?.setBackgroundResource(R.drawable.round_rectangle_white_transparent)

        lastVisibleItemWhiteBoarder = whiteImageView
    }

    private fun changeBackgroundImage(movie: Movie) {
        loadImage(mainBinding.backgroundImageView, movie.backdropPath)
    }
}