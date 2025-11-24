package com.proplens.shared.domain.usecase

import com.proplens.shared.domain.model.Assumptions
import com.proplens.shared.domain.repository.AssumptionsRepository

class SaveAssumptionsUseCase(private val assumptionsRepository: AssumptionsRepository) {
    suspend operator fun invoke(assumptions: Assumptions) = assumptionsRepository.saveAssumptions(assumptions)
}
