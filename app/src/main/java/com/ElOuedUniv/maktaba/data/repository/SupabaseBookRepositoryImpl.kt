package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import io.github.jan_tennert.supabase.SupabaseClient
import io.github.jan_tennert.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SupabaseBookRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : BookRepository {

    
    private val refreshSignal = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    override fun getAllBooks(): Flow<List<Book>> = flow {
        
        refreshSignal.collect {
            val books = supabaseClient.postgrest["books"]
                .select()
                .decodeList<Book>()
            emit(books)
        }
    }.onStart { delay(1000) } 
     .flowOn(Dispatchers.IO)

    override suspend fun getBookByIsbn(isbn: String): Book? {
        return try {
            supabaseClient.postgrest["books"]
                .select {
                    filter {
                        eq("isbn", isbn)
                    }
                }.decodeSingle<Book>()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addBook(book: Book) {
      
        supabaseClient.postgrest["books"].insert(book)
        
      
        refreshSignal.emit(Unit)
    }
}
