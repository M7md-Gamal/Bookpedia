package com.elkabsh.bookpedia.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration?=null) {
    startKoin {
        modules(sharedModule, platformModule)
        config?.invoke(this)
    }
}