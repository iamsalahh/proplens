package com.proplens.shared.data.repository

import com.proplens.shared.domain.model.Deal
import com.proplens.shared.domain.repository.DealRepository
import kotlin.random.Random

class DealRepositoryImpl : DealRepository {
    private val deals = mutableListOf(
        Deal(
            id = "1",
            title = "Dubai Marina 2BR",
            area = "Dubai Marina",
            bedrooms = 2,
            sizeSqft = 1200.0,
            askingPrice = 2200000.0,
            expectedRentPerYear = 160000.0,
            serviceChargePerYear = 12000.0,
            additionalCostsPerYear = 3000.0,
            createdAtMillis = System.currentTimeMillis()
        ),
        Deal(
            id = "2",
            title = "JVC Studio",
            area = "JVC",
            bedrooms = 0,
            sizeSqft = 450.0,
            askingPrice = 500000.0,
            expectedRentPerYear = 40000.0,
            serviceChargePerYear = 5000.0,
            additionalCostsPerYear = 1000.0,
            createdAtMillis = System.currentTimeMillis()
        )
    )

    override suspend fun getDeals(): List<Deal> = deals.sortedByDescending { it.createdAtMillis }

    override suspend fun getDealById(id: String): Deal? = deals.firstOrNull { it.id == id }

    override suspend fun saveDeal(deal: Deal) {
        val existingIndex = deals.indexOfFirst { it.id == deal.id }
        if (existingIndex >= 0) {
            deals[existingIndex] = deal
        } else {
            val newDeal = deal.copy(id = deal.id.ifEmpty { generateId() })
            deals.add(newDeal)
        }
    }

    override suspend fun deleteDeal(id: String) {
        deals.removeAll { it.id == id }
    }

    private fun generateId(): String = Random.nextInt(100000, 999999).toString()
}
