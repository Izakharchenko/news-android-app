package com.example.newsapp

import com.example.newsapp.db.FavoriteDao
import com.example.newsapp.model.Favorite
import com.example.newsapp.model.News
import com.example.newsapp.repository.FavoriteRepository
import com.example.newsapp.repository.FavoriteRepositoryImpl
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class FavoriteRepositoryUnitTest {
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var repository: FavoriteRepository

    private val favorite = Favorite(
        id = 1, title = "title", categoryId = 1, categoryTitle = "kotlin",
        body = "Big string", cover = "img.png", author = "admin", source = "none"
    )
    private val favorite1 = Favorite(
        id = 2, title = "other title", categoryId = 2, categoryTitle = "java",
        body = "not Big string", cover = "img.jpg", author = "user", source = "none"
    )

    @Before
    fun setUp () {
        favoriteDao = mockk()
        repository = FavoriteRepositoryImpl(favoriteDao)
    }
    @Test
    fun `add favorite calls dao insert`() = runBlocking {
        coEvery { favoriteDao.insert(any()) } just Runs

        repository.insert(favorite)

        coVerify(exactly = 1) { favoriteDao.insert(favorite) }
    }

    @Test
    fun `delete favorite calls delete`() = runBlocking {
        coEvery { favoriteDao.delete(any()) } just Runs

        repository.deleteById(favorite)

        coVerify (exactly = 1) { favoriteDao.delete(favorite) }
    }

    @Test
    fun `get favorite by id`() = runBlocking {

        val id = favorite.id
        coEvery { favoriteDao.getFavoriteById(id) } returns favorite

        val result = repository.getFavoriteById(id)

        assertEquals(favorite.toNews(), result)
    }

    @Test
    fun `get all favorites returns correct list of favorites`() = runBlocking {
        val favoriteList = listOf(favorite, favorite1)
        val favoriteFlow: Flow<List<Favorite>> = flowOf(favoriteList) // конвертуємо

        coEvery { favoriteDao.getFavorites() } returns favoriteFlow  // мокаємо

        val result = repository.getFavorites().toList() // викликаємо

        // перевіряю чи перший елмент співпадає перший бо res [[favorite, favorite]]
        assertEquals(favoriteList, result.first())
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}