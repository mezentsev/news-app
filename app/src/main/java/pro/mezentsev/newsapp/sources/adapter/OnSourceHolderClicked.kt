package pro.mezentsev.newsapp.sources.adapter

import pro.mezentsev.newsapp.model.Source

interface OnSourceHolderClicked {
    fun onClick(source: Source, holderPosition: Int)
}