package com.gbandroid.appsearchfilms.domain

interface CardsSource {
    fun getCardFilm(position: Int): TheMovieDBRepoEntity
    fun size(): Int
}