package com.example.pingme.Auth.ViewModel

import com.example.pingme.Auth.Model.SignUpData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpInterface {
    @POST("/register")
    suspend fun registerUser(@Body user: SignUpData): SignUpData
}

object RetrofitSignUpClient {
    private const val BASE_URL = "http://172.22.78.106:8080"

    val apiService: SignUpInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignUpInterface::class.java)
    }
}