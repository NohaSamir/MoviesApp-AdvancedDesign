package com.noha.moviesadvanced.presentaion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noha.moviesadvanced.databinding.ItemActorBinding
import com.noha.moviesadvanced.domain.model.Actor

class ActorsAdapter(private val list: List<Actor>) :
    RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActorViewHolder(
            ItemActorBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ActorViewHolder
    constructor(
        private val binding: ItemActorBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Actor) = with(itemView) {
            binding.actor = item
            binding.executePendingBindings()
        }
    }
}

