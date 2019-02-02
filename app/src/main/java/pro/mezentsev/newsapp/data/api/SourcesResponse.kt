package pro.mezentsev.newsapp.data.api

import pro.mezentsev.newsapp.model.Source

data class SourcesResponse(val status: String,
                           val sources: List<Source>)