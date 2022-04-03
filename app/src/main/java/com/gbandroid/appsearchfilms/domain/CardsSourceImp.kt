package com.gbandroid.appsearchfilms.domain

class CardsSourceImpl(private val dataSource: ListFilms) : CardsSource {

    override fun getCardFilm(position: Int) = dataSource.results[position]

    override fun size() = dataSource.size()

    fun sortListTop(): CardsSource {
        val lst = ArrayList<TheMovieDBRepoEntity>()

        for (i in 0..9) {
            lst.add(dataSource.results[i])
        }
        return CardsSourceImpl(ListFilms(lst))
    }

    fun sortListComingSoon(): CardsSource {
        val lst = ArrayList<TheMovieDBRepoEntity>()

        for (i in 0..9) {
            if (dataSource.results[i].getYear() == "2022") {
                lst.add(dataSource.results[i])
            }
        }
        return CardsSourceImpl(ListFilms(lst))
    }
}