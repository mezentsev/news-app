package pro.mezentsev.newsapp.sources.adapter

import pro.mezentsev.newsapp.model.Source

interface SourceClickListener {
    fun onSourceObtained(source: Source)
}