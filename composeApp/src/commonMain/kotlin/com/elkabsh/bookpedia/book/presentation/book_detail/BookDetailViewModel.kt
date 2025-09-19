package com.elkabsh.bookpedia.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elkabsh.bookpedia.book.domain.BookRepository
import com.elkabsh.bookpedia.core.domain.onError
import com.elkabsh.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    val bookRepository: BookRepository

) : ViewModel() {
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            observeIfBookIsFavorite()
            loadBookDescription()
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _state.value
        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnBackClick -> Unit // Handled in the UI

            is BookDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    state.value.book?.let { book ->
                        if (state.value.isFavorite) {
                            bookRepository.removeBookFromFavorites(book)
                        } else {
                            bookRepository.addBookToFavorites(book)
                                .onError { error ->
                                    println("Error adding book to favorites: $error")
                                }
                        }
                    }
                }
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _state.value = _state.value.copy(book = action.book)
            }
        }
    }

    fun loadBookDescription() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = false) }
            state.value.book?.id?.let { id ->
                _state.value = _state.value.copy(isLoading = true)
                println("Loading book description...")
                bookRepository.getBookDescription(bookId = id)
                    .onSuccess { description ->
                        _state.update {
                            it.copy(
                                book = it.book?.copy(description = description),
                                isLoading = false
                            )
                        }
                    }
                    .onError {
                        println("Error loading book description: $it")
                    }
            }

        }
    }
    private fun observeIfBookIsFavorite() {
        viewModelScope.launch {
            _state.value.book?.id?.let {
                bookRepository.isBookInFavorites(it) }?.collect { isFavorite ->
                _state.update { it.copy(isFavorite = isFavorite) }
            }
        }
    }

}