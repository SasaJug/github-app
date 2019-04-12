package com.sasaj.githubapp.userlist

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.User
import com.sasaj.domain.usecases.GetRepositoryContributorsUseCase
import com.sasaj.domain.usecases.GetRepositoryStargazersUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase
import com.sasaj.githubapp.common.BaseViewModel
import com.sasaj.githubapp.common.SingleLiveEvent

class UserListViewModel(private val getRepositoryStargazersUseCase: GetRepositoryStargazersUseCase,
                        private val getRepositoryContributorsUseCase: GetRepositoryContributorsUseCase,
                        private val requestMoreUseCase: RequestMoreUseCase) : BaseViewModel() {

    val listLiveData: MutableLiveData<UserListViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        listLiveData.value = UserListViewState()
    }


    fun getRepositoryContributors(url: String) {
        val listViewState = listLiveData.value?.copy(state = UserListViewState.LOADING)
        listLiveData.value = listViewState

        addDisposable(getRepositoryContributorsUseCase.getRepositoryContributors(url)
                .subscribe(
                        { list: List<Contributor> ->
                            val newListViewState = listLiveData.value?.copy(state = UserListViewState.CONTRIBUTORS_LOADED, contributorsList = list)
                            listLiveData.value = newListViewState
                            errorState.value = null
                        },
                        { e ->
                            errorState.value = e
                        },
                        { Log.i(TAG, "Contributors list fetched") }
                )
        )
    }


    fun getRepositoryStargazers(url: String) {
        val listViewState = listLiveData.value?.copy(state = UserListViewState.LOADING)
        listLiveData.value = listViewState

        addDisposable(getRepositoryStargazersUseCase.getRepositoryStargazers(url)
                .subscribe(
                        { list: List<User> ->
                            val newListViewState = listLiveData.value?.copy(state = UserListViewState.STARGAZERS_LOADED, stargazersList = list)
                            listLiveData.value = newListViewState
                            errorState.value = null
                        },
                        { e ->
                            errorState.value = e
                        },
                        { Log.i(TAG, "User list fetched") }
                )
        )
    }


    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int, type: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            requestMoreUseCase.requestMore(type).subscribe()
        }
    }

    companion object {
        const val TAG: String = "UserViewModel"
        private const val VISIBLE_THRESHOLD = 1
    }

}