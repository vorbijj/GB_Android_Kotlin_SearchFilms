package com.gbandroid.appsearchfilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbandroid.appsearchfilms.domain.CardsSourceImpl
import com.gbandroid.appsearchfilms.data.WebFilmRepoCaseImpl
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoCase
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity

class MainViewModel : ViewModel() {
    val CardLiveData = MutableLiveData<TheMovieDBRepoEntity>()
    private val theMovieDBRepoCase: TheMovieDBRepoCase by lazy { WebFilmRepoCaseImpl() }

    fun getCardsSource() = CardsSourceImpl()

    fun setCurrentCard(position: Int) {
        val card = CardsSourceImpl().getCardFilm(position)

        theMovieDBRepoCase.getReposForFilmAsync(card.id) { currentCardFilm ->
            CardLiveData.postValue(currentCardFilm)
        }
    }

    fun getCurrentCard() = CardLiveData
}