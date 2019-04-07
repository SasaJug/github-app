package com.sasaj.githubapp.userlist

import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.User

data class UserListViewState(var state: Int = -1,
                             var stargazersList: List<User>? = null,
                             var contributorsList: List<Contributor>? = null) {

    companion object {
        const val LOADING: Int = 0
        const val STARGAZERS_LOADED: Int = 1
        const val CONTRIBUTORS_LOADED: Int = 2
    }
}