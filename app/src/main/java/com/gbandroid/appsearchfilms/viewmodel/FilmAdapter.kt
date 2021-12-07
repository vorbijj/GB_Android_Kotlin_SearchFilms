package com.gbandroid.appsearchfilms.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.data.CardFilm
import com.gbandroid.appsearchfilms.data.CardsSource

class FilmAdapter(private val dataSource: CardsSource) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setData(dataSource.getCardFilm(position))
    }

    override fun getItemCount(): Int {
        return dataSource.size()
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: AppCompatImageView
        private val name: TextView
        private val year: TextView
        private val rating: TextView

        fun setData(cardFilm: CardFilm) {
            image.setImageResource(R.drawable.ic_baseline_camera_75)
            name.text = cardFilm.name
            year.text = cardFilm.year
            rating.text = cardFilm.rating
        }

        init {
            image = itemView.findViewById(R.id.cover_image_view)
            name = itemView.findViewById(R.id.name_text_view)
            year = itemView.findViewById(R.id.year_text_view)
            rating = itemView.findViewById(R.id.rating_text_view)
            image.setOnClickListener { v ->
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(v, adapterPosition)
                }
            }
        }
    }
}