package com.gbandroid.appsearchfilms.domain

interface TheMovieDBRepoCase {
    fun getReposForFilmSync(filmId: Long): TheMovieDBRepoEntity
}