package com.example.seminar_assignment.network

data class ResponseSignUpData(
    val data : SignUp,
    val message : String,
    val status : Int,
    val success : Boolean
)

data class SignUp (
    val email : String,
    val password : String,
    val userName : String
)