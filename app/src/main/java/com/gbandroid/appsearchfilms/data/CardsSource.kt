package com.gbandroid.appsearchfilms.data

interface CardsSource {
    fun getCardFilm(position: Int): CardFilm
    fun size(): Int

}