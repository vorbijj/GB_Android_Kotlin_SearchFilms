package com.gbandroid.appsearchfilms.data

import com.gbandroid.appsearchfilms.BuildConfig
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoCase
import com.gbandroid.appsearchfilms.domain.TheMovieDBRepoEntity
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WebFilmRepoCaseImpl: TheMovieDBRepoCase {

    override fun getReposForFilmSync(filmId: Long): TheMovieDBRepoEntity {
        lateinit var result: TheMovieDBRepoEntity

        var connection: HttpURLConnection? = null
        try {
            connection = getUrl(filmId).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5_000

            val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
            val resultJsonString = bufferedReader.readLines().toString()

            val reposArray =
                gson.fromJson(resultJsonString, Array<TheMovieDBRepoEntity>::class.java)

            result = reposArray[0]


        } catch (thr: Throwable) {
            connection?.disconnect()
            throw thr
        } finally {
            connection?.disconnect()
        }

        return result
    }

    override fun getReposForFilmAsync(filmId: Long, callback: (TheMovieDBRepoEntity) -> Unit) {
        Thread {
            callback.invoke(getReposForFilmSync(filmId))
        }.start()
    }

    private val gson by lazy { Gson() }

    private fun getUrl(filmId: Long, api_str: String = BuildConfig.FILM_API_KEY) = URL(
        "https://api.themoviedb.org/3/movie/$filmId?api_key=$api_str&language=ru")
}