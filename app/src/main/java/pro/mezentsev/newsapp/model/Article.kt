package pro.mezentsev.newsapp.model

data class Article(private val source: Source,
                   val author: String,
                   val title: String,
                   val description: String,
                   val url: String,
                   val urlToImage: String,
                   val publishedAt: String,
                   val content: String)