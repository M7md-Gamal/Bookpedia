package com.elkabsh.bookpedia.book.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_books")
data class BookEntity(
    @PrimaryKey() val id: String,
    val title: String,
    val description: String?,
    val imgUrl: String,
    val languages:List<String>,
    val authors: List<String>,
    val firstPublishYear: String?,
    val ratingsAverage: Double?,
    val ratingsCount: Int?,
    val numberOfPages: Int?,
    val numOfEditions: Int,



)
