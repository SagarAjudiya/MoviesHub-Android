package com.example.movieshub.ui.home.repository

import com.example.movieshub.api.RetrofitClient
import com.example.movieshub.data.model.response.CastDetail
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.util.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MovieRepository {

    suspend fun getMovies(category: String): MovieResponse? {
        val response = RetrofitClient.service.getMovies(category = category)
        return response.body()
    }

    suspend fun getCast(movieID: Int): CastDetail? {
        val castResponse = RetrofitClient.service.getCast(movieID)
        return castResponse.body()
    }

    suspend fun getCategories(): List<String> {
        // Used OKHttp for Learning Purpose
        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(Constants.CATEGORY_URL)
            .addHeader("Authorization", "Bearer ${Constants.AUTH_TOKEN}")
            .build()
        val listOfCategories = ArrayList<String>()
        val response = okHttpClient.newCall(request).execute()
        val jsonString = response.body?.string()

        if (jsonString != null) {
            listOfCategories.addAll(parseGenres(jsonString))
        }
        return listOfCategories
    }

    private fun parseGenres(json: String): List<String> {
        val genreNames = mutableListOf<String>()
        val jsonObject = JSONObject(json)
        val genresArray = jsonObject.optJSONArray("genres")
        genresArray?.let {
            for (i in 0 until genresArray.length()) {
                val genreObject = genresArray.optJSONObject(i)
                val genreName = genreObject.optString("name")
                genreNames.add(genreName)
            }
        }
        return genreNames
    }

}