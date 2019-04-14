package com.sasaj.githubapp.userlist

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sasaj.domain.entities.State
import com.sasaj.domain.entities.User
import com.sasaj.domain.usecases.GetRepositoryUsersUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase
import com.sasaj.githubapp.common.BaseViewModel
import com.sasaj.githubapp.common.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers

class UserListViewModel(private val getRepositoryUsersUseCase: GetRepositoryUsersUseCase,
                        private val requestMoreUseCase: RequestMoreUseCase) : BaseViewModel() {

    val listLiveData: MutableLiveData<UserListViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    var state: State? = null

    init {
        listLiveData.value = UserListViewState()
    }

    fun getRepositoryUsers(url: String) {

        val listViewState = listLiveData.value?.copy(state = UserListViewState.LOADING)
        listLiveData.value = listViewState

        val result = getRepositoryUsersUseCase.getRepositoryUsers(url)

        addDisposable(
                result.first.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                        { list: List<User> ->
                            val newListViewState = listLiveData.value?.copy(state = UserListViewState.CONTRIBUTORS_LOADED, contributorsList = list)
                            listLiveData.value = newListViewState
                            errorState.value = null
                        },
                        { e ->
                            errorState.value = e
                        },
                        { Log.i(TAG, "Users list fetched") }
                )
        )

        addDisposable(result.second.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                { any: Any -> (any) },
                { e ->
                    errorState.value = e
                },
                { Log.i(TAG, "") }
        )
        )

        addDisposable(result.third.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                { state: State? -> this@UserListViewModel.state = state },
                { e ->
                    errorState.value = e
                },
                { Log.i(TAG, "") }
        )
        )
    }


    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int, type: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            state?.next?.let {
                requestMoreUseCase.requestMore(type, state!!.next!!).subscribe()
            }
        }
    }

    companion object {
        const val TAG: String = "UserViewModel"
        private const val VISIBLE_THRESHOLD = 1
    }

}