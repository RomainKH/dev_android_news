package fr.romainkhanoyan.droidnewsapp.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import fr.romainkhanoyan.droidnewsapp.R
import fr.romainkhanoyan.droidnewsapp.services.Article

class NewsViewHolder(itemView: View) : FastAdapter.ViewHolder<NewsItem>(itemView) {

    val titleTextView: TextView
    val descriptionTextView: TextView
    val authorTextView: TextView
    val dateTextView: TextView
    val newsImageView: ImageView

    init {
        titleTextView = itemView.findViewById(R.id.titleTextView)
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView)
        authorTextView = itemView.findViewById(R.id.authorTextView)
        dateTextView = itemView.findViewById(R.id.dateTextView)
        newsImageView = itemView.findViewById(R.id.NewsImageView)
    }

    override fun bindView(item: NewsItem, payloads: MutableList<Any>) {
        titleTextView.text = item.article.title
        descriptionTextView.text = item.article.description
        authorTextView.text = item.article.author
        dateTextView.text = item.article.publishedAt

        Glide.with(newsImageView)
            .load(item.article.urlToImage)
            .into(newsImageView)
    }

    override fun unbindView(item: NewsItem) {
        titleTextView.text = ""
        descriptionTextView.text = ""
        authorTextView.text = ""
        dateTextView.text = ""
        newsImageView.setImageBitmap(null)
    }

}

class NewsItem(val article: Article): AbstractItem<NewsItem, NewsViewHolder>() {
    override fun getType() = R.id.item_news

    override fun getViewHolder(v: View) = NewsViewHolder(v)

    override fun getLayoutRes() = R.layout.row_article

}