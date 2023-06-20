package com.busanit.ch18_news

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object{
        val Query = "여행"
        val API_KEY = "4b717e9db87e491cb31646126be48af0"
        val BASE_URL = "https://newsapi.org"
        val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36(KHTML, like Gecko) " +
                "Chrome/60.0.3112.113 Safari/537.36"

        // Retrofit 용 추가
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        var netWorkService: INetworkService = retrofit.create(INetworkService::class.java)

    }
}