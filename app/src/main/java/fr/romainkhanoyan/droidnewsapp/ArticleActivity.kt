package fr.romainkhanoyan.droidnewsapp

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_article.*
import java.util.regex.Pattern

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        supportActionBar!!.title = "Go Back"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        if(intent != null && intent.extras != null) {
            val myTitle = intent.extras.getString("title", "")
            val myMessage = intent.extras.getString("content", "")
            val myImage = intent.extras.getString("image", "")
            val date = intent.extras.getString("date", "")
            val author = intent.extras.getString("author", "")
            val url = intent.extras.getString("url", "")
            Glide.with(articleImageView)
                .load(myImage)
                .into(articleImageView)
            if (myMessage.isNotBlank()) {
                contentTextView.text = myMessage
            } else {
                contentTextView.setText(R.string.display_empty_content)
            }
            if (myTitle.isNotBlank()) {
                titleTextView.text = myTitle
            } else {
                titleTextView.setText(R.string.display_empty_content)
            }
            if (date.isNotBlank()) {
                dateTextView.text = date
            } else {
                dateTextView.setText(R.string.display_empty_date)
            }
            if (author.isNotBlank()) {
                authorTextView.text = author
            } else {
                authorTextView.setText(R.string.display_empty_author)
            }
            if (url.isNotBlank()) {
                linkTextView.text = "Lire la suite"
                val pattern = Pattern.compile("Lire la suite")
                Linkify.addLinks(linkTextView, pattern, url)
            } else {
                linkTextView.setText(R.string.display_empty_content)
            }
        } else {
            contentTextView.setText(R.string.display_empty_content)
            titleTextView.setText(R.string.display_empty_content)
        }
    }

    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }
}

