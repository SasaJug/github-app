package com.sasaj.githubapp.userlist

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sasaj.domain.entities.User
import com.sasaj.domain.usecases.GetRepositoryUsersUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase
import com.sasaj.githubapp.common.ASyncTransformer
import com.sasaj.githubapp.common.BaseViewModel
import com.sasaj.githubapp.common.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers

class UserListViewModel(private val getRepositoryUsersUseCase: GetRepositoryUsersUseCase,
                        private val requestMoreUseCase: RequestMoreUseCase) : BaseViewModel() {

    val listLiveData: MutableLiveData<UserListViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    var url: String? = null
    var type: Int = -1

    init {
        listLiveData.value = UserListViewState()
    }


    fun getRepositoryUsers() {

        if (url == null) {
            throw IllegalArgumentException("Set url first!")
        }

        val listViewState = listLiveData.value?.copy(state = UserListViewState.LOADING)
        listLiveData.value = listViewState

        val result = getRepositoryUsersUseCase.getRepositoryUsers(url!!)

        addDisposable(
                result.first
                        .compose(ASyncTransformer<List<User>>())
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

        addDisposable(result.second
                .compose(ASyncTransformer<Any>())
                .subscribe(
                        { any: Any -> (any) },
                        { e ->
                            errorState.value = e
                        },
                        { Log.i(TAG, "") }
                )
        )

    }


    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {

            if (type == -1) {
                throw IllegalArgumentException("Set type first!")
            }
            requestMoreUseCase.requestMore(type).subscribe()
        }
    }

    companion object {
        const val TAG: String = "UserViewModel"
        private const val VISIBLE_THRESHOLD = 1
    }

}