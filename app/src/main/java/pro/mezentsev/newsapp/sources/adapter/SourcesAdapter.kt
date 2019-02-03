package pro.mezentsev.newsapp.sources.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.source_view.view.*
import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.model.Source


class SourcesAdapter : RecyclerView.Adapter<SourcesAdapter.SourceHolder>(), OnSourceHolderClicked {
    private var sourceClickListener: SourceClickListener? = null
    private var sources: List<Source>? = null

    fun setSources(sourcesList: List<Source>) {
        sources = sourcesList
        notifyDataSetChanged()
    }

    fun setSourceClickListener(listener: SourceClickListener?) {
        sourceClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceHolder {
        return SourceHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.source_view, parent, false)
        )
    }

    override fun getItemCount() = sources?.size ?: 0

    override fun onBindViewHolder(holder: SourceHolder, position: Int) {
        sources?.let {
            if (position < it.size) {
                holder.bind(it[position], this)
            }
        }
    }

    override fun onClick(source: Source, holderPosition: Int) {
        if (sources?.contains(source) == true) {
            sourceClickListener?.onSourceObtained(source)
        } else {
            Log.e(TAG, "Source is not found! $source", Exception())
        }
    }

    inner class SourceHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.title_view
        val descriptionTextView = view.description_view
        val urlView = view.url_view

        fun bind(source: Source, listener: OnSourceHolderClicked) {
            itemView.setOnClickListener {
                listener.onClick(source, adapterPosition)
            }

            titleTextView?.text = source.name
            descriptionTextView?.text = source.description
            urlView?.text = source.url
        }
    }

    companion object {
        const val TAG = "SourcesAdapter"
    }
}