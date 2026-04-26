package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()
            }
            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update { it.copy(isbn = action.isbn) }
                validateInputs()
            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(nbPages = action.pages) }
                validateInputs()
            }
            is AddBookUiAction.OnImageChange -> {
                _uiState.update { it.copy(imageUri = action.uri) }
            }
            AddBookUiAction.OnAddClick -> {
                if (_uiState.value.isFormValid) addBook()
            }
        }
    }

    private fun validateInputs() {
        val state = _uiState.value

        val titleError = if (state.title.isBlank()) "Title cannot be empty" else null

        val isbnError = if (state.isbn.length != 13 || !state.isbn.all { it.isDigit() })
            "ISBN must be exactly 13 digits" else null

        val pagesError = if (state.nbPages.toIntOrNull() == null || state.nbPages.toInt() <= 0)
            "Pages must be a positive number" else null

        _uiState.update {
            it.copy(
                titleError = titleError,
                isbnError = isbnError,
                pagesError = pagesError,
                isFormValid = titleError == null && isbnError == null && pagesError == null
            )
        }
    }
    
    private fun addBook() {
        val currentState = _uiState.value
        val book = Book(
            isbn = currentState.isbn,
            title = currentState.title,
            nbPages = currentState.nbPages.toIntOrNull() ?: 0,
            imageUrl = currentState.imageUri?.toString()
        )
        addBookUseCase(book)
        _uiState.update { it.copy(isSuccess = true) }
    }
}
