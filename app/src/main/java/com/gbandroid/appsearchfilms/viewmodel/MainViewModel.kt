package com.gbandroid.appsearchfilms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbandroid.appsearchfilms.data.RetrofitFilmRepoCaseImpl
import com.gbandroid.appsearchfilms.domain.CardsSource
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

    private val _topValidationErrorLiveData = MutableLiveData<Boolean>()
    val topValidationErrorLiveData: LiveData<Boolean>
        get() = _topValidationErrorLiveData

    private val _newValidationErrorLiveData = MutableLiveData<Boolean>()
    val newValidationErrorLiveData: LiveData<Boolean>
        get() = _newValidationErrorLiveData

    private val _comingSoonValidationErrorLiveData = MutableLiveData<Boolean>()
    val comingSoonValidationErrorLiveData: LiveData<Boolean>
        get() = _comingSoonValidationErrorLiveData

    private val _fantasyValidationErrorLiveData = MutableLiveData<Boolean>()
    val fantasyValidationErrorLiveData: LiveData<Boolean>
        get() = _fantasyValidationErrorLiveData

    private val _dramaValidationErrorLiveData = MutableLiveData<Boolean>()
    val dramaValidationErrorLiveData: LiveData<Boolean>
        get() = _dramaValidationErrorLiveData

    private val _topListFilmsLiveData = MutableLiveData<CardsSource>()
    val topListFilmsLiveData: LiveData<CardsSource>
        get() = _topListFilmsLiveData

    private val _newListFilmsLiveData = MutableLiveData<CardsSource>()
    val newListFilmsLiveData: LiveData<CardsSource>
        get() = _newListFilmsLiveData

    private val _comingSoonListFilmsLiveData = MutableLiveData<CardsSource>()
    val comingSoonListFilmsLiveData: LiveData<CardsSource>
        get() = _comingSoonListFilmsLiveData

    private val _fantasyListFilmsLiveData = MutableLiveData<CardsSource>()
    val fantasyListFilmsLiveData: LiveData<CardsSource>
        get() = _fantasyListFilmsLiveData

    private val _dramaListFilmsLiveData = MutableLiveData<CardsSource>()
    val dramaListFilmsLiveData: LiveData<CardsSource>
        get() = _dramaListFilmsLiveData

    private val theMovieDBRepoCase: TheMovieDBRepoCase by lazy { RetrofitFilmRepoCaseImpl() }

    fun setCurrentCard(position: Int, filmsKit: ListFilmsKit) {
        val currentCardFilm = when (filmsKit) {
            ListFilmsKit.LIST_TOP -> _topListFilmsLiveData.value!!.getCardFilm(position)
            ListFilmsKit.LIST_NEW -> _newListFilmsLiveData.value!!.getCardFilm(position)
            ListFilmsKit.LIST_COMING_SOON -> _comingSoonListFilmsLiveData.value!!.getCardFilm(
                position
            )
            ListFilmsKit.LIST_FANTASY -> _fantasyListFilmsLiveData.value!!.getCardFilm(position)
            ListFilmsKit.LIST_DRAMA -> _dramaListFilmsLiveData.value!!.getCardFilm(position)
        }
        _cardLiveData.postValue(currentCardFilm)
    }

    fun getTopListFilms(preference: Boolean) {
        theMovieDBRepoCase.getListFilmsAsync(
            ListFilmsKit.LIST_TOP.value,
            preference,
            onSuccess = { listFilms ->
                val films = CardsSourceImpl(listFilms)
                _topListFilmsLiveData.postValue(films.sortListTop())
                _topValidationErrorLiveData.postValue(false)
            },
            onError = { thr ->
                _onErrorLiveData.postValue(thr.toString())
                _topValidationErrorLiveData.postValue(true)
            }
        )
    }

    fun getNewListFilms(preference: Boolean) {
        theMovieDBRepoCase.getListFilmsAsync(
            ListFilmsKit.LIST_NEW.value,
            preference,
            onSuccess = { listFilms ->
                val films = CardsSourceImpl(listFilms)
                _newListFilmsLiveData.postValue(films)
                _newValidationErrorLiveData.postValue(false)
            },
            onError = { thr ->
                _onErrorLiveData.postValue(thr.toString())
                _newValidationErrorLiveData.postValue(true)
            }
        )
    }

    fun getComingSoonListFilms(preference: Boolean) {
        theMovieDBRepoCase.getListFilmsAsync(
            ListFilmsKit.LIST_COMING_SOON.value,
            preference,
            onSuccess = { listFilms ->
                val films = CardsSourceImpl(listFilms)
                _comingSoonListFilmsLiveData.postValue(films.sortListComingSoon())
                _comingSoonValidationErrorLiveData.postValue(false)
            },
            onError = { thr ->
                _onErrorLiveData.postValue(thr.toString())
                _comingSoonValidationErrorLiveData.postValue(true)
            }
        )
    }

    fun getFantasyListFilms(preference: Boolean) {
        theMovieDBRepoCase.getGenreListFilmsAsync(
            ListFilmsKit.LIST_FANTASY.value,
            preference,
            onSuccess = { listFilms ->
                val films = CardsSourceImpl(listFilms)
                _fantasyListFilmsLiveData.postValue(films)
                _fantasyValidationErrorLiveData.postValue(false)
            },
            onError = { thr ->
                _onErrorLiveData.postValue(thr.toString())
                _fantasyValidationErrorLiveData.postValue(true)
            }
        )
    }

    fun getDramaListFilms(preference: Boolean) {
        theMovieDBRepoCase.getGenreListFilmsAsync(
            ListFilmsKit.LIST_DRAMA.value,
            preference,
            onSuccess = { listFilms ->
                val films = CardsSourceImpl(listFilms)
                _dramaListFilmsLiveData.postValue(films)
                _dramaValidationErrorLiveData.postValue(false)
            },
            onError = { thr ->
                _onErrorLiveData.postValue(thr.toString())
                _dramaValidationErrorLiveData.postValue(true)
            }
        )
    }
}

enum class ListFilmsKit(val value: String) {
    LIST_TOP("popular"),
    LIST_NEW("now_playing"),
    LIST_COMING_SOON("upcoming"),
    LIST_FANTASY("14"),
    LIST_DRAMA("18")
}