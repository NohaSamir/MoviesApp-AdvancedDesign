package com.noha.moviesadvanced.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noha.moviesadvanced.model.Movie
import com.noha.moviesadvanced.databinding.ItemMovieBinding


class MovieAdapter(
    private val list: List<Movie>,
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MovieViewHolder
    constructor(
        private val binding: ItemMovieBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        private var lastSelectedItemBinding: ItemMovieBinding? = null

        fun bind(item: Movie) = with(itemView) {
            binding.movie = item
            binding.executePendingBindings()

            //Bind Actors list and enable nested scroll
            binding.detailsView.actorsRecyclerView.adapter = ActorsAdapter(item.actors)
            binding.detailsView.actorsRecyclerView.addOnItemTouchListener(object :
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

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item, binding)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Movie, binding: ItemMovieBinding)
    }
}

