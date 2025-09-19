package com.elkabsh.bookpedia.book.data.repo

import androidx.sqlite.SQLiteException
import com.elkabsh.bookpedia.book.data.local.FavoriteBookDao
import com.elkabsh.bookpedia.book.data.mappers.toBook
import com.elkabsh.bookpedia.book.data.mappers.toBookEntity
import com.elkabsh.bookpedia.book.data.remote.RemoteBookDataSource
import com.elkabsh.bookpedia.book.domain.Book
import com.elkabsh.bookpedia.book.domain.BookRepository
import com.elkabsh.bookpedia.core.domain.DataError
import com.elkabsh.bookpedia.core.domain.EmptyResult
import com.elkabsh.bookpedia.core.domain.Result
import com.elkabsh.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val remoteDataSource: RemoteBookDataSource, private val favoriteBookDao: FavoriteBookDao
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {

        return remoteDataSource.searchBook(query).map { dto -> dto.result.map { it.toBook() } }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localBook = favoriteBookDao.getBookById(bookId)
        return if (localBook != null) {
            Result.Success(localBook.description)
        } else remoteDataSource.getBookDescription(bookId).map { it.description }
    }

    override fun getAllFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getAllFavoriteBooks().map { list -> list.map { it.toBook() } }
    }

    override fun isBookInFavorites(bookId: String): Flow<Boolean> {
        return favoriteBookDao.getAllFavoriteBooks().map { list -> list.any { it.id == bookId } }
    }

    override suspend fun addBookToFavorites(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun removeBookFromFavorites(book: Book) {
        favoriteBookDao.deleteBookFromFavoritesById(book.toBookEntity())
    }


}

