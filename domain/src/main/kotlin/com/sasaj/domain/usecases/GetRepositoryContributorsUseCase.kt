package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.Contributor
import io.reactivex.Observable
import java.util.*

class GetRepositoryContributorsUseCase(transformer: Transformer<List<Contributor>>,
                                       private val repository: Repository) : UseCase<List<Contributor>>(transformer) {


    fun getRepositoryContributors(url: String): Observable<List<Contributor>> {
        val data = HashMap<String, String>()
        data[PARAM_URL] = url
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<Contributor>> {
        return repository.getContributorsForRepository( data!![PARAM_URL] as String)
    }

    companion object {
        private const val PARAM_URL = "param:url"
    }
}