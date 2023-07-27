package com.example.movieshub.ui.tvshows.repository

import android.util.Log
import com.example.movieshub.data.model.response.MovieResponse
import com.example.movieshub.util.Constants
import com.example.movieshub.util.base.BaseRepository
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

class TVShowsRepository : BaseRepository() {
    fun getTVShows(currentPage: Int): Result<MovieResponse> {
        // For Learning done API call using okhttp3
        return try {
            val urlBuilder: HttpUrl.Builder = Constants.BASE_URL.toHttpUrl().newBuilder()
            urlBuilder.addPathSegments("tv/popular")
            urlBuilder.addQueryParameter("language", "en-US")
            urlBuilder.addQueryParameter("page", currentPage.toString())
            val url: String = urlBuilder.build().toString()

            val request: Request = Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer ${Constants.AUTH_TOKEN}")
                .build()

            val client = OkHttpClient()
            val response = client.newCall(request).execute()
            val movieResponse = Gson().fromJson(response.body?.string(), MovieResponse::class.java)
            val myResponse = Response.success(movieResponse)
            handleResponse(myResponse)
        } catch (e: Exception) {
            Log.d("TAG", "getTVShows: ${e.localizedMessage}")
            Result.failure(Throwable(e.localizedMessage))
        }
    }
}