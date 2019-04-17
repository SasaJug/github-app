package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.User
import io.reactivex.Observable
import java.util.*

class GetRepositoryUsersUseCase(private val repository: Repository){


    fun getRepositoryUsers(url: String): Pair<Observable<List<User>>, Observable<Any>> {
        val data = HashMap<String, String>()
        data[PARAM_URL] = url
        return result(data)
    }

    private fun createObservables(data: Map<String, Any>?): Pair<Observable<List<User>>, Observable<Any>> {
        val url = data!![PARAM_URL] as String
        return if(url.contains(URL_KEYWORD)){
            repository.getContributorsForRepository(data!![PARAM_URL] as String)
        } else {
            repository.getStargazersForRepository(data!![PARAM_URL] as String)
        }
    }

    private fun result(withData: Map<String, Any>? = null) : Pair<Observable<List<User>>, Observable<Any>>{
        return createObservables(withData)
    }

    companion object {
        private const val URL_KEYWORD = "contributors"
        private const val PARAM_URL = "param:url"
    }
}