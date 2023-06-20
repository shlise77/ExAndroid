package com.busanit.ch18_news

import com.busanit.ch18_news.model.PageListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkService {
    @GET("/v2/everything")
    fun getList(
        @Query("q") q: String?, @Query("apikey") apikey: String?,
        @Query("page") page: Long, @Query("pageSize") pageSize: Int
    ): Call<PageListModel>
}