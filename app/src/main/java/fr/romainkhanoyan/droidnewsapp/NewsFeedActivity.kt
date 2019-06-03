package fr.romainkhanoyan.droidnewsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class NewsFeedActivity : AppCompatActivity() {
    var feed = "hot"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)
        var query = "us"

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        createContent(query)

        healthButton.setOnClickListener{
            createContentHealth(query)
        }

        val options = arrayListOf("Amerique", "France", "Germany","United Kingdom")
        val values = arrayListOf("us","fr", "de", "gb")
        countrySelector.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,options)
        countrySelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@NewsFeedActivity, "not selected", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(feed == "hot")
                {
                    createContent(values[p2])
                }
                else if (feed == "health")
                {
                    createContentHealth(values[p2])
                }
            }
        }
    }

    fun createContent(query: String)
    {
        feed = "hot"
        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val repository = NewsRepository()
        val fastAdapter = FastAdapter.with<NewsItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        itemAdapter.clear()

        repository.api.getHeadlines(query)
            .enqueue(object : Callback<NewsWrapper> {

                override fun onFailure(call: Call<NewsWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@NewsFeedActivity, "ERROR", Toast.LENGTH_SHORT).show()
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
            intent.putExtra("content", item.article.description)
            startActivityForResult(intent, 1)
            true
        }
    }

    fun createContentHealth(query: String)
    {
        feed = "health"
        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val repository = NewsRepository()
        val fastAdapter = FastAdapter.with<NewsItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        itemAdapter.clear()

        repository.api.getHeadlinesHealth(query)
            .enqueue(object : Callback<NewsWrapper> {

                override fun onFailure(call: Call<NewsWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@NewsFeedActivity, "ERROR", Toast.LENGTH_SHORT).show()
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
