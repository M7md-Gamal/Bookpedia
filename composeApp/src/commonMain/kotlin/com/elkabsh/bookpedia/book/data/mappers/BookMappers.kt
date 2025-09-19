package com.elkabsh.bookpedia.book.data.mappers

import com.elkabsh.bookpedia.book.data.dto.SearchedBookDto
import com.elkabsh.bookpedia.book.data.local.BookEntity
import com.elkabsh.bookpedia.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imgUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        publishedDate = firstPublishYear.toString(),
        averageRating = ratingAverage,
        ratingsCount = ratingCount,
        pageCount = numPagesMedian,
        numEditions = numEditions ?: 0
    )


}

    fun Book.toBookEntity(): BookEntity {
        return BookEntity(
            id = id,
            title = title,
            description = description,
            imgUrl = imgUrl,
            languages = languages,
            authors = authors,
            firstPublishYear = publishedDate ,
            ratingsAverage = averageRating,
            ratingsCount = ratingsCount,
            numberOfPages = pageCount,
            numOfEditions = numEditions
        )
    }

    fun BookEntity.toBook(): Book {
        return Book(
            id = id,
            title = title,
            description = description,
            imgUrl = imgUrl,
            languages = languages,
            authors = authors,
            publishedDate = firstPublishYear,
            averageRating = ratingsAverage,
            ratingsCount = ratingsCount,
            pageCount = numberOfPages,
            numEditions = numOfEditions
        )
    }

