package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import io.reactivex.Observable
import java.util.*

class RequestMoreUseCase (transformer: Transformer<Boolean>,
                          private val repository: Repository) : UseCase<Boolean>(transformer) {


    fun requestMore(type: Int): Observable<Boolean> {
        val data = HashMap<String, Any>()
        data[PARAM_TYPE] = type
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val type: Int = data!![PARAM_TYPE] as Int
        return repository.requestMore(type)
    }

    companion object {
        const val PARAM_TYPE = "param: type"
        const val CONST_REPOSITORY = 0
        const val CONST_CONTRIBUTOR = 1
        const val CONST_STARGAZER = 2
    }
}