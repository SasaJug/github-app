package com.sasaj.domain.entities

data class State(var id: Int,
            val first: String?,
            val prev: String?,
            val next: String?,
            val last: String?) {

    companion object {
        const val CONST_REPOSITORY = 0
        const val CONST_CONTRIBUTOR = 1
        const val CONST_STARGAZER = 2
    }
}