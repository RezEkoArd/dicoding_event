package com.vsga.dicodingevent.data.retrofit

import com.vsga.dicodingevent.data.response.ResponseDetailEvent
import com.vsga.dicodingevent.data.response.ResponseEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/events")
    suspend fun getEvents(
        @Query("active")active: String,
        @Query("q") query: String? = null
    ): Response<ResponseEvent>

    @GET("/events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: String
    ): Response<ResponseDetailEvent>
}