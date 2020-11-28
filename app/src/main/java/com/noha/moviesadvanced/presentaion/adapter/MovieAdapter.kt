package com.noha.moviesadvanced.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
        val movie = getItem(position)
        val previousMovie = if (position == 0) getItem(itemCount - 1) else getItem(position - 1)
        val nextMovie = if (position == itemCount - 1) getItem(0) else getItem(position + 1)

        holder.bind(movie, previousMovie, nextMovie)
    }

    class MovieViewHolder
    constructor(
        private val binding: ItemMovieBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(movie: Movie, previousMovie: Movie, nextMovie: Movie) = with(itemView) {
            binding.movie = movie
            binding.executePendingBindings()

            //Notify the listener on movie item click
            itemView.setOnClickListener {
                interaction?.onItemSelected(movie, previousMovie.poster, nextMovie.poster)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(movie: Movie, previousMoviePoster: String, nextMoviePoster: String)
    }
}

