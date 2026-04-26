package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import io.github.jan_tennert.supabase.SupabaseClient
import io.github.jan_tennert.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SupabaseCategoryRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> = flow {
        
        delay(1000) 
        
      
        val categories = supabaseClient.postgrest["categories"]
            .select()
            .decodeList<Category>()
            
        emit(categories)
    }.flowOn(Dispatchers.IO) 

    override suspend fun getCategoryById(id: String): Category? {
        return try {
            supabaseClient.postgrest["categories"]
                .select {
                    filter {
                        eq("id", id)
                    }
                }.decodeSingle<Category>()
        } catch (e: Exception) {
            null
        }
    }
}
