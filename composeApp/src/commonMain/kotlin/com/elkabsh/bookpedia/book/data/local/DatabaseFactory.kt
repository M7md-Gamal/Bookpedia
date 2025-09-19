package com.elkabsh.bookpedia.book.data.local

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun createDatabase(): RoomDatabase.Builder<FavoriteBookDatabase>
}