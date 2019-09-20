package pro.mezentsev.newsapp.data

import io.reactivex.Single
import pro.mezentsev.newsapp.model.Source

interface SourcesNewsRepository {
    /**
     * Loads list of [Source] from repository. Emits [Single].
     */
    fun loadSources(): Single<List<Source>>
}