package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Book(
    @SerialName("isbn")
    val isbn: String, 
    
    @SerialName("title")
    val title: String,
    
    @SerialName("nb_pages")
    val nbPages: Int,
    
    @SerialName("image_url")
    val imageUrl: String? = null,
    
    @SerialName("category_id") 
    val categoryId: Long? = null
)
