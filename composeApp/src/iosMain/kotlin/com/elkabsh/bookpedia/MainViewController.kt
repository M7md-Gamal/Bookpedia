package com.elkabsh.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.elkabsh.bookpedia.app.App
import com.elkabsh.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin () }
) { App() }