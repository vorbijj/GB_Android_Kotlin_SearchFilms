package com.gbandroid.appsearchfilms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbandroid.appsearchfilms.data.RetrofitFilmRepoCaseImpl
import com.gbandroid.appsearchfilms.domain.CardsSourceImpl
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoCase
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity

class MainViewModel : ViewModel() {
    private val _cardLiveData = MutableLiveData<TheMovieDBRepoEntity>()
    val cardLiveData: LiveData<TheMovieDBRepoEntity>
        get() = _cardLiveData

    private val _onErrorLiveData = MutableLiveData<String>()
    val onErrorLiveData: LiveData<String>
        get() = _onErrorLiveData

    private val _validationErrorLiveData = MutableLiveData<Boolean>()
    val validationErrorLiveData: LiveData<Boolean>
        get() = _validationErrorLiveData

    private val theMovieDBRepoCase: TheMovieDBRepoCase by lazy { RetrofitFilmRepoCaseImpl() }

    fun getCardsSource() = CardsSourceImpl()

    fun setCurrentCard(position: Int) {
        val card = CardsSourceImpl().getCardFilm(position)

        theMovieDBRepoCase.getReposForFilmAsync(
            card.id,
            onSuccess = { currentCardFilm ->
                _cardLiveData.postValue(currentCardFilm)
                _validationErrorLiveData.postValue(false)
            },
            onError = { thr ->
                _onErrorLiveData.postValue(thr.toString())
                _validationErrorLiveData.postValue(true)
            }
        )
    }
}