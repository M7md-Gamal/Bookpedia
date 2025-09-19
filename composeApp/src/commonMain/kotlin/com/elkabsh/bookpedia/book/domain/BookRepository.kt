package com.elkabsh.bookpedia.book.domain

import com.elkabsh.bookpedia.core.domain.DataError
import com.elkabsh.bookpedia.core.domain.EmptyResult
import com.elkabsh.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>

    fun getAllFavoriteBooks(): Flow<List<Book>>
    fun isBookInFavorites(bookId: String): Flow<Boolean>
    suspend fun addBookToFavorites(book: Book): EmptyResult<DataError.Local>
    suspend fun removeBookFromFavorites(book: Book)

}