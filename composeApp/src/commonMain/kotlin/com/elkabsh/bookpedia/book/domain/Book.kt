package com.elkabsh.bookpedia.book.domain

data class Book (
    val id: String,
    val title: String,
    val imgUrl: String,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val publishedDate: String?,
    val pageCount: Int?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val numEditions: Int
)