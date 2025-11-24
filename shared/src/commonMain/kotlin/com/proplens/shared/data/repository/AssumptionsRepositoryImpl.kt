package com.proplens.shared.data.repository

import com.proplens.shared.domain.model.Assumptions
import com.proplens.shared.domain.repository.AssumptionsRepository

class AssumptionsRepositoryImpl : AssumptionsRepository {
    private var assumptions: Assumptions = Assumptions()

    override suspend fun getAssumptions(): Assumptions = assumptions

    override suspend fun saveAssumptions(assumptions: Assumptions) {
        this.assumptions = assumptions
    }
}
