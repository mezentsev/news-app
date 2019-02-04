package pro.mezentsev.newsapp.articles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.article_view.view.*
import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.model.Article

class ArticlesAdapter constructor(private val context: Context) : RecyclerView.Adapter<ArticlesAdapter.ArticleHolder>() {
    private val articles = mutableListOf<Article>()

    fun setArticles(articlesList: List<Article>) {
        articles.clear()
        articles.addAll(articlesList)
        notifyDataSetChanged()
    }

    fun getArticles() = articles.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.article_view, parent, false)
        )
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(articles[position])

        Glide.with(context)
                .load(articles[position].urlToImage)
                .apply(RequestOptions()
                        .override(300))
                .into(holder.thumbnailImageView)
    }


    inner class ArticleHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.title_view
        val descriptionTextView = view.description_view
        val thumbnailImageView = view.thumbnail_view
        val authorTextView = view.author_view
        val dateTextView = view.date_view

        fun bind(article: Article) {
            titleTextView?.text = article.title
            descriptionTextView?.text = article.description
            authorTextView?.text = article.author
            dateTextView?.text = article.publishedAt
        }
    }
}