package com.example.pingme.Auth.ViewModel

import com.example.pingme.Auth.Model.loginData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface loginInterface{
    @POST("/login")
    suspend fun loginUser(@Body user: loginData) : String
}

object RetrofitLoginClient{
    private const val BASE_URL = "http://172.22.78.106:8080"

    val apiService: loginInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(loginInterface::class.java)
    }
}
