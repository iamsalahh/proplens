package com.proplens.shared.domain.repository

import com.proplens.shared.domain.model.Assumptions

interface AssumptionsRepository {
    suspend fun getAssumptions(): Assumptions
    suspend fun saveAssumptions(assumptions: Assumptions)
}
