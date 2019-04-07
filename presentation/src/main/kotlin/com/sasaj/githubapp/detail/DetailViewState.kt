package com.sasaj.githubapp.detail

import com.sasaj.domain.entities.GithubRepository

data class DetailViewState(var state: Int = -1, var repository: GithubRepository? = null) {

    companion object {
        const val LOADING: Int = 0
        const val REPO_LOADED: Int = 1
    }
}