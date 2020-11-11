package com.noha.moviesadvanced.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.noha.moviesadvanced.R
import com.noha.moviesadvanced.adapter.MovieAdapter
import com.noha.moviesadvanced.databinding.ActivityMainBinding
import com.noha.moviesadvanced.databinding.ItemMovieBinding
import com.noha.moviesadvanced.model.Movie
import com.noha.moviesadvanced.model.getDummyListOfMovies
import com.noha.moviesadvanced.util.CenterZoomLayoutManager
import com.noha.moviesadvanced.util.loadImage

class MainActivity : AppCompatActivity(), MovieAdapter.Interaction {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var layoutManager: CenterZoomLayoutManager
    private lateinit var adapter: MovieAdapter
    private var lastVisibleItemWhiteBoarder: ConstraintLayout? = null
    private var lastSelectedItemBinding: ItemMovieBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        layoutManager = CenterZoomLayoutManager(this)
        mainBinding.recyclerView.layoutManager = layoutManager

        adapter = MovieAdapter(getDummyListOfMovies(), this)
        mainBinding.recyclerView.adapter = adapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(mainBinding.recyclerView)

        mainBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visiblePosition: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (visiblePosition > -1) {
                    val visibleView: View? = layoutManager.findViewByPosition(visiblePosition)

                    visibleView?.let {
                        onFocusedItemChange(getDummyListOfMovies()[visiblePosition], it)
                    }
                }
            }
        })
    }

    override fun onItemSelected(position: Int, item: Movie, binding: ItemMovieBinding) {
        //Hide details when click on another item
        lastSelectedItemBinding?.let { displayMovieDetails(it, false) }

        //Display selected item details
        if (lastSelectedItemBinding != binding)
            displayMovieDetails(binding, true)
    }

    private fun onFocusedItemChange(movie: Movie, view: View) {
        //change card boarder color
        changeTransparentOfFocusedItem(view)

        //Change background image
        loadImage(mainBinding.backgroundImageView, movie.poster)
    }

    private fun displayMovieDetails(binding: ItemMovieBinding, show: Boolean) {
        //Hide Image
        val params = binding.posterGuideline.layoutParams as ConstraintLayout.LayoutParams
        if (show) params.guidePercent = 0.0f
        else params.guidePercent = 0.6f
        binding.posterGuideline.layoutParams = params

        //Display Movie Details
        binding.isDetailsVisible = show

        //ToDo: Scale white board

        lastSelectedItemBinding = binding
    }

    private fun changeTransparentOfFocusedItem(view: View) {

        val whiteImageView = view.findViewById<ConstraintLayout>(R.id.containerLayout)
        whiteImageView.setBackgroundResource(R.drawable.round_rectangle_white)
        lastVisibleItemWhiteBoarder?.setBackgroundResource(R.drawable.round_rectangle_white_transparent)

        lastVisibleItemWhiteBoarder = whiteImageView
    }


}