package com.elkabsh.bookpedia.book.data.remote

import com.elkabsh.bookpedia.book.data.dto.BookWorkDto
import com.elkabsh.bookpedia.book.data.dto.SearchResponseDto
import com.elkabsh.bookpedia.core.domain.DataError
import com.elkabsh.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBook(
        query: String, resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDescription(
        workKey: String
    ): Result<BookWorkDto, DataError.Remote>
}