package com.example.seminar_assignment.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SampleService {
    @Headers ("Content-Type:application/json")
    @POST("/users/signin")
    fun postLogin (
        @Body body : RequestLoginData
    ) : Call<ResponseLoginData>

    @POST("/users/signup")
    fun postSignUp (
        @Body body : RequestSignUpData
    ) : Call<ResponseSignUpData>
}