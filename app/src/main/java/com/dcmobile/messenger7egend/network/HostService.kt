package com.dcmobile.messenger7egend.network

import com.dcmobile.messenger7egend.BuildConfig
import com.dcmobile.messenger7egend.network.model.Conversation
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HostService {
    @GET("/conversation")
    fun getConversation(): Call<Conversation>

    companion object Factory {

        fun create(): HostService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()

            return retrofit.create(HostService::class.java)
        }
    }
}