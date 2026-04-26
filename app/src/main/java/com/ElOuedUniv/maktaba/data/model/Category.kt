package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.Serializable

// TODO: Complete the Category data class implementation
@Serializable
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val iconRes: Int
)
