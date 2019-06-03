package fr.romainkhanoyan.droidnewsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import fr.romainkhanoyan.droidnewsapp.services.NewsRepository
import fr.romainkhanoyan.droidnewsapp.services.NewsWrapper
import fr.romainkhanoyan.droidnewsapp.viewholder.NewsItem
import kotlinx.android.synthetic.main.activity_news_feed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)

        var query = "us"

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        val itemAdapter = ItemAdapter<IItem<*, *>>()

        createContent(query)

        changingNewsButton.setOnClickListener{
            query = "fr"
            createContent(query)
        }
    }

    fun createContent(query: String)
    {
        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val repository = NewsRepository()
        val fastAdapter = FastAdapter.with<NewsItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        itemAdapter.clear()

        repository.api.getHeadlinesHealth(query)
            .enqueue(object : Callback<NewsWrapper> {

                override fun onFailure(call: Call<NewsWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@HealthActivity, "ERROR", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<NewsWrapper>, response: Response<NewsWrapper>) {
                    val newsWrapper = response.body()

                    if (newsWrapper != null) {

                        for (_article in newsWrapper.articles) {
                            val item = NewsItem(_article)

                            itemAdapter.add(item)
                        }
                    }

                }

            })

        recyclerView.adapter = fastAdapter

        fastAdapter.withOnClickListener { view, adapter, item, position ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("image", item.article.urlToImage)
            intent.putExtra("title", item.article.title)
            intent.putExtra("author", item.article.author)
            intent.putExtra("date", item.article.publishedAt)
            intent.putExtra("content", item.article.content)
            startActivityForResult(intent, 1)
            true
        }
    }
}
