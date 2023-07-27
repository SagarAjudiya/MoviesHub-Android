package com.example.movieshub.util

import com.example.movieshub.BuildConfig

object Constants {

    val BASE_URL: String by lazy {
        BuildConfig.BASE_URL
    }
    val AUTH_TOKEN: String by lazy {
        BuildConfig.AUTH_TOKEN
    }
    val BASE_IMAGE_PATH: String by lazy {
        BuildConfig.BASE_IMAGE_PATH
    }
    val CATEGORY_URL: String by lazy {
        BuildConfig.CATEGORY_URL
    }

}