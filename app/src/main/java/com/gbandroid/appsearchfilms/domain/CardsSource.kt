package com.gbandroid.appsearchfilms.domain

interface CardsSource {
    fun getCardFilm(position: Int): CardFilm
    fun size(): Int

}