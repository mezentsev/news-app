package pro.mezentsev.newsapp.sources.adapter

import pro.mezentsev.newsapp.model.Source

interface OnSourceHolderClicked {
    /**
     * User clicked on ViewHolder of [SourcesAdapter].
     */
    fun onClick(source: Source, holderPosition: Int)
}