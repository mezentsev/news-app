package pro.mezentsev.newsapp.articles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.article_view.view.*
import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.model.Article

class ArticlesAdapter constructor(private val context: Context) : RecyclerView.Adapter<ArticlesAdapter.ArticleHolder>() {
    private val articles = mutableListOf<Article>()

    fun getArticles() = articles.toList()

    fun addArticles(articlesList: List<Article>) {
        if (articlesList.isNullOrEmpty()) {
            return
        }

        val previousCount = articles.size
        articles.addAll(articlesList)
        notifyItemRangeInserted(previousCount, articlesList.size)
    }

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
            dateTextView?.text = article.getDate()

            article.urlToImage?.let {
                Glide.with(context)
                        .load(it)
                        .apply(RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop())
                        .into(thumbnailImageView)
            } ?: Glide.with(context)
                    .clear(thumbnailImageView)

        }
    }
}