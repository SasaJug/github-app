package com.sasaj.githubapp.detail

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.usecases.GetSingleRepositoryUseCase
import com.sasaj.githubapp.common.BaseViewModel
import com.sasaj.githubapp.common.SingleLiveEvent


class DetailViewModel(private val getSingleRepositoryUseCase: GetSingleRepositoryUseCase) : BaseViewModel() {

    val detailLiveData: MutableLiveData<DetailViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        detailLiveData.value = DetailViewState()
    }

    fun getSingleRepository(userName: String, repositoryName: String) {
        val detailViewState = detailLiveData.value?.copy(state = DetailViewState.LOADING)
        detailLiveData.value = detailViewState

        addDisposable(getSingleRepositoryUseCase.getSingleRepository(userName, repositoryName)
                .subscribe(
                        { repository: GithubRepository ->
                            val newDetailViewState = detailLiveData.value?.copy(state = DetailViewState.REPO_LOADED, repository = repository)
                            detailLiveData.value = newDetailViewState
                            errorState.value = null
                        },
                        { e ->
                            errorState.value = e
                        },
                        { Log.i(TAG, "Repository fetched") }
                )
        )
    }

    companion object {
        const val TAG: String = "DetailViewModel"
    }

}