package com.example.parcial2_mobile2

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.view.LayoutInflater

class Adapter(private val characters: MutableList<Result>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var onClickListener: (Result) -> Unit

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val imageViewCharacter: ImageView = view.findViewById(R.id.imageViewCharacter)
        private val tvName: TextView = view.findViewById(R.id.tvCharacterName)

        fun bind(character: Result) {
            tvName.text = character.name
            Picasso.get()
                .load(character.image)
                .resize(500, 500)
                .centerCrop()
                .into(imageViewCharacter)

            itemView.setOnClickListener {
                onClickListener(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }
}