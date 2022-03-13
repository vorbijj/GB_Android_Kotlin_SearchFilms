package com.gbandroid.appsearchfilms.domain

interface TheMovieDBRepoCase {
    fun getReposForFilmAsync(
        filmId: Long,
        onSuccess: (TheMovieDBRepoEntity) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun getListFilmsAsync(
        nameFilmsKit: String,
        onSuccess: (ListFilms) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun getGenreListFilmsAsync(
        genre: String,
        onSuccess: (ListFilms) -> Unit,
        onError: (Throwable) -> Unit
    )

}