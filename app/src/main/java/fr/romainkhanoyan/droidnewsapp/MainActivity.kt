package fr.romainkhanoyan.droidnewsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import fr.romainkhanoyan.droidnewsapp.services.NewsRepository
import fr.romainkhanoyan.droidnewsapp.services.NewsWrapper
import fr.romainkhanoyan.droidnewsapp.viewholder.NewsItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var feed = "hot"
    var country = "fr"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        createContent(country)

        homeButton.setOnClickListener{
            feed = "hot"
            createContent(country)
        }

        healthButton.setOnClickListener{
            feed = "health"
            createContentCategory(country, feed)
        }

        businessButton.setOnClickListener{
            feed = "business"
            createContentCategory(country, feed)
        }

        scienceButton.setOnClickListener{
            feed = "science"
            createContentCategory(country, feed)
        }

        sportButton.setOnClickListener{
            feed = "sport"
            createContentCategory(country, feed)
        }

        entertainmentButton.setOnClickListener{
            feed = "entertainment"
            createContentCategory(country, feed)
        }

        technologyButton.setOnClickListener{
            feed = "technology"
            createContentCategory(country, feed)
        }

        val options = arrayListOf("France", "Amerique",  "Germany","United Kingdom")
        val values = arrayListOf("fr", "us", "de", "gb")
        countrySelector.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,options)
        countrySelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "not selected", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                country = values[p2]
                if(feed == "hot")
                {
                    createContent(values[p2])
                }
                else
                {
                    createContentCategory(values[p2], feed)
                }
            }
        }
    }

    fun createContent(country: String)
    {
        feed = "hot"
        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val repository = NewsRepository()
        val fastAdapter = FastAdapter.with<NewsItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        itemAdapter.clear()

        repository.api.getHeadlines(country)
            .enqueue(object : Callback<NewsWrapper> {

                override fun onFailure(call: Call<NewsWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT).show()
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            true
        }
    }

    fun createContentCategory(country: String, category: String)
    {
        feed = category
        val itemAdapter = ItemAdapter<IItem<*, *>>()
        val repository = NewsRepository()
        val fastAdapter = FastAdapter.with<NewsItem, ItemAdapter<IItem<*, *>>>(itemAdapter)

        itemAdapter.clear()

        repository.api.getHeadlinesHealth(country, category)
            .enqueue(object : Callback<NewsWrapper> {

                override fun onFailure(call: Call<NewsWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT).show()
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            true
        }
    }
}
