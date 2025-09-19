package com.elkabsh.bookpedia.book.data.local

import androidx.room.RoomDatabaseConstructor

@Suppress("No_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor: RoomDatabaseConstructor<FavoriteBookDatabase>{
    override fun initialize(): FavoriteBookDatabase
}