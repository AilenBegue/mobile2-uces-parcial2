package com.example.parcial2_mobile2

data class Characters(
    val results: List<Result>
)
data class Location(
    val name: String,
    val url: String
)

data class Result(
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val species: String,
    val status: String,
    val url: String
)