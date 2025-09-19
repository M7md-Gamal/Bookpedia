package com.elkabsh.bookpedia.book.data.remote

import com.elkabsh.bookpedia.book.data.dto.BookWorkDto
import com.elkabsh.bookpedia.book.data.dto.SearchResponseDto
import com.elkabsh.bookpedia.core.data.safeCall
import com.elkabsh.bookpedia.core.domain.DataError
import com.elkabsh.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBook(
        query: String, resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {

        return safeCall<SearchResponseDto> {
            httpClient.get("$BASE_URL/search.json") {
                parameter("q", query)
                parameter("limit", resultLimit) // default limit
                //parameter("language", "eng") // only english books
                parameter(
                    "fields",
                    "key,title,author_name," +
                            "author_key," +
                            "cover_edition_key,cover_i," +
                            "ratings_average,ratings_count," +
                            "first_publish_year,language," +
                            "number_of_pages_median,edition_count"
                )
            }
        }

    }

    override suspend fun getBookDescription(workKey: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$workKey.json"

            )
        }
    }
}
