package com.gbandroid.appsearchfilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbandroid.appsearchfilms.data.CardFilm
import com.gbandroid.appsearchfilms.data.CardsSource
import com.gbandroid.appsearchfilms.data.CardsSourceImpl

class MainViewModel : ViewModel() {
    val CardLiveData = MutableLiveData<CardFilm>()

    fun getCardsSource(): CardsSource{
        return CardsSourceImpl()
    }

    fun setCurrentCard(position: Int) {
        val card = CardsSourceImpl().getCardFilm(position)
        val cardFilm = CardFilm(card.picture, card.name, card.year, card.rating)
        CardLiveData.postValue(card)
    }
}