package com.example.parcial2_mobile2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET ("character")
    suspend fun getAllCharacters(): Response<Characters>

    @GET ("character/{id}")
    suspend fun getCharacterById(@Path("id") characterId: Int): Response<Result>

    @GET ("character/?status=alive")
    suspend fun getAliveCharacters(): Response<Characters>

    @GET ("character/?status=dead")
    suspend fun getDeadCharacters(): Response<Characters>

    @GET ("character/?status=unknown")
    suspend fun getUnknownCharacters(): Response<Characters>
}