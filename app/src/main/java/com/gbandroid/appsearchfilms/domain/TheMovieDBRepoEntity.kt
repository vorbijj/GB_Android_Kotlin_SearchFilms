package com.gbandroid.appsearchfilms.domain

import com.google.gson.annotations.SerializedName

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
    val id: Long
) {
    fun getYear() = this.releaseDate.substring(0..3)
}
