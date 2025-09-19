package com.elkabsh.bookpedia.book.presentation.book_detail

import com.elkabsh.bookpedia.book.domain.Book
import com.elkabsh.bookpedia.core.presentation.UiText

data class BookDetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val error: UiText? = null,
    val book: Book? = null
)