package fr.romainkhanoyan.droidnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        if(intent != null && intent.extras != null) {
            val myTitle = intent.extras.getString("title", "")
            val myMessage = intent.extras.getString("content", "")
            val myImage = intent.extras.getString("image", "")
            val date = intent.extras.getString("date", "")
            val author = intent.extras.getString("author", "")
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
        } else {
            contentTextView.setText(R.string.display_empty_content)
            titleTextView.setText(R.string.display_empty_content)
        }
    }
}
