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
    val authorTextView: TextView
    val dateTextView: TextView
    val newsImageView: ImageView

    init {
        titleTextView = itemView.findViewById(R.id.titleTextView)
        authorTextView = itemView.findViewById(R.id.authorTextView)
        dateTextView = itemView.findViewById(R.id.dateTextView)
        newsImageView = itemView.findViewById(R.id.NewsImageView)
    }

    override fun bindView(item: NewsItem, payloads: MutableList<Any>) {
        titleTextView.text = item.article.title
        authorTextView.text = item.article.author
        dateTextView.text = formateDate(item.article.publishedAt)

        Glide.with(newsImageView)
            .load(item.article.urlToImage)
            .into(newsImageView)
    }

    override fun unbindView(item: NewsItem) {
        titleTextView.text = ""
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

fun formateDate(date: String): CharSequence? {
    var reformateDate = date.replace("-","/")
    var addSpace = reformateDate.replace("T",", ")
    var finalDate = addSpace.replace("Z"," ")

    return finalDate
}