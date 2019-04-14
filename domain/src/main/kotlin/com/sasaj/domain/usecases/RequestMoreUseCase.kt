package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import io.reactivex.Observable
import java.util.*

class RequestMoreUseCase (transformer: Transformer<Boolean>,
                          private val repository: Repository) : UseCase<Boolean>(transformer) {


    fun requestMore(type: Int, url: String): Observable<Boolean> {
        val data = HashMap<String, Any>()
        data[PARAM_TYPE] = type
        data[PARAM_URL] = url
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val type: Int = data!![PARAM_TYPE] as Int
        val url: String = data!![PARAM_URL] as String
        return repository.requestMore(type, url)
    }

    companion object {
        const val PARAM_TYPE = "param: type"
        const val PARAM_URL = "param: url"
    }
}