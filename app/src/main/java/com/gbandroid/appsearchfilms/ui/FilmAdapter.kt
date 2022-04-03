package com.gbandroid.appsearchfilms.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gbandroid.appsearchfilms.R
import com.gbandroid.appsearchfilms.domain.CardsSource
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity

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

    override fun getItemCount() = dataSource.size()

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

        fun setData(cardFilm: TheMovieDBRepoEntity) {
            setImageOnView(cardFilm.getImageUrl())
            name.text = cardFilm.title
            year.text = cardFilm.getYear()
            rating.text = cardFilm.voteAverage.toString()
        }

        init {
            image = itemView.findViewById(R.id.cover_image_view)
            name = itemView.findViewById(R.id.name_description_text_view)
            year = itemView.findViewById(R.id.year_text_view)
            rating = itemView.findViewById(R.id.rating_text_view)
            image.setOnClickListener { v ->
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(v, adapterPosition)
                }
            }
        }

        private fun setImageOnView(imageUrl: String) {
            Glide.with(itemView)
                .load(imageUrl)
                .into(image)
        }
    }
}