package com.sasaj.data.database.mappers

import com.sasaj.data.database.entities.StateDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.State

class StateDbToDomainMapper : Mapper<StateDb?, State?>() {

    override fun mapFrom(from: StateDb?): State? {
        return if (from != null) {
            State(
                    id = from.id,
                    first = from.first,
                    prev = from.prev,
                    next = from.next,
                    last = from.last)
        } else {
            null
        }


    }
}