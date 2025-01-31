package com.anacaballero.disney.presentation.main.adapter

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anacaballero.disney.domain.entities.Character
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anacaballero.disney.R
import com.anacaballero.disney.databinding.ItemViewCharacterBinding
import com.bumptech.glide.Glide

class CharactersListAdapter : ListAdapter<Character, CharactersListAdapter.CharacterViewHolder>(ListAdapterCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewCharacterBinding.inflate(layoutInflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(
        private val binding: ItemViewCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.textName.text = character.name
            binding.textMovies.text = character.multimedia.joinToString()
            binding.textGames.text = character.videoGames.joinToString(" y ")
            binding.textGames.movementMethod = ScrollingMovementMethod()
            Glide.with(binding.root)
                .load(character.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imageCharacter)
        }
    }

    class ListAdapterCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}