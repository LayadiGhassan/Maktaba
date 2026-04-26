package com.ElOuedUniv.maktaba.data.repository
import com.ElOuedUniv.maktaba.R
import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val _booksList = mutableListOf(
        Book(isbn = "9876543210000", title = "Ai-Native 6G networks", nbPages = 400,
            imageUrl = "res:${R.drawable.book_Ai_native_wirless}"),
        Book(isbn = "9780136475992", title = "Quantum computing", nbPages = 350,
            imageUrl = "res:${R.drawable.book_Quantum_computing}"),
        Book(isbn = "9780134533221", title = "Enterprise Ai OS", nbPages = 431,
            imageUrl = "res:${R.drawable.book_EnterpriseAi}"),
        Book(isbn = "9800135957059", title = "Agentic Ai", nbPages = 452,
            imageUrl = "res:${R.drawable.book_Agentic}"),
    )

    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) // Simulate delay
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        _booksList.add(book)
        booksFlow.tryEmit(_booksList.toList())
    }
}
