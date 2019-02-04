package pro.mezentsev.newsapp.sources.adapter

import pro.mezentsev.newsapp.model.Source

interface SourceClickListener {
    /**
     * User clicked on [Source] in RecyclerView.
     */
    fun onSourceObtained(source: Source)
}