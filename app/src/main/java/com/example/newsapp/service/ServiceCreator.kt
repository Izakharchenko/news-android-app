package com.example.newsapp.service

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ServiceCreator {
    private const val BASE_URL = "https://actual-moved-octopus.ngrok-free.app/api/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

    suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            continuation.resume(body)
                        } else {
                            continuation.resumeWithException(
                                RuntimeException("Response body is null")
                            )
                        }
                    } else {
                        continuation.resumeWithException(
                            RuntimeException("Response is not successful: ${response.code()}")
                        )
                    }
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}