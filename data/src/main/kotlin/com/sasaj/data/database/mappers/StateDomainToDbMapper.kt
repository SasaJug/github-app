package com.sasaj.data.database.mappers

import com.sasaj.data.database.entities.StateDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.State

class StateDomainToDbMapper : Mapper<State, StateDb>() {

    override fun mapFrom(from: State): StateDb {
        return StateDb(
                id = from.id,
                first = from.first,
                prev = from.prev,
                next = from.next,
                last = from.last)

    }
}