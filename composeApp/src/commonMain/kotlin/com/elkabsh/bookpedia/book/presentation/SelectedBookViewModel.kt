package com.elkabsh.bookpedia.book.presentation

import androidx.lifecycle.ViewModel
import com.elkabsh.bookpedia.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedBookViewModel: ViewModel() {

    val _selectedBook= MutableStateFlow<Book?>(null)
    val selectedBook= _selectedBook.asStateFlow()

    fun selectBook(book: Book){
        _selectedBook.value= book
    }

}