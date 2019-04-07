package com.sasaj.domain.usecases

import com.sasaj.domain.Repository
import com.sasaj.domain.common.Transformer
import com.sasaj.domain.entities.GithubRepository
import io.reactivex.Observable

class GetAllRepositoriesUseCase(transformer: Transformer<List<GithubRepository>>,
                                private val repository: Repository) : UseCase<List<GithubRepository>>(transformer) {


    fun getAllRepositories(): Observable<List<GithubRepository>> {
        return observable()
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<GithubRepository>> {
        return repository.getPublicRepositories()
    }
}