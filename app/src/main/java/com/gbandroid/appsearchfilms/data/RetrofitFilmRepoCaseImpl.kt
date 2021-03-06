package com.gbandroid.appsearchfilms.data

import com.gbandroid.appsearchfilms.BuildConfig
import com.gbandroid.appsearchfilms.data.retrofit.FilmRepoApi
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoCase
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/"
private const val API_STR = BuildConfig.FILM_API_KEY
private const val LANG_SELECTION = "ru"

class RetrofitFilmRepoCaseImpl : TheMovieDBRepoCase {


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var api: FilmRepoApi = retrofit.create(FilmRepoApi::class.java)

    override fun getReposForFilmAsync(
        filmId: Long,
        onSuccess: (TheMovieDBRepoEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        api.loadReposByFilm(filmId, API_STR, LANG_SELECTION)
            .enqueue(object : Callback<TheMovieDBRepoEntity> {
                override fun onResponse(
                    call: Call<TheMovieDBRepoEntity>,
                    response: Response<TheMovieDBRepoEntity>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body() ?: throw IllegalStateException("null result"))
                    } else {
                        onError(Throwable("unknown error"))
                    }
                }

                override fun onFailure(call: Call<TheMovieDBRepoEntity>, t: Throwable) {
                    onError(t)
                }
            })
    }
}