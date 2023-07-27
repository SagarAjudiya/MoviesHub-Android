package com.example.movieshub.util.base

import com.example.movieshub.data.model.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseRepository {
    fun <T> handleResponse(response: Response<T>): Result<T> {
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.success(body)
                }
                return Result.failure(Throwable("No Data Found!"))
            } else {
                if (response.code() in 400..499) {
                    response.errorBody().let {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(), ErrorResponse::class.java
                        )
                        return Result.failure(Throwable(errorResponse.statusMessage))
                    }
                } else {
                    return Result.failure(Throwable(response.message()))
                }
            }
        } catch (e: Error) {
            Result.failure(Throwable(e.message ?: "Something went wrong!"))
        }
    }
}