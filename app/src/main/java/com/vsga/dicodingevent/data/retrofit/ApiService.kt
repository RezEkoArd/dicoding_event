package com.vsga.dicodingevent.data.retrofit

import com.vsga.dicodingevent.data.response.ResponseEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/events")
    suspend fun getEvents(
        @Query("active")query: String
    ): Response<ResponseEvent>

}