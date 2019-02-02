package pro.mezentsev.newsapp.data.api

import pro.mezentsev.newsapp.model.Article

data class ArticlesResponse(val status: String,
                            val totalResults: Int,
                            val articles: List<Article>)