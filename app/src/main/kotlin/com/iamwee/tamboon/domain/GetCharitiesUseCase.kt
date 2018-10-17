package com.iamwee.tamboon.domain

import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.data.repository.CharityRepository

class GetCharitiesUseCase(
    private val repository: CharityRepository
) : UseCase<Unit, List<Charity>>() {

    override suspend fun execute(params: Unit) = repository.charities()

}

