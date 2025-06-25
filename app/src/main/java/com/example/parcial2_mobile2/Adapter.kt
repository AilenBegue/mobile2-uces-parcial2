package com.example.parcial2_mobile2

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import java.nio.charset.CharsetEncoder

class Adapter(private val characters: MutableList<Characters>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var onItemClickListener: (Characters) -> Unit

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val ivCharacter: ImageView = view.findViewById(R.id.ivCharacter)
        private val ivName: TextView = view.findViewById(R.id.tvName)

        fun bind(characters: Characters) {
            ivName.text = "Nombre"

        }

        fun bind(image: String) {
            Picasso.get()
                .load(image)
                .resize(500, 500)
                .centerCrop()
                .into(ivCharacter)
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
        val quake = characters[position]
        holder.bind(quake)
    }
}