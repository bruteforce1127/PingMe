package com.example.pingme.ReminderManagement.ViewModel

import android.content.Context
import com.example.pingme.JWT_Token.AuthInterceptor
import com.example.pingme.ReminderManagement.Model.ReminderHistoryList
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ReminderHistoryApiInterface {
    @GET("/reminderManagement/{username}")
    suspend fun getHistory(
        @Path("username") username: String
    ): ReminderHistoryList
}

object RetrofitReminderHistoryClient {
    private const val BASE_URL = "http://172.22.43.225:8080"

    fun getApiService(context: Context): ReminderHistoryApiInterface {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        // Create a custom Gson instance with the specified date format
        val gson = GsonBuilder()
            .setDateFormat("MMM dd, yyyy")
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ReminderHistoryApiInterface::class.java)
    }
}