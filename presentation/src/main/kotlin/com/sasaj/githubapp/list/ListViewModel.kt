package com.sasaj.githubapp.list

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.usecases.GetAllRepositoriesUseCase
import com.sasaj.githubapp.common.BaseViewModel
import com.sasaj.githubapp.common.SingleLiveEvent

class ListViewModel(private val getAllRepositoriesUseCase: GetAllRepositoriesUseCase) : BaseViewModel(){

    val listLiveData: MutableLiveData<ListViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        listLiveData.value = ListViewState()
    }

    fun getAllRepositories() {
        val listViewState = listLiveData.value?.copy(state = ListViewState.LOADING)
        listLiveData.value = listViewState

        addDisposable(getAllRepositoriesUseCase.getAllRepositories()
                .subscribe(
                        { list: List<GithubRepository> ->
                            val newListViewState = listLiveData.value?.copy(state = ListViewState.REPOS_LOADED, repoList = list)
                            listLiveData.value = newListViewState
                            errorState.value = null
                        },
                        { e ->
                            errorState.value = e
                        },
                        { Log.i(TAG, "Repo list fetched") }
                )
        )
    }

    companion object {
        const val TAG : String = "ListViewModel"
    }

}