package fr.romainkhanoyan.droidnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        if(intent != null && intent.extras != null) {
            val myMessage = intent.extras.getString("content", "")
            if (myMessage.isNotBlank()) {
                contentTextView.text = myMessage
            } else {
                contentTextView.setText(R.string.display_empty_content)
            }
        } else {
            contentTextView.setText(R.string.display_empty_content)
        }
    }
}
