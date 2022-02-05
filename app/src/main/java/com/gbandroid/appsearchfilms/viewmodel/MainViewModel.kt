package com.gbandroid.appsearchfilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbandroid.appsearchfilms.data.RetrofitFilmRepoCaseImpl
import com.gbandroid.appsearchfilms.domain.CardsSourceImpl
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoCase
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity

class MainViewModel : ViewModel() {
    private val CardLiveData = MutableLiveData<TheMovieDBRepoEntity>()
    private val OnErrorLiveData = MutableLiveData<String>()
    val validationErrorLiveData = MutableLiveData<Boolean>()

    private val theMovieDBRepoCase: TheMovieDBRepoCase by lazy { RetrofitFilmRepoCaseImpl() }

    fun getCardsSource() = CardsSourceImpl()

    fun setCurrentCard(position: Int) {
        val card = CardsSourceImpl().getCardFilm(position)

        theMovieDBRepoCase.getReposForFilmAsync(
            card.id,
            onSuccess = { currentCardFilm ->
                CardLiveData.postValue(currentCardFilm)
                validationErrorLiveData.postValue(false)
            },
            onError = { thr ->
                OnErrorLiveData.postValue(thr.toString())
                validationErrorLiveData.postValue(true)
            }
        )
    }

    fun getCurrentCard() = CardLiveData
    fun getOnErrorLiveData() = OnErrorLiveData
}