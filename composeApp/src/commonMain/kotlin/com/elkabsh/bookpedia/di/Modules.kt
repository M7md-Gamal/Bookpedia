package com.elkabsh.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.elkabsh.bookpedia.book.data.local.DatabaseFactory
import com.elkabsh.bookpedia.book.data.local.FavoriteBookDatabase
import com.elkabsh.bookpedia.book.data.remote.KtorRemoteBookDataSource
import com.elkabsh.bookpedia.book.data.remote.RemoteBookDataSource
import com.elkabsh.bookpedia.book.data.repo.BookRepositoryImpl
import com.elkabsh.bookpedia.book.domain.BookRepository
import com.elkabsh.bookpedia.book.presentation.SelectedBookViewModel
import com.elkabsh.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.elkabsh.bookpedia.book.presentation.book_list.BookListViewModel
import com.elkabsh.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformModule: Module

val sharedModule = module {
    single {
        HttpClientFactory.create(get())
    }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()
    single {
        get<DatabaseFactory>().createDatabase()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<FavoriteBookDatabase>().dao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)

}