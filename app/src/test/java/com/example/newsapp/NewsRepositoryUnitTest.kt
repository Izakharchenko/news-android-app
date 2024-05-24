package com.example.newsapp

import com.example.newsapp.model.News
import com.example.newsapp.model.PopArticle
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.repository.NewsRepositoryImpl
import com.example.newsapp.service.NewsService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import retrofit2.Call
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.mock.Calls

class NewsRepositoryUnitTest {
    private lateinit var newsService: NewsService
    private lateinit var repository: NewsRepository
    private var news = News(
        id = 1, title = "Title 1", categoryId = 1, categoryTitle = "Category 1",
        body = "Many text", cover = "image", author = "Admin", source = "Some site", views = 0
    )
    private var news1 = News(
        id = 2, title = "Title 2", categoryId = 2, categoryTitle = "Category 2",
        body = "Big Story", cover = "image", author = "User", source = "Some site", views = 33
    )

    @Before
    fun setUp() {
        newsService = mockk()
        repository = NewsRepositoryImpl(newsService)
    }

    @Test
    fun `getNews return list of News`() = runBlocking {
        val listNews = listOf(news, news1)
        val mockCall: Call<List<News>> = Calls.response(listNews)

        coEvery { newsService.getNews() } returns mockCall

        val result = repository.getNews()

        assertEquals(listNews, result)
    }

    @Test
    fun `getNewsById return news by id`() = runBlocking {
        val mockCall: Call<News> = Calls.response(news)

        coEvery { newsService.getNews(news.id!!) } returns mockCall

        val result = repository.getNewsById(news.id!!)

        assertEquals(news, result)
    }

    @Test
    fun `getNewsByCategory return list of news`() = runBlocking {
        val categoryId = 1
        val list = listOf(news, News(
            id = 2, title = "Title 2", categoryId = categoryId, categoryTitle = "Category 2",
            body = "Big Story", cover = "image", author = "User", source = "Some site", views = 33
        ))
        val mockCall: Call<List<News>> = Calls.response(list)

        coEvery { newsService.getNewsByCategories(categoryId) } returns mockCall

        val result = repository.getNewsByCategory(categoryId)

        assert(result.all { it.categoryId == categoryId })

        assertEquals(list, result)
    }

    @Test
    fun `incrementViewCount return new view`() = runBlocking {
        val id = 1
        val newViewCount = 44

        val mockCall: Call<Int> = Calls.response(newViewCount)
        coEvery { newsService.incrementViewCount(id) } returns mockCall

        val result = repository.incrementViewCount(id)

        assertEquals(newViewCount, result)
    }

    @Test
    fun `getMostPopularNews return pop news`() = runBlocking {
        val popArticle = PopArticle("Most of popular", 44)
        val mockCall: Call<PopArticle> = Calls.response(popArticle)
        coEvery { newsService.getMostPopularNews() } returns  mockCall

        val result = repository.getMostPopularNews()

        assertEquals(popArticle, result)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}