package com.proplens.shared.domain.repository

import com.proplens.shared.domain.model.Deal

interface DealRepository {
    suspend fun getDeals(): List<Deal>
    suspend fun getDealById(id: String): Deal?
    suspend fun saveDeal(deal: Deal)
    suspend fun deleteDeal(id: String)
}
