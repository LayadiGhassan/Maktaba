package com.ElOuedUniv.maktaba.data.model

@Serializable
data class Book(
    val isbn: String,
    val title: String,
    val nbPages: Int,
    val imageUrl: String? = null
)

