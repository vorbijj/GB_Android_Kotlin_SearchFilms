package com.gbandroid.appsearchfilms.domain

interface TheMovieDBRepoCase {
    fun getReposForFilmAsync(
        filmId: Long,
        onSuccess: (TheMovieDBRepoEntity) -> Unit,
        onError: (Throwable) -> Unit
    )
}