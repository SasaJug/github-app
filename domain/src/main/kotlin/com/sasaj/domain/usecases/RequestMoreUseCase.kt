package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import io.reactivex.Observable
import java.util.*

class RequestMoreUseCase (transformer: Transformer<Boolean>,
                          private val repository: Repository) : UseCase<Boolean>(transformer) {


    fun requestMore(type: Int, username: String = "", repositoryName: String = ""): Observable<Boolean> {
        val data = HashMap<String, Any>()
        data[PARAM_TYPE] = type
        data[PARAM_USERNAME] = username
        data[PARAM_REPONAME] = repositoryName
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val type: Int = data!![PARAM_TYPE] as Int
        val username = data!![PARAM_USERNAME] as String
        val repositoryName = data!![PARAM_REPONAME] as String
        return repository.requestMore(type, username, repositoryName)
    }

    companion object {
        const val PARAM_TYPE = "param: type"
        const val PARAM_USERNAME = "param: username"
        const val PARAM_REPONAME = "param: repository"
        const val CONST_REPOSITORY = 0
        const val CONST_CONTRIBUTOR = 1
        const val CONST_STARGAZER = 2
    }
}