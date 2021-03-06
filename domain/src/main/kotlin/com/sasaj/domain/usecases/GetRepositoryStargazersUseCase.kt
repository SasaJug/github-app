package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable
import java.util.HashMap

class GetRepositoryStargazersUseCase(transformer: Transformer<List<User>>,
                                     private val repository: Repository) : UseCase<List<User>>(transformer) {


    fun getRepositoryStargazers(userName : String, repositoryName : String): Observable<List<User>> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NAME] = userName
        data[PARAM_REPO_NAME] = repositoryName
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<User>> {
        return repository.getStargazersForRepository(data!![PARAM_USER_NAME] as String, data!![PARAM_REPO_NAME] as String)

    }

    companion object {
        private const val PARAM_USER_NAME = "param:user_name"
        private const val PARAM_REPO_NAME = "param:repo_name"
    }
}