package com.proplens.shared.data.repository

import com.proplens.shared.domain.model.AreaStats
import com.proplens.shared.domain.repository.AreaStatsRepository

class AreaStatsRepositoryImpl : AreaStatsRepository {
    private val stats = listOf(
        AreaStats(area = "Dubai Marina", avgPricePerSqft = 1800.0, avgRentPerSqftPerYear = 130.0),
        AreaStats(area = "Downtown Dubai", avgPricePerSqft = 2200.0, avgRentPerSqftPerYear = 150.0),
        AreaStats(area = "JVC", avgPricePerSqft = 950.0, avgRentPerSqftPerYear = 75.0),
        AreaStats(area = "Palm Jumeirah", avgPricePerSqft = 2800.0, avgRentPerSqftPerYear = 170.0)
    )

    override suspend fun getAreaStats(): List<AreaStats> = stats

    override suspend fun getAreaStatsForArea(area: String): AreaStats? = stats.firstOrNull {
        it.area.equals(area, ignoreCase = true)
    }
}
