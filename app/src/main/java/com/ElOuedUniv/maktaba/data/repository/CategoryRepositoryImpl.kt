package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category

class CategoryRepositoryImpl : CategoryRepository {

    private val categoriesList = listOf(
    Category(
        id = "1",
        name = "Programming",
        description = "Books about software development and coding"
    ),
    Category(
        id = "2",
        name = "Algorithms",
        description = "Books about algorithms and data structures"
    ),
    Category(
        id = "3",
        name = "Databases",
        description = "Books about database design and management"
    ),
    Category(
        id = "4",
        name = "Software Engineering",
        description = "Books about Software Engineering design and management"
    ),
    Category(
        id = "5",
        name = "Machine learning",
        description = "Books about Machine learning design and management"
    ),
)
    
    override fun getAllCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override fun getCategoryById(id: String): Category? {
        TODO("Not yet implemented")
    }
}
