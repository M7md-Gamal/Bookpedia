package com.elkabsh.bookpedia.book.presentation.book_list

import com.elkabsh.bookpedia.book.domain.Book
import com.elkabsh.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "kotlin",
    val searchResult: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val selectedTapIndex: Int = 0,
    val isLoading: Boolean = true,
    val error: UiText? = null
)
