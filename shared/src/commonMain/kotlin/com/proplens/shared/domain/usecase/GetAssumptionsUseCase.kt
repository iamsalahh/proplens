package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.model.Assumptions
import com.proplens.shared.domain.repository.AssumptionsRepository

class GetAssumptionsUseCase(private val assumptionsRepository: AssumptionsRepository) {
    suspend operator fun invoke(): Assumptions = assumptionsRepository.getAssumptions()
}
