package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.Contributor
import io.reactivex.Observable
import java.util.*

class GetRepositoryContributorsUseCase(transformer: Transformer<List<Contributor>>,
                                       private val repository: Repository) : UseCase<List<Contributor>>(transformer) {


    fun getRepositoryContributors(userName: String, repositoryName: String): Observable<List<Contributor>> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NAME] = userName
        data[PARAM_REPO_NAME] = repositoryName
        return observable()
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<Contributor>> {
        return repository.getContributorsForRepository(data!![PARAM_USER_NAME] as String, data!![PARAM_REPO_NAME] as String)
    }

    companion object {
        private const val PARAM_USER_NAME = "param:user_name"
        private const val PARAM_REPO_NAME = "param:repo_name"
    }
}