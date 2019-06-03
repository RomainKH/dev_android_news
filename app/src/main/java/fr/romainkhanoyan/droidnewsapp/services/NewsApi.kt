package fr.romainkhanoyan.droidnewsapp.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines?pageSize=100&apiKey=6e2f93df9e8c4b4b8fd9a4041fb33f67")
    fun getHeadlines(@Query("country") country: String): Call<NewsWrapper>

    @GET("top-headlines?category=health&apiKey=6e2f93df9e8c4b4b8fd9a4041fb33f67")
    fun getHeadlinesHealth(@Query("country") country: String): Call<NewsWrapper>

}

data class Source(val name: String)

data class Article(val source: Source,
                    val author: String,
                    val title: String,
                    val description: String,
                    val url: String,
                    val urlToImage: String,
                    val publishedAt: String,
                    val content: String)

data class NewsWrapper(val status: String,
                       val totalResults: Int,
                       val articles: List<Article>)