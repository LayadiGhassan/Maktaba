package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase  // Done ✅
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val addBookUseCase: AddBookUseCase      // Done ✅ - Inject UseCase
) : ViewModel() {
    
    // Done ✅ - UI State
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    // Done ✅ - One-time events flow
    private val _uiEvent = MutableSharedFlow<BookUiEvent>()
    val uiEvent: SharedFlow<BookUiEvent> = _uiEvent.asSharedFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getBooksUseCase().collect { bookList ->
                    _uiState.value = _uiState.value.copy(
                        books = bookList,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
                viewModelScope.launch {
                    _uiEvent.emit(BookUiEvent.ShowSnackbar(e.message ?: "Unknown error"))
                }
            }
        }
    }

    // Done ✅ - Handle UI Actions with when expression
    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.RefreshBooks -> refreshBooks()
            BookUiAction.OnAddBookClick -> {
                _uiState.value = _uiState.value.copy(isAddingBook = true)
            }
            BookUiAction.OnDismissAddBook -> {
                _uiState.value = _uiState.value.copy(isAddingBook = false)
            }
            is BookUiAction.OnAddBookConfirm -> {
                // Done ✅ - Call AddBookUseCase and hide dialog
                addBook(
                    title = action.title,
                    isbn = action.isbn,
                    nbPages = action.nbPages
                )
            }
        }
    }

    // Done ✅ - Add book using AddBookUseCase
    private fun addBook(title: String, isbn: String, nbPages: Int) {
        viewModelScope.launch {
            // Hide dialog immediately
            _uiState.value = _uiState.value.copy(isAddingBook = false, isLoading = true)
            
            try {
                // Create Book object and call use case
                val newBook = Book(
                    isbn = isbn,
                    title = title,
                    nbPages = nbPages
                )
                addBookUseCase(newBook)  // suspend function call
                
                // Done ✅ - Show success message as one-time event
                _uiEvent.emit(BookUiEvent.ShowSnackbar("Book added successfully"))
                
                // Refresh the list
                loadBooks()
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _uiEvent.emit(BookUiEvent.ShowSnackbar(e.message ?: "Failed to add book"))
            }
        }
    }

    fun refreshBooks() {
        loadBooks()
    }
}
