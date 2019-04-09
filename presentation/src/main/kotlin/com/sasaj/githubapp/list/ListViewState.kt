package com.sasaj.githubapp.list

import com.sasaj.domain.entities.GithubRepository

data class ListViewState(var state: Int = -1, var repoList: List<GithubRepository>? = null) {

    companion object {
        const val LOADING: Int = 0
        const val REPOS_LOADED: Int = 1
    }
}