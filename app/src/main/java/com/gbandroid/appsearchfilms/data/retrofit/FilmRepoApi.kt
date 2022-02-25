package com.gbandroid.appsearchfilms.data.retrofit

import com.gbandroid.appsearchfilms.domain.ListFilms
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmRepoApi {

    @GET("3/movie/{id}")
    fun loadReposByFilm(
        @Path("id") filmId: Long,
        @Query("api_key") apiStr: String,
        @Query("language") lang: String,
    ): Call<TheMovieDBRepoEntity>

    @GET("3/movie/{list_name}")
    fun loadListFilms(
        @Path("list_name") listName: String,
        @Query("api_key") apiStr: String,
        @Query("language") lang: String,
        @Query("page") page: Int,
    ): Call<ListFilms>

}