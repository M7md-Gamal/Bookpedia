package com.elkabsh.bookpedia.book.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Upsert
    suspend fun upsert(bookEntity: BookEntity)

    @Delete
    suspend fun deleteBookFromFavoritesById(book: BookEntity )


    @Query("SELECT * FROM favorite_books")
    fun getAllFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM favorite_books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookEntity?
}