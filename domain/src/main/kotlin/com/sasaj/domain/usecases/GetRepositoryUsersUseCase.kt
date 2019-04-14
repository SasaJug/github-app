package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.User
import com.sasaj.domain.entities.State
import io.reactivex.Observable
import java.util.*

class GetRepositoryUsersUseCase( private val transformer: Transformer<Any>, private val repository: Repository){


    fun getRepositoryUsers(url: String): Triple<Observable<List<User>>, Observable<Any>, Observable<State>> {
        val data = HashMap<String, String>()
        data[PARAM_URL] = url
        return result(data)
    }

    private fun createObservable(data: Map<String, Any>?): Triple<Observable<List<User>>, Observable<Any>, Observable<State>> {
        val url = data!![PARAM_URL] as String
        return if(url.contains(URL_KEYWORD)){
            repository.getContributorsForRepository(data!![PARAM_URL] as String)
        } else {
            repository.getStargazersForRepository(data!![PARAM_URL] as String)
        }
    }

    private fun result(withData: Map<String, Any>? = null) : Triple<Observable<List<User>>, Observable<Any>, Observable<State>>{
        val result = createObservable(withData)
        result.first.compose(transformer)
        result.second.compose(transformer)
        result.first.compose(transformer)
        return result
    }

    companion object {
        private const val URL_KEYWORD = "contributors"
        private const val PARAM_URL = "param:url"
    }
}