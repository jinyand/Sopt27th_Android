package com.example.seminar_assignment

import com.example.seminar_assignment.network.SampleService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SampleInterface {
    private const val BASE_URL = "http://15.164.83.210:3000"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : SampleService = retrofit.create(SampleService::class.java)
}

