package com.noha.moviesadvanced.presentaion.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.noha.moviesadvanced.databinding.ItemMovieBinding
import com.noha.moviesadvanced.domain.model.Movie


class MovieAdapter(
    private val interaction: Interaction? = null
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    /**
     * We use DiffUtil to avoid use notifyDataSetChanged
     * notifyDataSetChanged() tells RecyclerView that the entire list is potentially invalid. As a result, RecyclerView rebinds and redraws every item in the list, including items that are not visible on screen. This is a lot of unnecessary work.
     * https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding#2
     */
    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemMovieBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return MovieViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MovieViewHolder
    constructor(
        private val binding: ItemMovieBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Movie) = with(itemView) {
            binding.movie = item
            binding.executePendingBindings()

            //Notify the listener on movie item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item, binding)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, movie: Movie, viewBinding: ItemMovieBinding)
    }

    fun displayMovieDetails(binding: ItemMovieBinding, show: Boolean) {
        //Hide Image
        val params = binding.posterGuideline.layoutParams as ConstraintLayout.LayoutParams
        if (show) params.guidePercent = 0.0f
        else params.guidePercent = 0.6f
        binding.posterGuideline.layoutParams = params

        //Display Movie Details
        binding.isDetailsVisible = show

        //ToDo: Scale white board
    }

    fun bindMissingData(movie: Movie, viewBinding: ItemMovieBinding) {
        viewBinding.movie = movie

        movie.actors?.let {
            //Bind Actors list and enable nested scroll
            viewBinding.detailsView.actorsRecyclerView.adapter = ActorsAdapter(it)
            viewBinding.detailsView.actorsRecyclerView.addOnItemTouchListener(object :
                RecyclerView.OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                }

            })
        }
    }
}

