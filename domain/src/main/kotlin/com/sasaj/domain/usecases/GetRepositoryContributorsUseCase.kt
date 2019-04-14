package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.User
import io.reactivex.Observable
import java.util.*

class GetRepositoryContributorsUseCase(transformer: Transformer<List<User>>,
                                       private val repository: Repository) : UseCase<List<User>>(transformer) {


    fun getRepositoryContributors(url: String): Observable<List<User>> {
        val data = HashMap<String, String>()
        data[PARAM_URL] = url
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<User>> {
        return repository.getContributorsForRepository( data!![PARAM_URL] as String).first
    }

    companion object {
        private const val PARAM_URL = "param:url"
    }
}