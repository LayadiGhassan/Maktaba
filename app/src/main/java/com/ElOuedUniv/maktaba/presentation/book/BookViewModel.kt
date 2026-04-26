package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {
    
    /** ¹ ✅ */
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()
    // ✅
    private val _uiEvent = MutableSharedFlow<BookUiEvent>()
    val uiEvent: SharedFlow<BookUiEvent> = _uiEvent.asSharedFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {

            /** ¹ ✅ */
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
            
            }

        }
    }

    /**
     * TODO: Exercise 3 - Handle UI Actions
     */
    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.RefreshBooks -> refreshBooks()
            BookUiAction.OnAddBookClick -> {
                // TODO: Set isAddingBook = true in your uiState ✅ 
                _uiState.value = _uiState.value.copy(isAddingBook = true)
            }
            BookUiAction.OnDismissAddBook -> {
                // TODO: Set isAddingBook = false ✅ 
                _uiState.value = _uiState.value.copy(isAddingBook = false)
            }
            is BookUiAction.OnAddBookConfirm -> {
                // TODO: Call AddBookUseCase and hide dialog  ✅
                addBook(action.title, action.isbn, action.nbPages)
               
            }
        }
    }

    private fun addBook(title: String, isbn: String, nbPages: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isAddingBook = false)
            try {
                 // Assuming you have an AddBookUseCase
                 addBookUseCase(title, isbn, nbPages).collect { result ->
                     _uiState.value = _uiState.value.copy(isLoading = false)
                     _uiEvent.emit(BookUiEvent.ShowSnackbar("Book added successfully"))
                     loadBooks() // Refresh the list
                 }
                // Placeholder for actual implementation
                _uiState.value = _uiState.value.copy(isLoading = false)
                _uiEvent.emit(BookUiEvent.ShowSnackbar("Book added: $title"))
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

