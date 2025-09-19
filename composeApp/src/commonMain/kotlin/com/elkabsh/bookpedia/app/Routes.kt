package com.elkabsh.bookpedia.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object BookList: Route

    @Serializable
    data object BookDetails: Route

    @Serializable
    data object BookGraph: Route

}