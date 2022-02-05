package com.gbandroid.appsearchfilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbandroid.appsearchfilms.data.WebFilmRepoCaseImpl
import com.gbandroid.appsearchfilms.domain.CardsSourceImpl
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoCase
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity

class MainViewModel : ViewModel() {
    private val CardLiveData = MutableLiveData<TheMovieDBRepoEntity>()
    private val OnErrorLiveData = MutableLiveData<String>()
    val validationErrorLiveData = MutableLiveData<Boolean>()

    private val theMovieDBRepoCase: TheMovieDBRepoCase by lazy { WebFilmRepoCaseImpl() }

    fun getCardsSource() = CardsSourceImpl()

    fun setCurrentCard(position: Int) {
        val card = CardsSourceImpl().getCardFilm(position)
        Thread {
            try {
                val currentCardFilm = theMovieDBRepoCase.getReposForFilmSync(card.id)
                CardLiveData.postValue(currentCardFilm)
                validationErrorLiveData.postValue(false)
            } catch (thr: Throwable) {
                OnErrorLiveData.postValue(thr.toString())
                validationErrorLiveData.postValue(true)
            }
        }.start()
    }

    fun getCurrentCard() = CardLiveData
    fun getOnErrorLiveData() = OnErrorLiveData
}