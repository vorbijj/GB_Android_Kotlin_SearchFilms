package com.gbandroid.appsearchfilms.domain

data class TheMovieDBRepoEntity(
    val title: String,
    val original_title: String,
    val overview: String,
    val release_date: String,
    val vote_average: Float,
    val id: Long
) {
    fun getYear() = this.release_date.substring(0..3)
}
