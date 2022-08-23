package com.gbandroid.appsearchfilms.domain

import com.google.gson.annotations.SerializedName

private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

data class TheMovieDBRepoEntity(
    @SerializedName("title")
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("id")
    val id: Long,
    @SerializedName("poster_path")
    val posterPath: String
) {
    fun getYear(): String {
        return if (releaseDate == "") {
            "n/a"
        } else {
            this.releaseDate.substring(0..3)
        }
    }

    fun getImageUrl() = IMAGE_URL + this.posterPath
}

data class ListFilms(
    @SerializedName("results")
    val results: ArrayList<TheMovieDBRepoEntity>
) {
    fun size() = results.size
}
