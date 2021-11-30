package com.gbandroid.appsearchfilms.data

interface CardsSource {
    fun getCardData(position: Int): CardData
    fun size(): Int

}