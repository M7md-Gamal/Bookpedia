package com.elkabsh.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.elkabsh.bookpedia.app.App
import com.elkabsh.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bookpedia",
        ) {
            App()
        }
    }
}