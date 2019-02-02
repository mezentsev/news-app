package pro.mezentsev.newsapp.model

data class Source(val id: String,
                  val name: String,
                  val description: String? = null,
                  val url: String? = null,
                  val category: String? = null,
                  val language: String? = null,
                  val country: String? = null)