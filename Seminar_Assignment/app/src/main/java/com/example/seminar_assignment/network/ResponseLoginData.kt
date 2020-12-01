package com.example.seminar_assignment.network

data class ResponseLoginData(
    val data : Login,
    val message : String,
    val status : Int,
    val success : Boolean
)

data class Login (
    val email : String,
    val password : String,
    val userName : String
)