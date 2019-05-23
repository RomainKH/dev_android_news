package fr.romainkhanoyan.droidnewsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.romainkhanoyan.droidnewsapp.services.NewsRepository
import fr.romainkhanoyan.droidnewsapp.services.NewsWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        val repository = NewsRepository()

        repository.api.getHeadlines()
            .enqueue(object : Callback<NewsWrapper> {

                override fun onFailure(call: Call<NewsWrapper>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@NewsFeedActivity, "ERROR", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<NewsWrapper>, response: Response<NewsWrapper>) {
                    val newsWrapper = response.body()

                    if (newsWrapper != null) {
                        val status = newsWrapper.status

                        Toast.makeText(this@NewsFeedActivity, "Status = $status", Toast.LENGTH_SHORT).show()

                    }

                }

            })
    }
}
