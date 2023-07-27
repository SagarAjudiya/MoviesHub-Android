package com.example.movieshub.api

import com.example.movieshub.data.model.response.CastDetail
import com.example.movieshub.data.model.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path(value = "category") category: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("tv/popular")
    suspend fun getTVShowsList(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path(value = "category") category: String,
    ): Response<MovieResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getCast(
        @Path(value = "movie_id") movieId: Int,
    ): Response<CastDetail>

}