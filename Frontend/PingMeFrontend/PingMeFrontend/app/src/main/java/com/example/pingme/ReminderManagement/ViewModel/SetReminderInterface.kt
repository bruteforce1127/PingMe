package com.example.pingme.ReminderManagement.ViewModel

import android.content.Context
import com.example.pingme.JWT_Token.AuthInterceptor
import com.example.pingme.ReminderManagement.Model.SetReminderClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT

interface SetReminderInterface {
    @PUT("/reminderManagement/{username}")
    suspend fun remind(
        @retrofit2.http.Path("username") username: String,
        @Body reminderData: SetReminderClass
    ): SetReminderClass
}

object RetrofitSetReminderClient {
    private const val BASE_URL = "http://172.22.43.225:8080"

    fun getApiService(context: Context): SetReminderInterface {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SetReminderInterface::class.java)
    }
}