package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

class BookRepositoryImpl : BookRepository {

    private val booksList = listOf(
        Book(isbn = "978-0134685991", title = "Clean Code", nbPages = 464),
        Book(isbn = "978-0135957059", title = "The Pragmatic Programmer", nbPages = 352),
        Book(isbn = "978-0201633610", title = "Design Patterns", nbPages = 395),
        Book(isbn = "978-0134757599", title = "Refactoring", nbPages = 448),
        Book(isbn = "978-1492057130", title = "Head First Design Patterns", nbPages = 672),
        Book(isbn = "978-0134610993", title = "Artificial Intelligence: A Modern Approach", nbPages = 1166),
        Book(isbn = "978-1107002173", title = "Quantum Computation and Quantum Information", nbPages = 702),
        Book(isbn = "978-1788477543", title = "Building Smart Drones with ESP8266 and Arduino", nbPages = 326),
        Book(isbn = "978-1788470599", title = "Internet of Things for Architects", nbPages = 536),
        Book(isbn = "978-1484274545", title = "Build Better Chatbots: A Complete Guide to Interactive AI", nbPages = 291)
    )
    
    override fun getAllBooks(): List<Book> {
        return booksList
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }
}

    fun searchBooksByTitle(title: String): List<Book> {
        return books.filter { it.title.contains(title, ignoreCase = true) }
}


/**
exo4:

4.1 Book Model (Book.kt)
Data Class: A special Kotlin class designed strictly to hold data. It automatically generates functions like equals(), hashCode(), toString(), and copy().
val vs var: We use val to ensure Thread-Safety and maintain a "Single Source of Truth," preventing accidental data changes.
Using var: It would make the properties mutable, leading to unpredictable states and making the code harder to debug in complex apps.

4.2 Repository (BookRepository.kt)
Purpose: It acts as an abstraction layer between the data sources (API, Database, or Dummy Data) and the rest of the app.
Return type of getAllBooks(): It returns a List<Book>.

4.3 Use Case (GetBooksUseCase.kt)
Why a Use Case? It follows the Single Responsibility Principle. It encapsulates specific business logic, making it reusable across different ViewModels and easier to unit test.
operator fun invoke(): This allows the class instance to be called like a function, making the syntax cleaner.
modifying the invoke operator by applying a filter and adding the condition nbpages > 300

4.4 ViewModel (BookViewModel.kt)
StateFlow: A state-holder observable flow that emits the current and new state updates to the UI. It is lifecycle-aware.
_books vs books: This is Encapsulation. _books is private so only the ViewModel can change the data, while books (StateFlow) is public and read-only for the UI.
viewModelScope: A Coroutine scope tied to the ViewModel's lifecycle. It automatically cancels long-running tasks when the ViewModel is cleared to prevent memory leaks.
init block: It is executed immediately when the ViewModel is instantiated.

4.5 View (BookListScreen.kt)
Observing changes: The UI uses collectAsState() or collectAsStateWithLifecycle() to convert the StateFlow into a Compose State that triggers a recomposition when data changes.
LazyColumn vs Column: LazyColumn is more efficient, it only renders items currently visible on the screen, whereas Column renders everything at once, which kills performance for large lists.
Empty List: If the list is empty, LazyColumn simply renders nothing unless you explicitly code an empty state UI (eg: a "No books found" message).


**/
