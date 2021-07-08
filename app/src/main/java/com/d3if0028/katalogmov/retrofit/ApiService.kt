package com.d3if0028.katalogmov.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "https://api.themoviedb.org/3/movie/"

class ApiService {
    val endpoint: ApiEndpoint
    get() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiEndpoint::class.java)
    }
}