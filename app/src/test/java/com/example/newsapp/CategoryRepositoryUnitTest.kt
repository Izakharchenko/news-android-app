package com.example.newsapp

import com.example.newsapp.model.Category
import com.example.newsapp.repository.CategoryRepository
import com.example.newsapp.repository.CategoryRepositoryImpl
import com.example.newsapp.service.CategoryService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.mock.Calls

class CategoryRepositoryUnitTest {
    private lateinit var categoryService: CategoryService
    private lateinit var repository: CategoryRepository

    @Before
    fun setUp() {
        categoryService = mockk()
        repository = CategoryRepositoryImpl(categoryService)
    }

    @Test
    fun `getCategories return Categories from API`() = runBlocking {
        val categories = listOf(
            Category(id = 1, title = "kotlin"), Category(id = 2, "Retrofit")
        )

        val mockedCall: Call<List<Category>> = Calls.response(categories)
        coEvery { categoryService.getCategories() } returns mockedCall

        val result = repository.getCategories()

        assertEquals(categories, result)
    }
    @After
    fun tearDown() {
        clearAllMocks()
    }

}