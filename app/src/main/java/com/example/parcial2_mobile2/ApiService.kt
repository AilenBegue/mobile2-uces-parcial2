package com.example.parcial2_mobile2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getAllCharacters(@Url url:String): Response<Characters>
}